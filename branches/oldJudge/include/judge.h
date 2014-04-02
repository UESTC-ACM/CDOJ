#ifndef __JUDGE_H__
#define __JUDGE_H__

#include <iostream>
#include <string>
#include <cstdio>
#include <cstdlib>
#include <cstring>
#include <cctype>
extern "C"
{
#include <unistd.h>
#include <sys/time.h>
#include <sys/resource.h>
#include <sys/signal.h>
#include <sys/wait.h>
#include <errno.h>
#include <pwd.h>
#include <sys/types.h>
#include "logger.h"
}

namespace judge_conf {

//日志文件路径
std::string log_file;

//judge本身的时限(ms)
int judge_time_limit            = 15000;

//编译限制(ms)
int compile_time_limit          = 5000;

//编译限制(MB)
int compile_mem_limit	          = 300;

//SPJ时间限制(ms)
int spj_time_limit              = 5000;

//程序运行的栈空间大小(KB)
int stack_size_limit            = 8192;

//参照Oak的设置，附加一段时间到time_limit里，不把运行时间限制得太死
int time_limit_addtion          = 314;

int java_time_factor            = 2;

int java_memory_factor          = 2;

//--------------Constants-------------

//输入文件列表文件名
const std::string data_filename = "data.txt";

//OJ结果代码
const int OJ_WAIT           = 0; //Queue
const int OJ_AC             = 1; //Accepted
const int OJ_PE             = 2; //Presentation Error
const int OJ_TLE            = 3; //Time Limit Exceeded
const int OJ_MLE            = 4; //Memory Limit Exceeded
const int OJ_WA             = 5; //Wrong Answer
const int OJ_OLE            = 6; //Output Limit Exceeded
const int OJ_CE             = 7; //Compilation Error
const int OJ_RE_SEGV        = 8; //Segment Violation
const int OJ_RE_FPE         = 9; //FPU Error
const int OJ_RE_BUS         = 10;//Bus Error
const int OJ_RE_ABRT        = 11;//Abort
const int OJ_RE_UNKNOWN     = 12;//Unknow
const int OJ_RF             = 13;//Restricted Function
const int OJ_SE             = 14;//System Error
const int OJ_RE_JAVA        = 15;//JAVA Run Time Exception

//一些常量
const int KILO              = 1024;         // 1K
const int MEGA              = KILO * KILO;  // 1M
const int GIGA              = KILO * MEGA;  // 1G

const int GCC_COMPILE_ERROR = 1;

//退出原因
const int EXIT_OK               = 0;
const int EXIT_UNPRIVILEGED     = 1;
const int EXIT_BAD_PARAM        = 3;
const int EXIT_VERY_FIRST       = 4;
const int EXIT_COMPILE          = 6;
const int EXIT_PRE_JUDGE        = 9;
const int EXIT_PRE_JUDGE_PTRACE = 10;
const int EXIT_PRE_JUDGE_EXECLP = 11;
const int EXIT_SET_LIMIT        = 15;
const int EXIT_SET_SECURITY     = 17;
const int EXIT_JUDGE            = 21;
const int EXIT_COMPARE          = 27;
const int EXIT_COMPARE_SPJ      = 30;
const int EXIT_COMPARE_SPJ_FORK = 31;
const int EXIT_TIMEOUT          = 36;
const int EXIT_UNKNOWN          = 127;

const std::string languages[]    = {"unknown", "c", "c++", "java", "pascal"};
const int LANG_UNKNOWN      = 0;
const int LANG_C            = 1;
const int LANG_CPP          = 2;
const int LANG_JAVA         = 3;
const int LANG_PASCAL       = 4;
};

namespace problem {
int id                  = 0;
int lang                = 0;
int time_limit          = 1000;
int memory_limit        = 65536;
int output_limit        = 8192;
int result              = 0;
int status;

long memory_usage       = 0;
int time_usage          = 0;

bool spj                = false;

bool needCompile		= false;

std::string uid;

std::string temp_dir;
std::string data_dir;

std::string stdout_file_compiler;
std::string stderr_file_compiler;

std::string source_file;
std::string exec_file;
std::string spj_exe_file;

std::string input_file;
std::string output_file_std;

std::string stdout_file_executive;
std::string stderr_file_executive;


std::string stdout_file_spj;

void dump_to_log() {
  FM_LOG_DEBUG("--problem information--");
  FM_LOG_DEBUG("id            %d", id);
  FM_LOG_DEBUG("lang          %s", judge_conf::languages[lang].c_str());
  FM_LOG_DEBUG("time_limit    %d", time_limit);
  FM_LOG_DEBUG("memory_limit  %d", memory_limit);
  FM_LOG_DEBUG("output_limit  %d", output_limit);
  FM_LOG_DEBUG("spj           %s", spj ? "true" : "false");
  FM_LOG_DEBUG("temp_dir      %s", temp_dir.c_str());
  FM_LOG_DEBUG("data_dir      %s", data_dir.c_str());
  FM_LOG_DEBUG("source_file   %s", source_file.c_str());
  FM_LOG_DEBUG("need compile  %s", needCompile ? "true" : "false");
  FM_LOG_DEBUG("");
}
};

