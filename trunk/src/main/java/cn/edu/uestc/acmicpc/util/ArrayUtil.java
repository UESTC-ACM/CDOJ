package cn.edu.uestc.acmicpc.util;

/**
 * Array util functions.
 */
public class ArrayUtil {

  /**
   * Join all objects into a string, splitting with {@code flag}.
   *
   * @param objects object array
   * @param flag splitting flag
   * @return expected string
   */
  public static String join(Object[] objects, String flag) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < objects.length; ++i) {
      if (i > 0)
        stringBuilder.append(flag);
      stringBuilder.append(objects[i]);
    }
    return stringBuilder.toString();
  }

  /**
   * Joins all strings into a string, splitting with {@code flag}.
   *
   * @param objects string array
   * @param flag splitting flag
   * @return expected string
   */
  public static String join(String[] objects, String flag) {
    return join(toArray(objects), flag);
  }

  /**
   * Transform any array into {@code Object} array.
   *
   * @param array array parameter
   * @param <T> element type
   * @return expected array
   */
  public static <T> Object[] toArray(T[] array) {
    if (array == null || array.length < 1)
      return new Object[] {};
    Object[] result = new Object[array.length];
    System.arraycopy(array, 0, result, 0, result.length);
    return result;
  }

  /**
   * Transform string array into integer array.
   * <p/>
   * Set element null if it can not be parsed to integer.
   *
   * @param s integer list splitting with ','
   * @return expected integer array
   */
  public static Integer[] parseIntArray(String s) {
    if (s == null)
      return new Integer[0];
    String[] arr = s.split("[,]");
    Integer[] result = new Integer[arr.length];
    for (int i = 0; i < arr.length; ++i) {
      try {
        result[i] = Integer.parseInt(arr[i].trim());
      } catch (NumberFormatException e) {
        result[i] = null;
      }
    }
    return result;
  }
}
