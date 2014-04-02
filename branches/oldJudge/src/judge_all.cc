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
int int_ignored;

int main(int argc, char *argv[], char *envp[])
{
    nice(10);
    parse_arguments(argc, argv);

    log_open(judge_conf::log_file.c_str());
    FM_LOG_DEBUG("\n\x1b[31m-----a new start-----\x1b[0m");
    if (geteuid() != 0)
    {
        FM_LOG_FATAL("euid != 0, please run as root, or set suid bit(chmod +4755)");
        exit(judge_conf::EXIT_UNPRIVILEGED);
    }
    judge_conf::judge_time_limit += problem::time_limit;
    if (EXIT_SUCCESS != malarm(ITIMER_REAL, judge_conf::judge_time_limit))
    {
        FM_LOG_WARNING("set alarm for judge failed, %d: %s", errno, strerror(errno));
        exit(judge_conf::EXIT_VERY_FIRST);
    }
    signal(SIGALRM, timeout);

	if (problem::needCompile) {
		//compile
		pid_t compiler = fork();
		int status = 0;
		if (compiler < 0)
		{
		    FM_LOG_WARNING("error fork compiler");
		    exit(judge_conf::EXIT_COMPILE);
		}
		else if (compiler == 0)
		{
			// run compiler
		    log_add_info("compiler");
        static char buffer[1024];
        getcwd(buffer, 1024);
        FM_LOG_WARNING("cwd = %s", buffer);
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
		            FM_LOG_TRACE("start: gcc -static -w -O2 -DONLINE_JUDGE -o %s %s",
		                    problem::exec_file.c_str(), problem::source_file.c_str());
		            execlp("/usr/bin/gcc", "gcc", "-static", "-w", "-O2", "-DONLINE_JUDGE",
		                   "-o", problem::exec_file.c_str(),
		                   problem::source_file.c_str(),
		                   NULL);
		            break;

		        case judge_conf::LANG_CPP:
		            FM_LOG_TRACE("start: g++ -static -w -O2 -DONLINE_JUDGE -o %s %s",
		                    problem::exec_file.c_str(), problem::source_file.c_str());
		            execlp("/usr/bin/g++", "g++", "-static", "-w", "-O2", "-DONLINE_JUDGE",
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

			// execlp error
		    FM_LOG_WARNING("exec error");
		    exit(judge_conf::EXIT_COMPILE);
		}
		else
		{
			// Judger
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
	}

	//Judge----------------------------------------------------------------------
    struct rusage rused;
    //int_ignored = fscanf(fp, " %1023[^\r\n]", line);
    //problem::input_file      = problem::data_dir + "/" + line + ".in";
    //problem::output_file_std = problem::data_dir + "/" + line + ".out";
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
		// child process
        log_add_info("executive");

		//io redirect
        io_redirect();

		// chroot & setuid
        set_security_option();

		// set memory, time and output limit etc.
        set_limit();

        FM_LOG_DEBUG("tl: %d, tu: %d, tla: %d",
                problem::time_limit, problem::time_usage,
                judge_conf::time_limit_addtion);
        int real_time_limit = problem::time_limit
                            - problem::time_usage
                            + judge_conf::time_limit_addtion; //time fix
		// set real time alarm
        if (EXIT_SUCCESS != malarm(ITIMER_REAL, real_time_limit))
        {
            FM_LOG_WARNING("malarm failed");
            exit(judge_conf::EXIT_PRE_JUDGE);
        }

        FM_LOG_TRACE("begin executive: %s", problem::exec_file.c_str());
        log_close();
        if (ptrace(PTRACE_TRACEME, 0, NULL, NULL) < 0)
        {
            exit(judge_conf::EXIT_PRE_JUDGE_PTRACE);
        }
		// load program
        if (problem::lang != judge_conf::LANG_JAVA)
        {
            execl("./a.out", "a.out", NULL);
        }
        else
        {
            //execlp("java", "java", "-Xms256m", "-Xmx512m", "-Xss8m", "-Djava.security.manager", "-Djava.security.policy==../../java.policy", "Main", NULL);
            execlp("java", "java", "-Djava.security.manager", "-Djava.security.policy=../../java.policy", "-cp", problem::temp_dir.c_str(), "Main", NULL);
        }

		// execlp error
        exit(judge_conf::EXIT_PRE_JUDGE_EXECLP);
    }
    else
    {
		//Judger
        int status          = 0;
        int syscall_id      = 0;
        struct user_regs_struct regs;

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
                if (problem::lang != judge_conf::LANG_JAVA
                    || WEXITSTATUS(status) == EXIT_SUCCESS)
                {
					// AC PE WA
                    FM_LOG_TRACE("normal quit");
                    int result;
                    if (problem::spj)
                    {
						// use SPJ
                        result = oj_compare_output_spj( problem::input_file,
                        			       problem::output_file_std,
                                           problem::stdout_file_executive,
                                           problem::spj_exe_file,
                                           problem::stdout_file_spj);
                    }
                    else
                    {
						//compare file
                        result = oj_compare_output(problem::output_file_std,
                                                   problem::stdout_file_executive);
                    }
					// WA
                    if (result == judge_conf::OJ_WA)
                    {
						// set WA
                        problem::result = judge_conf::OJ_WA;
                    }
					//AC or PE
                    else if (problem::result != judge_conf::OJ_PE)
                    {
                        problem::result = result;
                    }
                    else /* (problem::result == judge_conf::OJ_PE) */
                    {
						// PE
                        problem::result = judge_conf::OJ_PE;
                    }
                    FM_LOG_TRACE("case result: %d, problem result: %d",
                                result, problem::result);
                }
                else
                {
					// null 0 return
                    FM_LOG_TRACE("abnormal quit, exit_code: %d", WEXITSTATUS(status));
                    problem::result = judge_conf::OJ_RE_JAVA;
                }
                break;
            }

			// RE/TLE/OLE
            if ( WIFSIGNALED(status) ||
                (WIFSTOPPED(status) && WSTOPSIG(status) != SIGTRAP))
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
                    //RE
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

			// check syscall
            if (ptrace(PTRACE_GETREGS, executive, NULL, &regs) < 0)
            {
                FM_LOG_WARNING("ptrace(PTRACE_GETREGS) failed, %d: %s",
                        errno, strerror(errno));
                exit(judge_conf::EXIT_JUDGE);
            }
#ifdef __i386__
            syscall_id = regs.orig_eax;
#else
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
    }

    output_result(problem::result, problem::memory_usage, problem::time_usage);
    return 0;
}
