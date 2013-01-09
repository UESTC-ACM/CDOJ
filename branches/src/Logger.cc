/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2012  fish <@link lyhypacm@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

#include <cstring>
#include <ctime>
#include <cstdlib>
#include <unistd.h>
#include <sys/file.h>
#include <cstdarg>
#include <error.h>
#include "Logger.h"
#include "Settings.h"

Logger* Logger::instance;
static char LOG_LEVEL_NOTE[][10]	=
	{ "FATAL", "WARNING", "MONITOR", "NOTICE", "TRACE", "DEBUG", "INFO" };

/**
 * Simple System Logger
 * 		A simple logger for c/c++
 */
Logger::Logger() {
	fp = NULL;
}

Logger* Logger::getInstance(void) {
	if (Logger::instance == NULL)
		Logger::instance = new Logger();
	return instance;
}

void Logger::open(void) throw(AppException) {
	if (fp == NULL)
		fp = fopen(Settings::getInstance()->getLogFile().c_str(), "a");
}

void Logger::close(void) {
	if (fp != NULL)
		fclose(fp);
}

void Logger::log(LoggerFlag flag, const char* fmt, ...) {
	if (fp != NULL) {
		static char buffer[BUFFSIZE];
		static char log_buffer[BUFFSIZE];
		static char dateTime[100];
		static time_t now;
		now = time(NULL);

		strftime(dateTime, 99, "%Y-%m-%d %H:%M:%S", localtime(&now));
		va_list ap;
		va_start(ap, fmt);
		vsnprintf(log_buffer, BUFFSIZE, fmt, ap);
		va_end(ap);

		size_t count = snprintf(buffer, BUFFSIZE,
			"[%s][%s]: %s\n", LOG_LEVEL_NOTE[flag], dateTime, log_buffer);
		int fd = fp->_fileno;
		if (flock(fd, LOCK_EX) == 0) {
			if (write(fd, buffer, count) < 0) {
				perror("write error");
				exit(1);
			}
			flock(fd, LOCK_UN);
		} else {
			perror("flock error");
			exit(1);
		}
	}
}

