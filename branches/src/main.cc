#include "logger.h"
#include "AppException.h"

int main() {
	try {
		throw AppException("catched exception");
	} catch (const AppException& e) {
		e.printStackTrace();
	}
	return 0;
}

