/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.checker;

import cn.edu.uestc.acmicpc.checker.base.Checker;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.io.File;

/**
 * Data checker for data.zip files.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class ZipDataChecker implements Checker<File> {
    @Override
    public void check(File file) throws AppException {
        File[] files = file.listFiles();
        if (files == null)
            throw new AppException("Data file is invalid.");
        int minId = 0, maxId = 0, count = 0;
        for (File current : files) {
            if (current.isDirectory())
                throw new AppException("Data file contains directory.");
            if (current.getName().endsWith(".in") || current.getName().endsWith(".out")) {
                String prefix = current.getName().substring(0, current.getName().lastIndexOf('.'));
                try {
                    int currentId = Integer.parseInt(prefix);
                    if (count == 0) {
                        minId = maxId = currentId;
                    } else {
                        minId = Math.min(minId, currentId);
                        maxId = Math.max(maxId, currentId);
                    }
                    ++count;
                } catch (NumberFormatException e) {
                    throw new AppException("Data files must begin with numbers.");
                }
            } else {
                throw new AppException("Data file contains unknown file type.");
            }
        }
        if (minId != 1)
            throw new AppException("Data id must begin with 1.");
        if (count != (maxId - minId + 1) * 2)
            throw new AppException("Some data files has not input file or output file.");
    }
}
