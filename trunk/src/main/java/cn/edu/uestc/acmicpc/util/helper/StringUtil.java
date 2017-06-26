package cn.edu.uestc.acmicpc.util.helper;

import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * global static class to deal with strings
 */
public class StringUtil {

  /**
   * Trim all space, include chinese blank character.
   *
   * @param str
   *          origin string
   * @return result
   */
  public static String trimAllSpace(String str) {
    return str == null ? str : str.replaceAll("^[\\s　]*|[\\s　]*$", "");
  }

  /**
   * Create @user link
   *
   * @param userName
   *          user's name
   * @return result
   */
  public static String getAtLink(String userName) {
    return "@" + userName + " ";
  }

  /**
   * get Setter or Getter name, return {@code null} if exception occurred.
   *
   * @param methodType
   *          method type for getter or setter
   * @param name
   *          field name
   * @return method name
   */
  public static String getGetterOrSetter(MethodType methodType, String name) {
    try {
      char charAt = name.charAt(0);
      if (charAt >= 'a' && charAt <= 'z') {
        charAt = (char) (charAt - 'a' + 'A');
      }
      return String.format("%s%c%s", methodType == MethodType.GETTER ? "get" : "set", charAt,
          name.substring(1));
    } catch (Exception e) {
      return null;
    }
  }

  /**
   * Get specific field name from it's getter or setter name
   *
   * @param methodName
   *          method's name
   * @return filed's name
   */
  public static String getFieldNameFromGetterOrSetter(String methodName) {
    if (methodName.startsWith("get") || methodName.startsWith("set")) {
      StringBuilder stringBuilder = new StringBuilder();
      stringBuilder.append(Character.toLowerCase(methodName.charAt(3)));
      if (methodName.length() > 4) {
        stringBuilder.append(methodName.substring(4));
      }
      return stringBuilder.toString();
    } else {
      return null;
    }
  }

  public enum MethodType {
    GETTER, SETTER
  }

  /**
   * repeat a string many times
   * <p>
   * <strong>EXAMPLE</strong> repeat("hello#", 2) it means "hello#hello#"
   *
   * @param s
   *          the basic string we should deal with
   * @param count
   *          the time that we want to repeat
   * @return the result string we need to get
   */
  public static String repeat(String s, int count) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < count; ++i) {
      stringBuilder.append(s);
    }
    return stringBuilder.toString();
  }

  /**
   * check whether a string is null or empty
   *
   * @param s
   *          the string to be checked
   * @return if the string is null or empty, return true
   */
  public static boolean isNullOrEmpty(String s) {
    return s == null || s.length() < 1;
  }

  /**
   * check whether a string is null or only contains white spaces
   *
   * @param s
   *          the string to be checked
   * @return if the string is null or only contains white spaces, return true
   */
  public static boolean isNullOrWhiteSpace(String s) {
    return s == null || s.trim().length() < 1;
  }

  /**
   * encode a string with SHA1
   *
   * @param text
   *          the basic text we want to encode
   * @return the text after encoding
   */
  public static String encodeSHA1(String text) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
      messageDigest.update(text.getBytes("UTF-8"));
      byte[] result = messageDigest.digest();
      char hexDigit[] = { '0', '1', '2', '3', '4', '5', '6', '7',
          '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
      StringBuilder buf = new StringBuilder();
      for (byte aResult : result) {
        buf.append(hexDigit[(aResult >> 4) & 0x0f]);
        buf.append(hexDigit[aResult & 0x0f]);
      }
      return buf.toString();
    } catch (Exception e) {
      return "";
    }
  }

  private static Integer SEED = (int) 0;

  /**
   * generate a file name depend on current time
   *
   * @param filename
   *          basic file name
   * @return the file name generated
   */
  public static String generateFileName(String filename) {
    DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
    SEED = (SEED + 1) & 65535;
    String formatDate = format.format(new Date()) + SEED.toString();
    return formatDate + getFilenameExt(filename);
  }

  /**
   * get a file's extension name
   *
   * @param filename
   *          the file's name
   * @return the extension name of the file
   */
  public static String getFilenameExt(String filename) {
    int position = filename.lastIndexOf(".");
    return position > -1 ? filename.substring(position) : "";
  }

  /**
   * If {@code str1} is not {@code null} return {@code str1}, otherwise return
   * {@code str2}.
   *
   * @param str1
   *          main string
   * @param str2
   *          backup string
   * @return expected value
   */
  public static String choose(String str1, String str2) {
    return !isNullOrEmpty(str1) ? str1 : str2;
  }

  /**
   * Check whether the file can match types list
   *
   * @param fileName
   *          file name
   * @param types
   *          type list splitting with ';'
   * @return if this file can be upload, return {@code true}
   */
  public static boolean containTypes(String fileName, String types) {
    String ext = getFilenameExt(fileName).replace(".", "");
    ext = String.format(";%s;", ext).toLowerCase();
    return String.format(";%s;", types).toLowerCase().contains(ext);
  }

  /**
   * Compare two string, skipping all white spaces.
   *
   * @param first
   *          first string to be compared
   * @param second
   *          second string to be compared
   * @return if first string is smaller second string, return {@code -1}, if
   *         first string is larger than second string, return {@code 1},
   *         otherwise return {@code 0}.
   */
  public static int compareSkipSpaces(String first, String second) {
    char[] firstCharArray = first.toCharArray();
    char[] secondCharArray = second.toCharArray();
    for (int i = 0, j = 0;;) {
      if (i < firstCharArray.length && Character.isWhitespace(firstCharArray[i])) {
        ++i;
      } else if (j < secondCharArray.length && Character.isWhitespace(secondCharArray[j])) {
        ++j;
      } else if (i == firstCharArray.length) {
        if (j == secondCharArray.length) {
          return 0;
        } else {
          return -1;
        }
      } else if (j == secondCharArray.length) {
        return 1;
      } else {
        if (firstCharArray[i] != secondCharArray[j]) {
          return firstCharArray[i] < secondCharArray[j] ? -1 : 1;
        } else {
          ++i;
          ++j;
        }
      }
    }
  }

  /**
   * Get status information description by case number.
   *
   * @param type
   *          status type
   * @param currentCase
   *          current case number
   * @return target description
   */
  public static String getStatusDescription(OnlineJudgeReturnType type, int currentCase) {
    return type.getDescription().replace("$case", Integer.toString(currentCase));
  }
}
