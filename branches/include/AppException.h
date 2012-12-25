/**
 * Copyright 2012, fish <lyhypacm@gmail.com>
 *
 * Application Exception Class
 *		all customized exception of this project
 * will use this class to deal with customized
 * actions.
 */
#ifndef __APPEXCEPTION_H__
#define __APPEXCEPTION_H__

#include <string>

// buffer's size limit of application exception
#define	APPBUFSIZ	1024

class AppException {
public:
	AppException(void);
	AppException(const std::string& _message);
	~AppException(void);
	const char* message(void) const;
	void printStackTrace(void) const;

private:
	std::string _message;
};

#endif // __APPEXCEPTION_H__

