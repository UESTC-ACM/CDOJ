#include <cstdlib>
#include <cstdio>
#include <execinfo.h>
#include <signal.h>
#include <exception>
#include "AppException.h"

AppException::AppException() {
	_message = "";
}

AppException::AppException(const std::string& _message)
	: _message(_message) {
}

const char* AppException::message() const {
	return _message.c_str();
}

void AppException::printStackTrace() const {
	static void* array[APPBUFSIZ];
	int nSize = backtrace(array, APPBUFSIZ);
	char** symbols = backtrace_symbols(array, nSize);
	fprintf(stderr, "\033[1m\033[40;31m%s\033[0m\n", message());
	for (int i = 0; i < nSize; ++i)
		fprintf(stderr, "%s\n", symbols[i]);
	free(symbols);
}

