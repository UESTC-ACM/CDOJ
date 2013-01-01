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
#include "BufferedIO.h"

#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <unistd.h>
#include <cstdlib>

/**
 * Buffered IO Utils base class
 *		All the buffered io class will be it's
 * subclass, it handle file descriptor and 
 * buffer space, we can change the buffer size
 * by changing the value of BUFFER_SIZE.
 */
 
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
	if (fd >= 3)
		close(fd);
}

