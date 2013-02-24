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

package cn.edu.uestc.acmicpc.util;

import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Operations for zip files.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
public class ZipUtil {
    private static final int BUFFER_SIZE = 2048;

    /**
     * Unzip the zip file and put all the files in the zip file to the path.
     *
     * @param zipFile zipFile object
     * @param path    target path
     * @throws AppException if exception occurred, convert them into {@code AppException} object.
     */
    public static void unzipFile(ZipFile zipFile, String path) throws AppException {
        try {
            Enumeration enumeration = zipFile.entries();
            while (enumeration.hasMoreElements()) {
                ZipEntry zipEntry = (ZipEntry) enumeration.nextElement();
                if (zipEntry.isDirectory()) {
                    if (!new File(path + "/" + zipEntry.getName()).mkdirs())
                        throw new Exception();
                    continue;
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(zipFile.getInputStream(zipEntry));
                File file = new File(path + "/" + zipEntry.getName());
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    if (!parent.mkdirs())
                        throw new AppException();
                }
                FileOutputStream fileOutputStream = new FileOutputStream(file);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, BUFFER_SIZE);
                int len;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((len = bufferedInputStream.read(buffer, 0, BUFFER_SIZE)) != -1) {
                    bufferedOutputStream.write(buffer, 0, len);
                }
                bufferedOutputStream.flush();
                bufferedOutputStream.close();
                bufferedInputStream.close();
            }
            zipFile.close();
        } catch (Exception e) {
            throw new AppException("Unzip zip file error.");
        }
    }
}
