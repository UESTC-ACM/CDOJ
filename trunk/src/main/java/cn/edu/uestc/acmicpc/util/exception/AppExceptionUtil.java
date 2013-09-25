package cn.edu.uestc.acmicpc.util.exception;

/** Checking util for application. */
public class AppExceptionUtil {

  /**
   * Checks a object is not null.
   *
   * @param object object to check not null.
   * @throws AppException
   */
  public static void assertNotNull(Object object) throws AppException {
    if (object == null) {
      throw new AppException("null");
    }
  }

  /**
   * Checks a statement is true.
   *
   * @param statement statement to check
   * @throws AppException
   */
  public static void assertTrue(boolean statement) throws AppException {
    if (!statement) {
      throw new AppException("statement is false");
    }
  }
}