void parse_arguments(int argc, char *argv[]) {
  int opt;
  extern char *optarg;

  while ((opt = getopt(argc, argv, "l:u:s:n:D:d:t:m:o:I:O:T:L:SC")) != -1) {
    switch (opt) {
    case 'u': // task ID
      problem::uid          	= optarg;
      break;
    case 's': // Program source path
      problem::source_file  	= optarg;
      break;
    case 'n': // Problem ID
      problem::id           	= atoi(optarg);
      break;
    case 'D': // Data directory path
      problem::data_dir     	= optarg;
      break;
    case 'd': // Work directory
      problem::temp_dir     	= optarg;
      break;
    case 't': // Time limit
      problem::time_limit   	= atoi(optarg);
      break;
    case 'm': // Memory limit
      problem::memory_limit 	= atoi(optarg);
      break;
    case 'o': // Output limit
      problem::output_limit 	= atoi(optarg);
      break;
    case 'S': // SPJ option
      problem::spj          	= true;
      break;
    case 'l': // Language
      problem::lang         	= atoi(optarg);
      break;
    case 'I': // Input file
      problem::input_file		= optarg;
      break;
    case 'O': // Output file
      problem::output_file_std	= optarg;
      break;
    case 'C': // Compile option
      problem::needCompile		= true;
      break;
    case 'T': // SPJ time
      judge_conf::spj_time_limit = atoi(optarg);
      break;
    case 'L': // Log file
      judge_conf::log_file = optarg;
      break;
    default:
      FM_LOG_WARNING("unknown option provided: -%c %s", opt, optarg);
      exit(judge_conf::EXIT_BAD_PARAM);
    }
  }
  problem::source_file = problem::temp_dir + "/" + problem::source_file;
  switch (problem::lang) {
  case judge_conf::LANG_C:
  case judge_conf::LANG_CPP:
  case judge_conf::LANG_PASCAL:
  case judge_conf::LANG_JAVA:
    break;
  default:
    FM_LOG_WARNING("Bad language specified: %d", problem::lang);
    exit(judge_conf::EXIT_BAD_PARAM);
  }
  //problem::data_file              = problem::data_dir + "/" + judge_conf::data_filename;
  problem::exec_file              = problem::temp_dir + "/" + "a.out";
  if(problem::lang == judge_conf::LANG_JAVA) {
    problem::exec_file          = problem::temp_dir + "/" + "Main";
    problem::memory_limit      *= judge_conf::java_memory_factor;
    problem::time_limit        *= judge_conf::java_time_factor;
  }
  problem::stdout_file_compiler   = problem::temp_dir + "/" + "stdout_compiler.txt";
  problem::stderr_file_compiler   = problem::temp_dir + "/" + "stderr_compiler.txt";
  problem::stdout_file_executive  = problem::temp_dir + "/" + "stdout_executive.txt";
  problem::stderr_file_executive  = problem::temp_dir + "/" + "stderr_executive.txt";
  if (problem::spj == true) {
    problem::spj_exe_file       = problem::data_dir + "/" + "spj";
    problem::stdout_file_spj    = problem::temp_dir + "/" + "stdout_spj.txt";
  }
  if (false == problem::uid.empty()) {
    // Log uid into log file
    log_add_info(("uid:" + problem::uid).c_str());
  }
  problem::dump_to_log();
}

void timeout(int signo) {
  if (signo == SIGALRM) {
    exit(judge_conf::EXIT_TIMEOUT);
  }
}

//a simpler interface for setitimer
//which can be ITIMER_REAL, ITIMER_VIRTUAL, ITIMER_PROF
int malarm(int which, int milliseconds) {
  struct itimerval t;
  FM_LOG_TRACE("malarm: %d", milliseconds);
  t.it_value.tv_sec       = milliseconds / 1000;
  t.it_value.tv_usec      = milliseconds % 1000 * 1000;
  t.it_interval.tv_sec    = 0;
  t.it_interval.tv_usec   = 0;
  return setitimer(which, &t, NULL);
}

