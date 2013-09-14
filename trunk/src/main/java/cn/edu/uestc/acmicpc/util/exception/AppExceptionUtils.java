package cn.edu.uestc.acmicpc.util.exception;

/**
 * Utility methods for {@link AppException}.
 */
public class AppExceptionUtils {

  public static boolean assertEquals(AppException expected, AppException actual) {
    if (expected == actual) {
      return true;
    }
    if (expected == null || actual == null) {
      return false;
    }
    boolean result = true;
    if (expected.getCause() == null) {
      result &= actual.getCause() == null;
    } else {
      result &= expected.getCause().equals(actual.getCause());
    }
    if (expected.getMessage() == null) {
      result &= actual.getMessage() == null;
    } else {
      result &= expected.getMessage().equals(actual.getMessage());
    }
    return result;
  }
}
