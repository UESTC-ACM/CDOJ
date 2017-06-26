package cn.edu.uestc.acmicpc.config;

import cn.edu.uestc.acmicpc.util.log.LogConstants;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

/**
 * Test listener to show colorful message in console.
 */
public class TestListener extends TestListenerAdapter {

  @Override
  public void onStart(ITestContext testContext) {}

  @Override
  public void onFinish(ITestContext testContext) {
    StringBuilder builder = new StringBuilder();
    if (testContext.getFailedTests().size() != 0) {
      builder.append(LogConstants.FATAL_COLOR);
      builder.append("FAILED: ");
    } else if (testContext.getFailedButWithinSuccessPercentageTests().size() != 0) {
      builder.append(LogConstants.WARN_COLOR);
      builder.append("FLAKY: ");
    } else {
      builder.append(LogConstants.INFO_COLOR);
      builder.append("PASSED: ");
    }
    builder.append(LogConstants.END_COLOR);
    builder.append(testContext.getName());
    System.out.println(builder.toString());
  }

  @Override
  public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {}

  @Override
  public void onTestSkipped(ITestResult tr) {}

  @Override
  public void onTestSuccess(ITestResult tr) {}

  @Override
  public void onTestStart(ITestResult result) {}
}
