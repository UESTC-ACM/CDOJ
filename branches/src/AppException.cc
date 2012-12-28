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
#include <cstdlib>
#include <cstdio>
#include <execinfo.h>
#include <signal.h>
#include <exception>
#include "AppException.h"

/**
 * Application Exception Class
 *		all customized exception of this project
 * will use this class to deal with customized
 * actions.
 */

AppException::AppException(void) {
	_message = "";
}

AppException::AppException(const std::string& _message)
	: _message(_message) {
}

AppException::~AppException(void) {
}

const char* AppException::message(void) const {
	return _message.c_str();
}

void AppException::printStackTrace(void) const {
	static void* array[APPBUFSIZ];
	int nSize = backtrace(array, APPBUFSIZ);
	char** symbols = backtrace_symbols(array, nSize);
	fprintf(stderr, "\033[1m\033[40;31m%s\033[0m\n", message());
	for (int i = 0; i < nSize; ++i)
		fprintf(stderr, "%s\n", symbols[i]);
	free(symbols);
}

