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
#include "BufferedReader.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

/**
 * Buffered IO class implementation
 *		we use this class object to read data from
 * a file whose name or descriptor has been pre-defined.
 */

BufferedReader::BufferedReader(const int& fd)
	: BufferedIO(fd) {
}

BufferedReader::BufferedReader(const char* fileName)
	throw(AppException) : BufferedIO(fileName) {
}

uint8_t BufferedReader::readUInt8(void) throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint8_t value;
	size_t result = read(fd, &value, sizeof(uint8_t));
	if (result != sizeof(uint8_t))
		throw AppException("read from file error.");
	return value;
}

uint16_t BufferedReader::readUInt16(void) throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint16_t value;
	size_t result = read(fd, &value, sizeof(uint16_t));
	if (result != sizeof(uint16_t))
		throw AppException("read from file error.");
	return value;
}

uint32_t BufferedReader::readUInt32(void) throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint32_t value;
	size_t result = read(fd, &value, sizeof(uint32_t));
	if (result != sizeof(uint32_t))
		throw AppException("read from file error.");
	return value;
}

uint64_t BufferedReader::readUInt64(void) throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	uint64_t value;
	size_t result = read(fd, &value, sizeof(uint64_t));
	if (result != sizeof(uint64_t))
		throw AppException("read from file error.");
	return value;
}

const char* BufferedReader::next(void) throw(AppException) {
	if (fd < 0)
		throw AppException("cannot open input file.");
	char value;
	size_t result;
	size_t current = 0;

	while (current < BUFFER_SIZE - 1) {
		result = read(fd, &value, sizeof(char));
		if (result == 0x00 || value == 0x00 ||
			value == 0x0A || value == 0x0D
			|| value == 0x20 || value == 0x09)
			break;
		buffer[current++] = value;
	}
	buffer[current] = 0;
	if (current == 0) return NULL;
	return buffer;
}

