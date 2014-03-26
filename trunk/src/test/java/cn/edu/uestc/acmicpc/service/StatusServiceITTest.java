package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;

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

  @Test
  public void testCountProblemsUserAccepted_normalUser() throws AppException {
    Integer userId = 3;
    Assert.assertEquals(statusService.countProblemsUserAccepted(userId), Long.valueOf(1L));
  }

  @Test
  public void testCountProblemsUserAccepted_administrator() throws AppException {
    Integer userId = 1;
    Assert.assertEquals(statusService.countProblemsUserAccepted(userId), Long.valueOf(0L));
  }

  @Test
  public void testFindAllUserTriedProblemIds_normalUser_forAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1, 2}));
  }

  @Test
  public void testFindAllUserTriedProblemIds_normalUser_notForAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1, 2}));
  }

  @Test
  public void testFindAllUserTriedProblemIds_administrator_forAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1}));
  }

  @Test
  public void testFindAllUserTriedProblemIds_administrator_notForAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserTriedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_normalUser_forAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {2}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_normalUser_notForAdmin() throws AppException {
    Integer userId = 3;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {2}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_administrator_forAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = true;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {1}));
  }

  @Test
  public void testFindAllUserAcceptedProblemIds_administrator_notForAdmin() throws AppException {
    Integer userId = 1;
    Boolean isAdmin = false;
    Assert.assertEquals(statusService.findAllUserAcceptedProblemIds(userId, isAdmin), Arrays.asList(new Object[] {}));
  }

  @Test
  public void testCountUsersTriedProblem() throws AppException {
    Integer problemId1 = 1;
    Assert.assertEquals(statusService.countUsersTriedProblem(problemId1), Long.valueOf(1));
  }
}