void io_redirect() {
  //io_redirect
  stdin  = freopen(problem::input_file.c_str(), "r", stdin);
  stdout = freopen(problem::stdout_file_executive.c_str(), "w", stdout);
  stderr = freopen(problem::stderr_file_executive.c_str(), "w", stderr);
  if (stdin == NULL || stdout == NULL || stderr == NULL) {
    FM_LOG_WARNING("error freopen: stdin(%p) stdout(%p), stderr(%p)",
                   stdin, stdout, stderr);
    exit(judge_conf::EXIT_PRE_JUDGE);
  }
  FM_LOG_TRACE("io redirect ok!");
}


void set_limit() {

  rlimit lim;

  // Set time limit round up
  lim.rlim_max = (problem::time_limit - problem::time_usage + 999) / 1000 + 1; //硬限制
  lim.rlim_cur = lim.rlim_max; //软限制
  if (setrlimit(RLIMIT_CPU, &lim) < 0) {
    FM_LOG_WARNING("error setrlimit for RLIMIT_CPU");
    exit(judge_conf::EXIT_SET_LIMIT);
  }

  /*
   	// Memory control
      lim.rlim_max = memlimit * judge_conf::KILO;
      lim.rlim_cur = memlimit * judge_conf::KILO;
      if (setrlimit(RLIMIT_AS, &lim) < 0)
      {
          perror("setrlimit");
          exit(judge_conf::EXIT_SET_LIMIT);
      }
  */

  // Stack space
  lim.rlim_max = judge_conf::stack_size_limit * judge_conf::KILO;
  lim.rlim_cur = lim.rlim_max;

  if (setrlimit(RLIMIT_STACK, &lim) < 0) {
    FM_LOG_WARNING("setrlimit RLIMIT_STACK failed");
    exit(judge_conf::EXIT_SET_LIMIT);
  }

  // Output limit
  lim.rlim_max = problem::output_limit * judge_conf::KILO;
  lim.rlim_cur = lim.rlim_max;
  if (setrlimit(RLIMIT_FSIZE, &lim) < 0) {
    FM_LOG_WARNING("setrlimit RLIMIT_FSIZE failed");
    exit(judge_conf::EXIT_SET_LIMIT);
  }

  FM_LOG_TRACE("set limit ok");
}

void set_compile_limit() {
  if(problem::lang == judge_conf::LANG_JAVA) return;

  int cpu = (judge_conf::compile_time_limit + 999) / 1000;
  int mem = judge_conf::compile_mem_limit * judge_conf::MEGA;

  rlimit lim;

  lim.rlim_cur = lim.rlim_max = cpu;
  if (setrlimit(RLIMIT_CPU, &lim) < 0) {
    FM_LOG_WARNING("error setrlimit for RLIMIT_CPU");
    exit(judge_conf::EXIT_SET_LIMIT);
  }

  lim.rlim_cur = lim.rlim_max = mem;
  if (setrlimit(RLIMIT_AS, &lim) < 0) {
    perror("setrlimit");
    exit(judge_conf::EXIT_SET_LIMIT);
  }

  /*
  //Stack space control
  lim.rlim_max = judge_conf::stack_size_limit * judge_conf::KILO;
  lim.rlim_cur = lim.rlim_max;

  if (setrlimit(RLIMIT_STACK, &lim) < 0)
  {
      FM_LOG_WARNING("setrlimit RLIMIT_STACK failed");
      exit(judge_conf::EXIT_SET_LIMIT);
  }
  */

  /*
  // Output limit control
  lim.rlim_max = problem::output_limit * judge_conf::KILO;
  lim.rlim_cur = lim.rlim_max;
  if (setrlimit(RLIMIT_FSIZE, &lim) < 0)
  {
      FM_LOG_WARNING("setrlimit RLIMIT_FSIZE failed");
      exit(judge_conf::EXIT_SET_LIMIT);
  }
  */

  FM_LOG_TRACE("set compile limit ok");
}

/*
 * chroot & setuid
 */
