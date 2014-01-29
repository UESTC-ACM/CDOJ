package cn.edu.uestc.acmicpc.util.exception;

/**
 * Checking util for application.
 */
public class AppExceptionUtil {

  /**
   * Checks a object is not null.
   *
   * @param object object to check not null.
   * @throws AppException
   */
  public static void assertNotNull(Object object) throws AppException {
    assertNotNull(object, "Null");
  }

  /**
   * Checks a object is not null.
   *
   * @param object object to check not null.
   * @param message message will return
   * @throws AppException
   */
  public static void assertNotNull(Object object, String message) throws AppException {
    if (object == null) {
      throw new AppException(message);
    }
  }

  /**
   * Checks a statement is true.
   *
   * @param statement statement to check
   * @throws AppException
   */
  public static void assertTrue(boolean statement) throws AppException {
    assertTrue(statement, "Statement is false");
  }

  /**
   * Checks a statement is true.
   *
   * @param statement statement to check
   * @param message message will return
   * @throws AppException
   */
  public static void assertTrue(boolean statement, String message) throws AppException {
    if (!statement) {
      throw new AppException(message);
    }
  }
}
