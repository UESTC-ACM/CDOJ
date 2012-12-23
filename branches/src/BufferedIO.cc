#include "BufferedIO.h"
#include "logger.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <cstdlib>

BufferedIO::BufferedIO() {
	buffer = (uint8_t *)malloc(sizeof(uint8_t) * BUFFER_SIZE);
	OJ_LOG_DEBUG("Passed");
}

BufferedIO::BufferedIO(int fd)
	: fd(fd) {
	BufferedIO();
}

BufferedIO::BufferedIO(const char* fileName) throw(AppException) {
	fd = open(fileName, O_RDONLY);
	if (fd < 0)
		throw AppException("cannot open input file.");
}

BufferedIO::~BufferedIO() {
	if (buffer != NULL)
		free(buffer);
}

