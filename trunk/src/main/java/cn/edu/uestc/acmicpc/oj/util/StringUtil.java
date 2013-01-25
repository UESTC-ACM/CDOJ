/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2012  fish <@link lyhypacm@gmail.com>,
 * 	mzry1992 <@link muziriyun@gmail.com>
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

package cn.edu.uestc.acmicpc.oj.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * global static class to deal with strings
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@SuppressWarnings("UnusedDeclaration")
public class StringUtil {
    /**
     * repeat a string many times
     * <p/>
     * <strong>EXAMPLE</strong>
     * repeat("hello#", 2)
     * it means "hello#hello#"
     *
     * @param s     the basic string we should deal with
     * @param count the time that we want to repeat
     * @return the result string we need to get
     */
    public static String repeat(String s, int count) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < count; ++i)
            stringBuilder.append(s);
        return stringBuilder.toString();
    }

    /**
     * check whether a string is null or empty
     *
     * @param s the string to be checked
     * @return if the string is null or empty, return true
     */
    public static boolean isNullOrEmpty(String s) {
        return s == null || s.length() < 1;
    }

    /**
     * check whether a string is null or only contains white spaces
     *
     * @param s the string to be checked
     * @return if the string is null or only contains white spaces, return true
     */
    public static boolean isNullOrWhiteSpace(String s) {
        return s == null || s.trim().length() < 1;
    }

    /**
     * encode a string with SHA1
     *
     * @param text the basic text we want to encode
     * @return the text after encoding
     */
    public static String encodeSHA1(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            messageDigest.update(text.getBytes("UTF-8"));
            byte[] result = messageDigest.digest();
            StringBuilder stringBuilder = new StringBuilder();
            for (byte b : result) {
                int i = b & 0xFF;
                if (i < 0xF)
                    stringBuilder.append(0);
                stringBuilder.append(Integer.toHexString(i));
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * generate a file name depend on current time
     *
     * @param filename basic file name
     * @param seed     a random seed for suffix
     * @return the file name generated
     */
    public static String generateFileName(String filename, Integer seed) {
        DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String formatDate = format.format(new Date())
                + (seed == null ? "" : seed.toString());
        return formatDate + getFilenameExt(filename);
    }

    /**
     * get a file's extension name
     *
     * @param filename the file's name
     * @return the extension name of the file
     */
    public static String getFilenameExt(String filename) {
        int position = filename.lastIndexOf(".");
        return position > -1 ? filename.substring(position) : "";
    }

    /**
     * If {@code str1} is not {@code null} return {@code str1},
     * otherwise return {@code str2}.
     *
     * @param str1 main string
     * @param str2 backup string
     * @return expected value
     */
    public static String choose(String str1, String str2) {
        return !isNullOrEmpty(str1) ? str1 : str2;
    }
}
