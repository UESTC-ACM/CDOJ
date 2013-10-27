package cn.edu.uestc.acmicpc.config;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import cn.edu.uestc.acmicpc.util.log.LogConstants;

public class TestListener extends TestListenerAdapter {

  @Override
  public void onStart(ITestContext testContext) {
    System.out.print("Test: ");
    System.out.print(LogConstants.WARN_COLOR);
    System.out.print(testContext.getName());
    System.out.println(LogConstants.END_COLOUR);
  }

  @Override
  public void onFinish(ITestContext testContext) {
  }

  @Override
  public void onTestSuccess(ITestResult result) {
    StringBuilder builder = new StringBuilder();
    builder.append(LogConstants.INFO_COLOR);
    builder.append("PASSED: ");
    builder.append(LogConstants.END_COLOUR);
    builder.append(result.getName());
    System.out.println(builder.toString());
  }

  @Override
  public void onTestFailure(ITestResult result) {
    StringBuilder builder = new StringBuilder();
    builder.append(LogConstants.FATAL_COLOR);
    builder.append("FAILED: ");
    builder.append(LogConstants.END_COLOUR);
    builder.append(result.getName());
    System.out.println(builder.toString());
  }

  @Override
  public void onTestSkipped(ITestResult result) {
    StringBuilder builder = new StringBuilder();
    builder.append("SKIPPED: ");
    builder.append(result.getName());
    System.out.println(builder.toString());
  }
}
