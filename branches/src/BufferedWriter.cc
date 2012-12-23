#include "BufferedWriter.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

BufferedWriter::BufferedWriter(const int& fd)
	: BufferedIO(fd) {
}

BufferedWriter::BufferedWriter(const char* fileName)
	throw(AppException) : BufferedIO(fileName) {
}

void BufferedWriter::writeUInt8(uint8_t value) throw(AppException) {
	size_t result = write(fd, &value, sizeof(uint8_t));
	if (result != sizeof(uint8_t))
		throw AppException("write into file error.");
}

void BufferedWriter::writeUInt16(uint16_t value) throw(AppException) {
	size_t result = write(fd, &value, sizeof(uint16_t));
	if (result != sizeof(uint16_t))
		throw AppException("write into file error.");
}

void BufferedWriter::writeUInt32(uint32_t value) throw(AppException) {
	size_t result = write(fd, &value, sizeof(uint32_t));
	if (result != sizeof(uint32_t))
		throw AppException("write into file error.");
}

void BufferedWriter::writeUInt64(uint64_t value) throw(AppException) {
	size_t result = write(fd, &value, sizeof(uint64_t));
	if (result != sizeof(uint64_t))
		throw AppException("write into file error.");
}

