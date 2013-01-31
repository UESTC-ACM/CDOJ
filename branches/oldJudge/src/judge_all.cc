/*
 * judge_all.cpp
 *
 * by felix021 http://www.felix021.com
 *
 * 此judge整合了C/C++/Java/Pascal的judge
 *
 */
#include <iostream>
#include <string>
#include <cstdio>
#include <cstdlib>
#include <cstring>
extern "C"
{
#include <unistd.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <sys/reg.h>
#include <sys/user.h>
#include <sys/syscall.h>
#include <sys/resource.h>
#include <sys/ptrace.h>
}
#include "judge.h"
#include "logger.h"
#include "rf_table.h"

using namespace std;

extern int errno;
int int_ignored; //用来应付"warn_if_unused"的返回值, GCC真麻烦..

int main(int argc, char *argv[], char *envp[])
{
    nice(10);
    judge_conf::load();
    log_open(judge_conf::log_file.c_str());
    FM_LOG_DEBUG("\n\x1b[31m-----a new start-----\x1b[0m");
    if (geteuid() != 0)
    {
        FM_LOG_FATAL("euid != 0, please run as root, or set suid bit(chmod +4755)");
        exit(judge_conf::EXIT_UNPRIVILEGED);
    }
    parse_arguments(argc, argv);
    judge_conf::judge_time_limit += problem::time_limit;
    if (EXIT_SUCCESS != malarm(ITIMER_REAL, judge_conf::judge_time_limit))
    {
        FM_LOG_WARNING("set alarm for judge failed, %d: %s", errno, strerror(errno));
        exit(judge_conf::EXIT_VERY_FIRST);
    }
    signal(SIGALRM, timeout);

//编译---------------------------------------------------------------------
    pid_t compiler = fork();
    int status = 0;
    if (compiler < 0)
    {
        FM_LOG_WARNING("error fork compiler");
        exit(judge_conf::EXIT_COMPILE);
    }
    else if (compiler == 0)
    {
        //运行编译器的进程
        log_add_info("compiler");
        set_compile_limit();
        stdout = freopen(problem::stdout_file_compiler.c_str(), "w", stdout);
        stderr = freopen(problem::stderr_file_compiler.c_str(), "w", stderr);

        if (stdout == NULL || stderr == NULL)
        {
            FM_LOG_WARNING("error freopen: stdout(%p), stderr(%p)", stdout, stderr);
            exit(judge_conf::EXIT_COMPILE);
        }

        //malarm(ITIMER_REAL, judge_conf::compile_time_limit);
        switch (problem::lang)
        {
            case judge_conf::LANG_C:
                FM_LOG_TRACE("start: gcc -static -w -O2 -DOJ -o %s %s",
                        problem::exec_file.c_str(), problem::source_file.c_str());
                execlp("gcc", "gcc", "-static", "-w", "-O2", "-DOJ",
                       "-o", problem::exec_file.c_str(),
                       problem::source_file.c_str(),
                       NULL);
                break;

            case judge_conf::LANG_CPP:
                FM_LOG_TRACE("start: g++ -static -w -O2 -DOJ -o %s %s",
                        problem::exec_file.c_str(), problem::source_file.c_str());
                execlp("g++", "g++", "-static", "-w", "-O2", "-DOJ",
                       "-o", problem::exec_file.c_str(),
                       problem::source_file.c_str(),
                       NULL);
                break;

            case judge_conf::LANG_JAVA:
                FM_LOG_TRACE("start: javac %s -d %s", problem::source_file.c_str(), problem::temp_dir.c_str());
                execlp("javac", "javac",
                       problem::source_file.c_str(), "-d", problem::temp_dir.c_str(),
                       NULL);
                break;

            case judge_conf::LANG_PASCAL:
                FM_LOG_TRACE("start: fpc -o%s %s -Co -Cr -Ct -Ci",
                        problem::exec_file.c_str(), problem::source_file.c_str());
                execlp("fpc", "fpc", problem::source_file.c_str(),
                       ("-o" + problem::exec_file).c_str(),
                        "-Co", "-Cr", "-Ct", "-Ci",
                       NULL);
                break;
        }

        //如果执行到这里说明execlp出错了
        FM_LOG_WARNING("exec error");
        exit(judge_conf::EXIT_COMPILE);
    }
    else
    {
        //Judge进程(父进程)
        pid_t w = waitpid(compiler, &status, WUNTRACED);
        if (w == -1)
        {
            FM_LOG_WARNING("waitpid error");
            exit(judge_conf::EXIT_COMPILE);
        }

        FM_LOG_TRACE("compiler finished");
        if (WIFEXITED(status))
        {
            if (EXIT_SUCCESS == WEXITSTATUS(status))
            {
                FM_LOG_TRACE("compile succeeded");
            }
            else if (judge_conf::GCC_COMPILE_ERROR == WEXITSTATUS(status))
            {
                FM_LOG_TRACE("compile error");
                output_result(judge_conf::OJ_CE);
                exit(judge_conf::EXIT_OK);
            }
            else
            {
                FM_LOG_WARNING(" compiler unknown exit status %d", WEXITSTATUS(status));
                exit(judge_conf::EXIT_COMPILE);
            }
        }
        else
        {
            if (WIFSIGNALED(status))
            {
                //if (SIGALRM == WTERMSIG(status))
                    //FM_LOG_WARNING("compiler time limit exceeded");
                //else
                    //FM_LOG_WARNING("unknown signal(%d)", WTERMSIG(status));
                FM_LOG_WARNING("compiler limit exceeded");
                output_result(judge_conf::OJ_CE);
                stderr = freopen(problem::stderr_file_compiler.c_str(), "w", stderr);
                fprintf(stderr, "Compiler Limit Exceeded\n");
                exit(judge_conf::EXIT_OK);
            }
            else if (WIFSTOPPED(status))
            {
                FM_LOG_WARNING("stopped by signal %d\n", WSTOPSIG(status));
            }
            else
            {
                FM_LOG_WARNING("unknown stop reason, status(%d)", status);
            }
            exit(judge_conf::EXIT_COMPILE);
        }
    }
    //end of fork for compiler, compile succeeded here

//Judge----------------------------------------------------------------------
    //打开输入输出文件
    FILE *fp = fopen(problem::data_file.c_str(), "r");
    if (fp == NULL)
    {
        FM_LOG_WARNING("error opening data_file");
        return judge_conf::EXIT_PRE_JUDGE;
    }

    FM_LOG_TRACE("start reading data.txt");
    char line[1024];
    //每一行对应一个输入文件, 需要judge一次
    while (true)
    {
        struct rusage rused;
        int_ignored = fscanf(fp, " %1023[^\r\n]", line);
        int len = strlen(line);
        while (len > 0 && (line[len - 1] == ' ' || line[len - 1] == '\t'))
        {
            len--;
        }
        if (feof(fp) || len == 0)
        {
            FM_LOG_TRACE("end of reading data_file");
            break;
        }
        line[len] = '\0';
        problem::input_file      = problem::data_dir + "/" + line + ".in";
        problem::output_file_std = problem::data_dir + "/" + line + ".out";
        FM_LOG_DEBUG("input_file        %s", problem::input_file.c_str());
        FM_LOG_DEBUG("output_file_std   %s", problem::output_file_std.c_str());
        pid_t executive = fork();
        if (executive < 0)
        {
            FM_LOG_WARNING("fork for child failed");
            exit(judge_conf::EXIT_PRE_JUDGE);
        }
        else if (executive == 0)
        {
            //子进程，用于运行用户提交的代码
            log_add_info("executive");

            //重定向 stdin, stdout, stderr
            io_redirect();
            fclose(fp);

            //安全相关, 包括seteuid, chroot
            set_security_option();

            //设置 memory, time, output 限制..
            set_limit();

            FM_LOG_DEBUG("tl: %d, tu: %d, tla: %d",
                    problem::time_limit, problem::time_usage,
                    judge_conf::time_limit_addtion);
            int real_time_limit = problem::time_limit //总时限
                                - problem::time_usage //已用时间
                                + judge_conf::time_limit_addtion; //误差调整
            //设置一个真实时间的ALARM，防止sleep/io等卡住不退
            if (EXIT_SUCCESS != malarm(ITIMER_REAL, real_time_limit))
            {
                FM_LOG_WARNING("malarm failed");
                exit(judge_conf::EXIT_PRE_JUDGE);
            }

            FM_LOG_TRACE("begin executive: %s", problem::exec_file.c_str());
            log_close();
            if (ptrace(PTRACE_TRACEME, 0, NULL, NULL) < 0)
            {
                //无法打日志了，因为已经close日志且chroot了, 所以给出更详细的退出值
                exit(judge_conf::EXIT_PRE_JUDGE_PTRACE);
            }
            //载入程序
            if (problem::lang != judge_conf::LANG_JAVA)
            {
                execl("./a.out", "a.out", NULL);
            }
            else
            {
                //execlp("java", "java", "-Xms256m", "-Xmx512m", "-Xss8m", "-Djava.security.manager", "-Djava.security.policy==../../java.policy", "Main", NULL);
                execlp("java", "java", "-Djava.security.manager", "-Djava.security.policy=../../java.policy", "Main", NULL);
            }

            //运行到此说明execlp出错了, 无法打日志了
            exit(judge_conf::EXIT_PRE_JUDGE_EXECLP);
        }
        else
        {
            //judge进程(父进程)
            int status          = 0;
            int syscall_id      = 0; //存放系统调用的调用号
            struct user_regs_struct regs;

            //每个case在judge之前都需要初始化rf_table
            init_RF_table(problem::lang);
            FM_LOG_TRACE("start judging...");
            while (true)
            {
                if (wait4(executive, &status, 0, &rused) < 0)
                {
                    FM_LOG_WARNING("wait4 failed, %d:%s", errno, strerror(errno));
                    exit(judge_conf::EXIT_JUDGE);
                }

                if (WIFEXITED(status))
                {
                    //子进程主动退出
                    //如果是JAVA返回值非0表示出错，其他语言不考虑此返回值
                    if (problem::lang != judge_conf::LANG_JAVA
                        || WEXITSTATUS(status) == EXIT_SUCCESS)
                    {
                        //子进程返回0 (AC/PE,WA)
                        FM_LOG_TRACE("normal quit");
                        int result;
                        if (problem::spj)
                        {
                            //SPJ，由SPJ程序判结果
                            result = oj_compare_output_spj( problem::input_file,
                            			       problem::output_file_std,
                                                       problem::stdout_file_executive,
                                                       problem::spj_exe_file,
                                                       problem::stdout_file_spj);
                        }
                        else
                        {
                            //非SPJ，直接判结果
                            result = oj_compare_output(problem::output_file_std,
                                                       problem::stdout_file_executive);
                        }
                        //如果这一轮是WA
                        if (result == judge_conf::OJ_WA)
                        {
                            //那么最终结果就是WA
                            problem::result = judge_conf::OJ_WA;
                        }
                        //这一轮是AC或PE
                        else if (problem::result != judge_conf::OJ_PE)
                        {
                            //第一轮或上一轮是AC，结果就是这一轮的情况
                            problem::result = result;
                        }
                        else /* (problem::result == judge_conf::OJ_PE) */
                        {
                            //上一轮是PE，这轮无论是AC还是PE，都是PE
                            problem::result = judge_conf::OJ_PE;
                        }
                        FM_LOG_TRACE("case result: %d, problem result: %d",
                                    result, problem::result);
                    }
                    else
                    {
                        //子进程返回值异常
                        FM_LOG_TRACE("abnormal quit, exit_code: %d", WEXITSTATUS(status));
                        problem::result = judge_conf::OJ_RE_JAVA;
                    }
                    break;
                }

                //判断是否是RE/TLE/OLE等非正常退出的情况
                if ( WIFSIGNALED(status) ||
                     //这是因为超过软/硬限制而收到信号的情况
                    (WIFSTOPPED(status) && WSTOPSIG(status) != SIGTRAP))
                     //这是程序首到信号暂停、但是并未结束的情况
                     //被ptrace暂停的情况(=SIGTRAP)是正常的，需要过滤掉
                {
                    int signo = 0;
                    if (WIFSIGNALED(status))
                    {
                        signo = WTERMSIG(status);
                        FM_LOG_TRACE("child signaled by %d, %s", signo, strsignal(signo));
                    }
                    else
                    {
                        signo = WSTOPSIG(status);
                        FM_LOG_TRACE("child stopped by %d, %s", signo, strsignal(signo));
                    }
                    switch (signo)
                    {
                        //TLE
                        case SIGALRM:
                        case SIGXCPU:
                        case SIGVTALRM:
                        case SIGKILL:
                            FM_LOG_TRACE("time limit exceeded");
                            problem::result = judge_conf::OJ_TLE;
                            break;
                        //OLE
                        case SIGXFSZ:
                            FM_LOG_TRACE("file limit exceeded");
                            problem::result = judge_conf::OJ_OLE;
                            break;
                        //RE的各种情况
                        case SIGSEGV:
                            problem::result = judge_conf::OJ_RE_SEGV;
                            break;
                        case SIGFPE:
                            problem::result = judge_conf::OJ_RE_FPE;
                            break;
                        case SIGBUS:
                            problem::result = judge_conf::OJ_RE_BUS;
                            break;
                        case SIGABRT:
                            problem::result = judge_conf::OJ_RE_ABRT;
                            break;
                        default:
                            problem::result = judge_conf::OJ_RE_UNKNOWN;
                            break;
                    } //end of swtich
                    ptrace(PTRACE_KILL, executive, NULL, NULL);
                    break;
                } //end of  "if (WIFSIGNALED(status) ...)"

                //MLE
                problem::memory_usage = std::max(problem::memory_usage,
                        rused.ru_minflt * (getpagesize() / judge_conf::KILO));
                if (problem::memory_usage > problem::memory_limit)
                {
                    problem::result = judge_conf::OJ_MLE;
                    FM_LOG_TRACE("memory limit exceeded: %d (fault: %d * %d)",
                            problem::memory_usage, rused.ru_minflt, getpagesize());
                    ptrace(PTRACE_KILL, executive, NULL, NULL);
                    break;
                }

                //截获的SYSCALL, 检查
                if (ptrace(PTRACE_GETREGS, executive, NULL, &regs) < 0)
                {
                    FM_LOG_WARNING("ptrace(PTRACE_GETREGS) failed, %d: %s",
                            errno, strerror(errno));
                    exit(judge_conf::EXIT_JUDGE);
                }
#ifdef __i386__
                syscall_id = regs.orig_eax;
#else
                //此处是从bnuoj直接copy过来的，没有用i386平台测试过...
                syscall_id = regs.orig_rax;
#endif
                if (syscall_id > 0 && !is_valid_syscall(problem::lang, syscall_id))
                {
                    FM_LOG_TRACE("restricted function, syscall_id: %d", syscall_id);
                    problem::result = judge_conf::OJ_RF;
                    ptrace(PTRACE_KILL, executive, NULL, NULL);
                    break;
                }

                if (ptrace(PTRACE_SYSCALL, executive, NULL, NULL) < 0)
                {
                    FM_LOG_WARNING("ptrace(PTRACE_SYSCALL) failed");
                    exit(judge_conf::EXIT_JUDGE);
                }
            }
        } //end of fork for judge process


        problem::time_usage += (rused.ru_utime.tv_sec * 1000 +
                                rused.ru_utime.tv_usec / 1000);
        problem::time_usage += (rused.ru_stime.tv_sec * 1000 +
                                rused.ru_stime.tv_usec / 1000);

        if(problem::time_usage > problem::time_limit)
        {
                problem::result = judge_conf::OJ_TLE;
        }

        if (problem::result != judge_conf::OJ_AC &&
            problem::result != judge_conf::OJ_PE)
        {
            FM_LOG_TRACE("not ac/pe, no need to continue");
            if (problem::result == judge_conf::OJ_TLE)
            {
                problem::time_usage = problem::time_limit;
            }
            break;
        }
    }

    output_result(problem::result, problem::memory_usage, problem::time_usage);
    return 0;
}
