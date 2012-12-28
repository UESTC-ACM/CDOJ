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
#ifndef __LOGGER_H__
#define __LOGGER_H__

/**
 * Simple System Logger
 * 		A simple logger for c/c++
 */

/**
 * if you want to open the log, open the LOG_ON option
 */
//#define LOG_ON

#include <cstdio>
#include <cstdlib>
#include <ctime>
#include <cstring>
#include <cstdarg>
#include <unistd.h>
#include <error.h>
#include <sys/file.h>

#ifdef LOG_ON
bool log_open(const char* fileName);
void log_close(void);
void log_add_info(const char* info);
void log_write(int, const char* , const int, const char* , ...);

extern const int LOG_FATAL;
extern const int LOG_WARNING;
extern const int LOG_MONITOR;
extern const int LOG_NOTICE;
extern const int LOG_TRACE;
extern const int LOG_DEBUG;

#define OJ_LOG_FATAL(x...)		log_write(LOG_FATAL, __FILE__, __LINE__, ##x)
#define OJ_LOG_WARNING(x...)	log_write(LOG_WARNING, __FILE__, __LINE__, ##x)
#define OJ_LOG_MONITOR(x...)	log_write(LOG_MONITOR, __FILE__, __LINE__, ##x)
#define OJ_LOG_NOTICE(x...)		log_write(LOG_NOTICE, __FILE__, __LINE__, ##x)
#define OJ_LOG_TRACE(x...)		log_write(LOG_TRACE, __FILE__, __LINE__, ##x)
#define OJ_LOG_DEBUG(x...)		log_write(LOG_DEBUG, __FILE__, __LINE__, ##x)
#else // LOG_ON is not defined
#define OJ_LOG_FATAL(x...)
#define OJ_LOG_WARNING(x...)
#define OJ_LOG_MONITOR(x...)
#define OJ_LOG_NOTICE(x...)
#define OJ_LOG_TRACE(x...)
#define OJ_LOG_DEBUG(x...)
#define log_open(x...)
#define log_close()
#endif // LOG_ON

#endif // __LOGGER_H__

