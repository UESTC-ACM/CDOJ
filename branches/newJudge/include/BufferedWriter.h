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
#ifndef __BUFFERED_WRITER_H__
#define __BUFFERED_WRITER_H__

#include <stdint.h>
#include "AppException.h"
#include "BufferedIO.h"


/**
 * Buffered IO class implementation
 *		we use this class object to write data into
 * a file whose name or descriptor has been pre-defined.
 */

class BufferedWriter : public BufferedIO {
public:
	BufferedWriter(const int& fd);
	BufferedWriter(const char* fileName) throw(AppException);
	void writeUInt8(uint8_t value) throw(AppException);
	void writeUInt16(uint16_t value) throw(AppException);
	void writeUInt32(uint32_t value) throw(AppException);
	void writeUInt64(uint64_t value) throw(AppException);
};

#endif // __BUFFERED_WRITER_H__

