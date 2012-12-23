#ifndef __BUFFERED_READER_H__
#define __BUFFERED_READER_H__

#include <stdint.h>
#include "AppException.h"
#include "BufferedIO.h"

class BufferedReader : public BufferedIO {
public:
	BufferedReader(const int& fd);
	BufferedReader(const char* fileName) throw(AppException);
	uint8_t readUInt8() throw(AppException);
	uint16_t readUInt16() throw(AppException);
	uint32_t readUInt32() throw(AppException);
	uint64_t readUInt64() throw(AppException);
};

#endif // __BUFFERED_READER_H__

