package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * test cases for {@link cn.edu.uestc.acmicpc.service.iface.StatusService}
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class StatusServiceITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private StatusService statusService;

  @Test
  public void testCountProblemsUserTried_normalUser() throws AppException {
    Integer userId = 3;
    Assert.assertEquals(statusService.countProblemsUserTried(userId), Long.valueOf(2L));
  }

  @Test
  public void testCountProblemsUserTried_administrator() throws AppException {
    Integer userId = 1;
    Assert.assertEquals(statusService.countProblemsUserTried(userId), Long.valueOf(0L));
  }
}
