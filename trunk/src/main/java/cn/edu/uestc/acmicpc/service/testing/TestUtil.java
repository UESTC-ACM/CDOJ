package cn.edu.uestc.acmicpc.service.testing;

/**
 * Utils for test
 */
public class TestUtil {

  private static long counter = 0;

  public static synchronized Long getUniqueId() {
    return ++counter;
  }
}