void set_security_option() {
  struct passwd *nobody = getpwnam("nobody");
  if (nobody == NULL) {
    FM_LOG_WARNING("no user named 'nobody'? %d: %s", errno, strerror(errno));
    exit(judge_conf::EXIT_SET_SECURITY);
  }

  //chdir + chroot
  if (EXIT_SUCCESS != chdir(problem::temp_dir.c_str())) {
    FM_LOG_WARNING("chdir(%s) failed, %d: %s",
                   problem::temp_dir.c_str(), errno, strerror(errno));
    exit(judge_conf::EXIT_SET_SECURITY);
  }

  char cwd[1024], *tmp = getcwd(cwd, 1024);
  if (tmp == NULL) {
    FM_LOG_WARNING("getcwd failed, %d: %s", errno, strerror(errno));
    exit(judge_conf::EXIT_SET_SECURITY);
  }
  FM_LOG_DEBUG("cwd: %s", cwd);
  if(problem::lang != judge_conf::LANG_JAVA) {
    if (EXIT_SUCCESS != chroot(cwd)) {
      FM_LOG_WARNING("chroot(%s) failed, %d: %s",
                     cwd, errno, strerror(errno));
      exit(judge_conf::EXIT_SET_SECURITY);
    }
  }


  //setuid
  if(problem::lang != judge_conf::LANG_JAVA) {
    if (EXIT_SUCCESS != setuid(nobody->pw_uid)) {
      FM_LOG_WARNING("setuid(%d) failed, %d: %s",
                     nobody->pw_uid, errno, strerror(errno));
      exit(judge_conf::EXIT_SET_SECURITY);
    }
  }

  FM_LOG_TRACE("set_security_option ok");
}

// copied from
// http://csourcesearch.net/c/fid471AEC75A44B4EB7F79BAB9F1C5DE7CA616177E5.aspx
int strincmp(const char *s1, const char *s2, int n) {
  /* case insensitive comparison */
  int d;
  while (--n >= 0) {
#ifdef ASCII_CTYPE
    if (!isascii(*s1) || !isascii(*s2))
      d = *s1 - *s2;
    else
#endif
      d = (tolower((unsigned char)*s1) - tolower((unsigned char)*s2));
    if ( d != 0 || *s1 == '\0' || *s2 == '\0' )
      return d;
    ++s1;
    ++s2;
  }
  return(0);
}

// Run spj
int oj_compare_output_spj(
  std::string file_in,  //std input
  std::string file_std, //std output
  std::string file_exec, //user output
  std::string spj_exec,  //path of spj
  std::string file_spj) { //spj output
  FM_LOG_TRACE("start compare spj");
  pid_t pid_spj = fork();
  int status = 0;
  if (pid_spj < 0) {
    FM_LOG_WARNING("fork for spj failed");
    exit(judge_conf::EXIT_COMPARE_SPJ);
  } else if (pid_spj == 0) {
    log_add_info("spj");
    stdin  = freopen(file_exec.c_str(), "r", stdin);
    stdout = freopen(file_spj.c_str(),  "w", stdout);
    if (stdin == NULL || stdout == NULL) {
      FM_LOG_WARNING("failed to open files: stdin(%p), stdout(%p)", stdin, stdout);
      exit(judge_conf::EXIT_COMPARE_SPJ);
    }
    // Set spj timeout
    if (EXIT_SUCCESS == malarm(ITIMER_REAL, judge_conf::spj_time_limit)) {
      FM_LOG_TRACE("load spj.exe");
      log_close();
      execlp(spj_exec.c_str(), spj_exec.c_str(), file_in.c_str(), file_std.c_str(), NULL);
      exit(judge_conf::EXIT_COMPARE_SPJ_FORK);
    } else {
      FM_LOG_WARNING("malarm failed");
      exit(judge_conf::EXIT_COMPARE_SPJ);
    }
  } else {
    if (wait4(pid_spj, &status, 0, NULL) < 0) {
      FM_LOG_WARNING("wait4 failed, %d:%s", errno, strerror(errno));
      exit(judge_conf::EXIT_COMPARE_SPJ);
    }
    if (WIFEXITED(status)) {
      // exit successfully
      if (WEXITSTATUS(status) == EXIT_SUCCESS) {
        // return 0
        FILE *fp = fopen(file_spj.c_str(), "r");
        if (fp == NULL) {
          FM_LOG_WARNING("open stdout_file_spj failed");
          exit(judge_conf::EXIT_COMPARE_SPJ);
        }
        char buff[20];
        fscanf(fp, "%19s", buff);
        FM_LOG_TRACE("spj.exe result: %s", buff);
        if (strincmp(buff, "AC", 2) == 0) {
          return judge_conf::OJ_AC;
        } else if (strincmp(buff, "PE", 2) == 0) {
          return judge_conf::OJ_PE;
        } else if (strincmp(buff, "WA", 2) == 0) {
          return judge_conf::OJ_WA;
        } else {
          FM_LOG_WARNING("unknown spj output");
          exit(judge_conf::EXIT_COMPARE_SPJ);
        }
      } else {
        // return not 0
        FM_LOG_WARNING("spj.exe abnormal termination, "
                       "exit code: %d", WEXITSTATUS(status));
      }
    } else if (WIFSIGNALED(status) && WTERMSIG(status) == SIGALRM) {
      // recv SIGNALRM
      FM_LOG_WARNING("spj.exe: time out");
    } else {
      // spj RE
      FM_LOG_WARNING("unkown termination, status = %d", status);
    }
  }
  exit(judge_conf::EXIT_COMPARE_SPJ);
}

