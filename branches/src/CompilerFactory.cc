#include "CompilerFactory.h"

CompilerFactory* CompilerFactory::instance = NULL;
std::map<int, const Compiler*> CompilerFactory::compilers;

CompilerFactory::CompilerFactory(void) {
	// TODO
}

const CompilerFactory* CompilerFactory::getInstance(void) {
	if (instance == NULL)
		instance = new CompilerFactory();
	return instance;
}

const Compiler* CompilerFactory::getCompiler(int id) const {
	if (CompilerFactory::compilers.find(id) != CompilerFactory::compilers.end())
		return CompilerFactory::compilers.find(id)->second;
	else
		return NULL;
}

const Compiler* CompilerFactory::getCompiler(std::string extension) const {
	for (std::map<int, const Compiler*>::const_iterator it = CompilerFactory::compilers.begin();
		it != CompilerFactory::compilers.end(); ++it) {
		if (it->second->getExtension().find(extension) != std::string::npos)
			return it->second;
	}
	return NULL;
}

