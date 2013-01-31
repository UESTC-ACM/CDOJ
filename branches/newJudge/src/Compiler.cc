/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2012  fish <@link lyhypacm@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
#include "Compiler.h"
#include "Logger.h"
#include "BufferedWriter.h"

#define	COMPILER_BUF_SIZE	1024
#define	COMPILING			0
#define	INTERNAL_ERROR		11

/**
 * Compiler Entity 
 *  	store a compiler's information, and
 * parameters. It's just a abstract class.
 */

Compiler::Compiler(int id, std::string name, std::string extension)
	: id(id), name(name), extension(extension) {
}

Compiler::~Compiler(void) {
}

bool Compiler::compile(int sockId) {
	// TODO
	Logger* logger = Logger::getInstance();
	BufferedWriter* bufferWriter = new BufferedWriter(sockId);
	static char buf[COMPILER_BUF_SIZE];
	try {
		logger->log(INFO, "begin compile sockId: %d\n", sockId);
		bufferWriter->writeUInt32(COMPILING);
	} catch (AppException& e) {
		e.printStackTrace();
		bufferWriter->writeUInt32(INTERNAL_ERROR);
		return false;
	}
	if (bufferWriter != NULL)
		delete bufferWriter;
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

