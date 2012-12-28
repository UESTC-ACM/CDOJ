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
#include "logger.h"
#include "AppException.h"
#include "BufferedIO.h"

int main(int argc, char* argv[]) {
	log_open("log/core.log");
	OJ_LOG_NOTICE("core begin with argc = %d argv address %p", argc, argv);
	BufferedIO* bufferIO = new BufferedIO(0);
	try {
		printf("%p\n", bufferIO->buffer);
		throw AppException("catched exception");
	} catch (const AppException& e) {
		e.printStackTrace();
	}
	if (bufferIO != NULL)
		delete bufferIO;
	log_close();
	return 0;
}

