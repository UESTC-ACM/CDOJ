#ifndef __APPEXCEPTION_H__
#define __APPEXCEPTION_H__

#include <string>

#define	APPBUFSIZ	1024

class AppException {
public:
	AppException();
	AppException(const std::string& _message);
	const char* message() const;
	void printStackTrace() const;

private:
	std::string _message;
};

#endif // __APPEXCEPTION_H__

