package cn.edu.uestc.acmicpc.util.exception;

/** Checking util for application. */
public class AppExceptionUtil {

  /**
   * Check a object is not null.
   *
   * @throws AppException
   */
  public static void assertNotNull(Object object) throws AppException {
    if (object == null) {
      throw new AppException("null");
    }
  }
}
