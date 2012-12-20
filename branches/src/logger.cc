#include "logger.h"

static void log_write(int, const char* , const int, const char* , ...);

const int LOG_FATAL		= 0;
const int LOG_WARNING	= 1; 
const int LOG_MONITOR	= 2;
const int LOG_NOTICE	= 3;
const int LOG_TRACE		= 4;
const int LOG_DEBUG		= 5;

static char LOG_LEVEL_NOTE[][10]	=
	{ "FATAL", "WARNING", "MONITOR", "NOTICE", "TRACE", "DEBUG" };

static FILE* 	log_fp			= NULL;
static char* 	log_fileName	= NULL;
static bool		log_opened		= false;

#define LOGBUFSIZ	8192
static char	log_buffer[LOGBUFSIZ];
static char	log_extra_info[LOGBUFSIZ];

#ifdef LOG_ON
bool log_open(const char* fileName) {
	if (log_opened) {
		fprintf(stderr, "logger: log already opened\n");
		return false;
	}
	int len = strlen(fileName);
	log_fileName = (char* )malloc(sizeof(char) * (len + 1));
	strcpy(log_fileName, fileName);
	log_fp = fopen(log_fileName, "a");
	if (log_fp == NULL) {
		fprintf(stderr, "log_file: %s", log_fileName);
		perror("can't not open log file");
		exit(1);
	}
	
	atexit(log_close);
	log_opened = true;
	log_extra_info[0] = 0;
	OJ_LOG_NOTICE("log_open");
	return true;
}

#endif // LOG_ON

void log_close() {
	if (log_opened) {
		OJ_LOG_TRACE("log_close");
		fclose(log_fp);
		free(log_fileName);
		log_fp			= NULL;
		log_fileName	= NULL;
		log_opened		= false;
	}
}

static void log_write(int level, const char* file, const int line, const char* fmt, ...) {
	if (log_opened == false) {
		fprintf(stderr, "log_open not called yet\n");
		exit(1);
	}
	static char buffer[LOGBUFSIZ];
	static char dateTime[100];
	static char line_str[20];
	static time_t now;
	now = time(NULL);

	strftime(dateTime, 99, "%Y-%m-%d %H:%M:%S", localtime(&now));
	snprintf(line_str, 19, "%d", line);
	va_list ap;
	va_start(ap, fmt);
	vsnprintf(log_buffer, LOGBUFSIZ, fmt, ap);
	va_end(ap);

	size_t count = snprintf(buffer, LOGBUFSIZ,
			"%s\x7 [%s]\x7 [%s:%d]%s\x7 %s\x7\n",
			LOG_LEVEL_NOTE[level], dateTime, file, line, log_extra_info, log_buffer);
	int log_fd = log_fp->_fileno;
	if (flock(log_fd, LOCK_EX) == 0) {
		if (write(log_fd, buffer, count) < 0) {
			perror("write error");
			exit(1);
		}
		flock(log_fd, LOCK_UN);
	} else {
		perror("flock error");
		exit(1);
	}
}

void log_add_info(const char* info) {
	int len = strlen(log_extra_info);
	snprintf(log_extra_info + len, LOGBUFSIZ - len, "\x7 [%s]", info);
}

