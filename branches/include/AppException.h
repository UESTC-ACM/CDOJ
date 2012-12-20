#ifndef __APPEXCEPTION_H__
#define __APPEXCEPTION_H__

#include <string>

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

