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
#include "CompilerFactory.h"

/**
 * Compiler Factory Class
 * 		compiler factory, we use this class to
 * get all kinds of compilers' instance, which
 * has been defined in cofiguration file.
 */

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

