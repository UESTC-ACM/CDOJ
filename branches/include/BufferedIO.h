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
#ifndef __BUFFERED_IO_H__
#define __BUFFERED_IO_H__

#include "AppException.h"

#include <stdint.h>

// customized buffer size
#define BUFFER_SIZE 1024

/**
 * Buffered IO Utils base class
 *		All the buffered io class will be it's
 * subclass, it handle file descriptor and 
 * buffer space, we can change the buffer size
 * by changing the value of BUFFER_SIZE.
 */

class BufferedIO {
public:
	BufferedIO();
	BufferedIO(int fd);
	BufferedIO(const char* fileName) throw(AppException);
	virtual ~BufferedIO();
	uint8_t* buffer;
protected:
	int fd;
};

#endif // __BUFFERED_IO_H__

