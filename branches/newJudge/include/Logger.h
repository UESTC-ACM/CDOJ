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

#include "AppException.h"

// logger buffer size
#define BUFFSIZE 1024

/**
 * logger types
 */
enum LoggerFlag {
	FATAL, WARNING, MONITOR, NOTICE, TRADE, DEBUG, INFO
};

/**
 * Simple System Logger
 * 		A simple logger for c/c++
 */

class Logger {
public:
	static Logger* getInstance();
	void open() throw(AppException);
	void close();
	void log(LoggerFlag loggerFlag, const char* fmt, ...);
private:
	Logger();
	static Logger* instance;
	// file pointer, if it's NULL, that means logger is close, otherwise, logger is open
	FILE* fp;
};

#endif // __LOGGER_H__

