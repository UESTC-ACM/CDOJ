package cn.edu.uestc.acmicpc.util.helper;

/**
 * Character utils, list all methods for characters control.
 */
public class CharUtil {

  /**
   * Check whether expected character is white space or not.
   *
   * @param character expected character
   * @return if expected character is white space, return {@code return}
   */
  public static boolean isWhiteSpace(char character) {
    return character == ' ' || character == '\t' || character == '\n' || character == '\r';
  }

  /**
   * Convert character to lower case.
   *
   * @param c character to be converted
   * @return character after converting
   */
  public static char toLowerCase(char c) {
    if (c >= 'A' && c <= 'Z')
      return (char) (c - 'A' + 'a');
    else
      return c;
  }

  /**
   * Convert character to upper case.
   *
   * @param c character to be converted
   * @return character after converting
   */
  public static char toUpperCase(char c) {
    if (c >= 'a' && c <= 'z')
      return (char) (c - 'a' + 'Z');
    else
      return c;
  }
}
