/**
 * Copyright 2012, fish <lyhypacm@gmail.com>
 *
 * Compiler Entity 
 *  	store a compiler's information, and
 * parameters. It's just a abstract class.
 */
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
	virtual bool compile() = 0;
private:
	int id;
	std::string name;
	std::string extension;
};

#endif // __COMPILER_H__

