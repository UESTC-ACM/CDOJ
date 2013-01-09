/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013  fish <@link lyhypacm@gmail.com>
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
#include <cstring>
#include "Settings.h"
#include "BufferedReader.h"
#include "Logger.h"

/**
 * Global Settings class, all system settings
 * 		will be stored in this class instance.
 */

Settings* Settings::instance;

Settings::Settings() {
	BufferedReader* bufferReader = NULL;
	Logger* logger = Logger::getInstance();
	try {
		const char* line;
		bufferReader = new BufferedReader("config/settings.cfg");
		while ((line = bufferReader->next()) != NULL) {
			if (strncmp(line, "logFile=", 8) == 0)
				logFile = line + 8;
			else if (strncmp(line, "tempDir=", 8) == 0)
				tempDir = line + 8;
			else if (strncmp(line, "dataDir=", 8) == 0)
				dataDir = line + 8;
		}
	} catch (AppException e) {
		e.printStackTrace();
	}
	if (bufferReader != NULL)
		delete bufferReader;
}

std::string Settings::getTempDir(void) const {
	return tempDir;
}

std::string Settings::getDataDir(void) const {
	return dataDir;
}

std::string Settings::getLogFile(void) const {
	return logFile;
}

void Settings::chroot(void) const throw(AppException) {
	// TODO
}

void Settings::clean(int fd) const throw(AppException) {
	// TODO
}

const Settings* Settings::getInstance() {
	if (instance == NULL)
		instance = new Settings();
	return instance;
}

