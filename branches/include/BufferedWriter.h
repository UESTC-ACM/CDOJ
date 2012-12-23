#ifndef __BUFFERED_WRITER_H__
#define __BUFFERED_WRITER_H__

#include <stdint.h>
#include "AppException.h"
#include "BufferedIO.h"

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

