package cn.edu.uestc.acmicpc.util;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * global static class to deal with strings
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
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
}
