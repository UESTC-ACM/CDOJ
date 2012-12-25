/**
 * Copyright 2012, fish <lyhypacm@gmail.com>
 *
 * Buffered IO Utils base class
 *		All the buffered io class will be it's
 * subclass, it handle file descriptor and 
 * buffer space, we can change the buffer size
 * by changing the value of BUFFER_SIZE.
 */
#ifndef __BUFFERED_IO_H__
#define __BUFFERED_IO_H__

#include "AppException.h"

#include <stdint.h>

// customized buffer size
#define BUFFER_SIZE 1024

class BufferedIO {
public:
	BufferedIO();
	BufferedIO(int fd);
	BufferedIO(const char* fileName) throw(AppException);
	virtual ~BufferedIO();
protected:
	int fd;
public:
	uint8_t* buffer;
};

#endif // __BUFFERED_IO_H__

