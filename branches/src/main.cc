#include "logger.h"
#include "AppException.h"
#include "BufferedIO.h"

int main(int argc, char* argv[]) {
	log_open("log/core.log");
	OJ_LOG_NOTICE("core begin with argc = %d argv address %p\n",
		argc, argv);
	try {
		BufferedIO io(0);
		printf("%p\n", io.buffer);
		throw AppException("catched exception");
	} catch (const AppException& e) {
		e.printStackTrace();
	}
	return 0;
}

