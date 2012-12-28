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
#ifndef __APPEXCEPTION_H__
#define __APPEXCEPTION_H__

#include <string>

// buffer's size limit of application exception
#define	APPBUFSIZ	1024

/**
 * Application Exception Class
 *		all customized exception of this project
 * will use this class to deal with customized
 * actions.
 */

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

