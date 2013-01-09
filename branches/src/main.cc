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
#include <cstdio>
#include "Logger.h"
#include "AppException.h"
#include "BufferedIO.h"

int main(int argc, char* argv[]) {
	try {
		Logger* logger = Logger::getInstance();
		logger->open();
		logger->log(INFO, "core begin with argc = %d argv address %p", argc, argv);
		BufferedIO* bufferIO = new BufferedIO(0);
		printf("%p\n", bufferIO->buffer);
		throw AppException("catched exception");
		if (bufferIO != NULL)
			delete bufferIO;
		logger->close();
	} catch (const AppException& e) {
		e.printStackTrace();
	}
	return 0;
}

