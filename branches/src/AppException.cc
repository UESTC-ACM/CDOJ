/**
 * Copyright 2012, fish <lyhypacm@gmail.com>
 *
 * Application Exception Class
 *		all customized exception of this project
 * will use this class to deal with customized
 * actions.
 */
#include <cstdlib>
#include <cstdio>
#include <execinfo.h>
#include <signal.h>
#include <exception>
#include "AppException.h"

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

