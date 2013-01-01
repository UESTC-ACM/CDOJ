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
#ifndef __BUFFERED_READER_H__
#define __BUFFERED_READER_H__

#include <stdint.h>
#include "AppException.h"
#include "BufferedIO.h"

/**
 * Buffered IO class implementation
 *		we use this class object to read data from
 * a file whose name or descriptor has been pre-defined.
 */

class BufferedReader : public BufferedIO {
public:
	BufferedReader(const int& fd);
	BufferedReader(const char* fileName) throw(AppException);
	uint8_t readUInt8(void) throw(AppException);
	uint16_t readUInt16(void) throw(AppException);
	uint32_t readUInt32(void) throw(AppException);
	uint64_t readUInt64(void) throw(AppException);
	//const char* read(void) throw(AppException);
};

#endif // __BUFFERED_READER_H__

