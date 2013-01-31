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
#ifndef __COMPILER_FACTORY_H__
#define __COMPILER_FACTORY_H__

#include <string>
#include <map>
#include "Compiler.h"


/**
 * Compiler Factory Class
 * 		compiler factory, we use this class to
 * get all kinds of compilers' instance, which
 * has been defined in cofiguration file.
 */

class CompilerFactory {
public:
	static const CompilerFactory* getInstance(void);
	const Compiler* getCompiler(int id) const;
	const Compiler* getCompiler(std::string extension) const;
private:
	CompilerFactory(void);
	static std::map<int, const Compiler*> compilers;
	static CompilerFactory* instance;
};

#endif // __COMPILER_FACTORY_H__

