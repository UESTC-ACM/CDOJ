#include "logger.h"
#include "AppException.h"
#include "BufferedIO.h"

int main(int argc, char* argv[]) {
	log_open("log/core.log");
	OJ_LOG_NOTICE("core begin with argc = %d argv address %p", argc, argv);
	BufferedIO* bufferIO = new BufferedIO(0);
	try {
		printf("%p\n", bufferIO->buffer);
		throw AppException("catched exception");
	} catch (const AppException& e) {
		e.printStackTrace();
	}
	if (bufferIO != NULL)
		delete bufferIO;
	log_close();
	return 0;
}

