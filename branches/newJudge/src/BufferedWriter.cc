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
#include "BufferedWriter.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>

/**
 * Buffered IO class implementation
 *		we use this class object to write data into
 * a file whose name or descriptor has been pre-defined.
 */

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

