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
 * <p/>
 * <strong>Check items</strong>:
 * <ul>
 * <li>
 * All file names in the zip file will match "*.in" or "*.out".
 * </li>
 * <li>
 * The file name will be numbered from 1 to number of test cases.
 * </li>
 * <li>
 * For all the data files, input file and output file will match.
 * </li>
 * </ul>
 * <p/>
 * <strong>For developers</strong>:
 * <p/>
 * This checker will not consider the folders in the zip file, that
 * means if the zip file only contains a folder and the folder contains
 * a valid file structure, this checker will not check the files in the
 * folder.
 * <strong>For administrators</strong>:
 * <p/>
 * Please put all data files in the zip file's root, rather than a
 * specific folder.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
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
            } else if (current.getName().equals("spj.cc")) {
                // spj checker, ignored
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
