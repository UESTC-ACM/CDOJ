#include "Compiler.h"
#include "logger.h"

Compiler::Compiler(int id, std::string name, std::string extension)
	: id(id), name(name), extension(extension) {
}

Compiler::~Compiler(void) {
}

bool Compiler::compile(int sockId) {
	// TODO
	OJ_LOG_NOTICE("begin compile sockId: %d\n", sockId);
	return true;
}

std::string Compiler::getExtension(void) const {
	return extension;
}

int Compiler::getId(void) const {
	return id;
}

std::string Compiler::getName(void) const {
	return name;
}

