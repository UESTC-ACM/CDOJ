/**
 * Copyright 2012, fish <lyhypacm@gmail.com>
 *
 * Buffered IO class implementation
 *		we use this class object to read data from
 * a file whose name or descriptor has been pre-defined.
 */
#include "BufferedReader.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

BufferedReader::BufferedReader(const int& fd)
	: BufferedIO(fd) {
}

BufferedReader::BufferedReader(const char* fileName)
	throw(AppException) : BufferedIO(fileName) {
}

uint8_t BufferedReader::readUInt8() throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint8_t value;
	size_t result = read(fd, &value, sizeof(uint8_t));
	if (result != sizeof(uint8_t))
		throw AppException("read from file error.");
	return value;
}

uint16_t BufferedReader::readUInt16() throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint16_t value;
	size_t result = read(fd, &value, sizeof(uint16_t));
	if (result != sizeof(uint16_t))
		throw AppException("read from file error.");
	return value;
}

uint32_t BufferedReader::readUInt32() throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint32_t value;
	size_t result = read(fd, &value, sizeof(uint32_t));
	if (result != sizeof(uint32_t))
		throw AppException("read from file error.");
	return value;
}

uint64_t BufferedReader::readUInt64() throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint64_t value;
	size_t result = read(fd, &value, sizeof(uint64_t));
	if (result != sizeof(uint64_t))
		throw AppException("read from file error.");
	return value;
}

