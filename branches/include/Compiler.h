#ifndef __COMPILER_H__
#define __COMPILER_H__

#include <string>

class Compiler {
public:
	Compiler(int id, std::string name, std::string extension);
	~Compiler(void);
	bool compile(int sockId);
	int getId(void) const;
	std::string getName(void) const;
	std::string getExtension(void) const;
private:
	int id;
	std::string name;
	std::string extension;
};

#endif // __COMPILER_H__