int oj_compare_output(std::string file_std, std::string file_exec) {
  FM_LOG_TRACE("start compare");
  FILE *fp_std = fopen(file_std.c_str(), "r");
  if (fp_std == NULL) {
    FM_LOG_WARNING("open standard output failed");
    exit(judge_conf::EXIT_COMPARE);
  }
  FILE *fp_exe = fopen(file_exec.c_str(), "r");
  if (fp_exe == NULL) {
    FM_LOG_WARNING("open executive's output failed");
    exit(judge_conf::EXIT_COMPARE);
  }
  int a, b, Na = 0, Nb = 0;
  enum {
    AC = judge_conf::OJ_AC,
    PE = judge_conf::OJ_PE,
    WA = judge_conf::OJ_WA
  } status = AC;
  while (true) {
    while((a = fgetc(fp_std)) == '\r');
    while((b = fgetc(fp_exe)) == '\r');
    Na++, Nb++;

    // deal with '\r' and '\n'
    if (a == '\r') a = '\n';
    if (b == '\r') b = '\n';
#define is_space_char(a) ((a == ' ') || (a == '\t') || (a == '\n'))

    if (feof(fp_std) && feof(fp_exe)) {
      break;
    } else if (feof(fp_std) || feof(fp_exe)) {
      // deal with tailing white spaces
      FM_LOG_TRACE("one file ended");
      FILE *fp_tmp;
      if (feof(fp_std)) {
        if (!is_space_char(b)) {
          FM_LOG_TRACE("WA exe['%c':0x%x @%d]", b, b, Nb);
          status = WA;
          break;
        }
        fp_tmp = fp_exe;
      } else { /* feof(fp_exe) */
        if (!is_space_char(a)) {
          FM_LOG_TRACE("WA std['%c':0x%x @%d]", a, a, Na);
          status = WA;
          break;
        }
        fp_tmp = fp_std;
      }
      int c;
      while (c = fgetc(fp_tmp), c != EOF) {
        if (c == '\r') c = '\n';
        if (!is_space_char(c)) {
          FM_LOG_TRACE("WA ['%c':0x%x]", c, c);
          status = WA;
          break;
        }
      }
      break;
    }


    if (a != b) {
      status = PE;
      if (is_space_char(a) && is_space_char(b)) {
        continue;
      }
      if (is_space_char(a)) {
        ungetc(b, fp_exe);
        Nb--;
      } else if (is_space_char(b)) {
        ungetc(a, fp_std);
        Na--;
      } else {
        FM_LOG_TRACE("WA ['%c':0x%x @%d] : ['%c':0x%x @%d]", a, a, Na, b, b, Nb);
        status = WA;
        break;
      }
    }
  }
  fclose(fp_std);
  fclose(fp_exe);
  FM_LOG_TRACE("compare over, result = %s",
               status == AC ? "AC" : (status == PE ? "PE" : "WA"));
  return status;
}

#include "rf_table.h"
// deal with sys call
static bool in_syscall = true;
bool is_valid_syscall(int lang, int syscall_id) {
  in_syscall = !in_syscall;
  //FM_LOG_DEBUG("syscall: %d, %s, count: %d", syscall_id, in_syscall?"in":"out", RF_table[syscall_id]);
  if (RF_table[syscall_id] == 0) {
    return false;
  } else if (RF_table[syscall_id] > 0) {
    if (in_syscall == false)
      RF_table[syscall_id]--;
  } else {
    ;
  }
  return true;
}

//Output result
void output_result(int result, int memory_usage = 0, int time_usage = 0) {
  FM_LOG_TRACE("result: %d, %dKB, %dms", result, memory_usage, time_usage);
  printf("%d %d %d", result, memory_usage, time_usage);
}
#endif
