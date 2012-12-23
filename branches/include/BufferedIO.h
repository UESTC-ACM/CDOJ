#ifndef __BUFFERED_IO_H__
#define __BUFFERED_IO_H__

#include "AppException.h"

#include <stdint.h>

#define BUFFER_SIZE 1024

class BufferedIO {
public:
	BufferedIO();
	BufferedIO(int fd);
	BufferedIO(const char* fileName) throw(AppException);
	~BufferedIO();
protected:
	int fd;
public:
	uint8_t* buffer;
};

#endif // __BUFFERED_IO_H__

