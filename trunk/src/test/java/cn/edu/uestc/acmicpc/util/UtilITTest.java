package cn.edu.uestc.acmicpc.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Utility class test
 */
public class UtilITTest {

  private static String VJ_1Y = "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})$";
  private static String VJ_NORMAL =
      "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})\\s*\\(\\s*-\\s*(\\d+)\\s*\\)$";
  private static String VJ_FAIL = "^\\(\\s*-\\s*(\\d+)\\s*\\)$";
  private static String PC_NORMAL = "^(\\d+)\\s*/\\s*(\\d+)$";
  private static String PC_FAIL = "^(\\d+)\\s*/\\s*-\\s*-$";

  @Test
  public void regexTest() {
    Pattern pattern;
    Matcher matcher;
    String query;

    pattern = Pattern.compile(VJ_1Y);
    query = "4 : 28 : 00";
    matcher = pattern.matcher(query);
    if (matcher.find()) {
      assert matcher.group(1).equals("4") && matcher.group(2).equals("28")
          && matcher.group(3).equals("00");
    } else {
      assert false;
    }

    pattern = Pattern.compile(VJ_NORMAL);
    query = "4:35:00 (-2)";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());

    pattern = Pattern.compile(VJ_FAIL);
    query = "(-5 )";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());

    pattern = Pattern.compile(PC_NORMAL);
    query = "14/ 294";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());

    pattern = Pattern.compile(PC_FAIL);
    query = "16/- -";
    matcher = pattern.matcher(query);
    Assert.assertTrue(matcher.find());
  }
}
