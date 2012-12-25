/**
 * Copyright 2012, fish <lyhypacm@gmail.com>
 *
 * Buffered IO Utils base class
 *		All the buffered io class will be it's
 * subclass, it handle file descriptor and 
 * buffer space, we can change the buffer size
 * by changing the value of BUFFER_SIZE.
 */
#include "BufferedIO.h"
#include "logger.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <cstdlib>

BufferedIO::BufferedIO() {
	buffer = new uint8_t[BUFFER_SIZE];
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
		delete buffer;
}

