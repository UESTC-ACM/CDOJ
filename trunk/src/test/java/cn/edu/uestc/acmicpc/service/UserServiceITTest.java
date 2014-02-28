package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import cn.edu.uestc.acmicpc.util.settings.Global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Integration test cases for {@link UserService}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class UserServiceITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  UserService userService;

  @Test
  public void testGetUserByUserId() throws AppException {
    UserDTO user = userService.getUserDTOByUserId(2);
    Assert.assertEquals(user.getUserName(), "admin");
  }

  @Test
  public void testGetUserByUserName() throws AppException {
    UserDTO user = userService.getUserDTOByUserName("admin");
    Assert.assertEquals(user.getUserName(), "admin");
  }

  @Test
  public void testGetUserByUserEmail() throws AppException {
    UserDTO user = userService.getUserDTOByEmail("acm_admin@uestc.edu.cn");
    Assert.assertEquals(user.getEmail(), "acm_admin@uestc.edu.cn");
  }

  @Test
  public void testUpdateUser() throws AppException, FieldNotUniqueException {
    UserDTO user = userService.getUserDTOByUserName("administrator");
    user.setLastLogin(new Timestamp(new Date().getTime()));
    userService.updateUser(user);
  }

  @Test(enabled = false)
  public void testCreateNewUser() {
  }

  @Test
  public void testCount_emptyCondition() throws AppException {
    UserCondition condition = new UserCondition();
    Assert.assertEquals(userService.count(condition), Long.valueOf(6L));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    Assert.assertEquals(userService.count(condition), Long.valueOf(5L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.endId = 2;
    Assert.assertEquals(userService.count(condition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 2;
    condition.endId = 10;
    Assert.assertEquals(userService.count(condition), Long.valueOf(5L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    UserCondition condition = new UserCondition();
    condition.startId = 3;
    condition.endId = 2;
    Assert.assertEquals(userService.count(condition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byDepartmentId() throws AppException {
    UserCondition condition = new UserCondition();
    condition.departmentId = 1;
    Assert.assertEquals(userService.count(condition), Long.valueOf(5L));
  }

  @Test
  public void testCount_bySchool() throws AppException {
    UserCondition condition = new UserCondition();
    condition.school = "UES";
    Assert.assertEquals(userService.count(condition), Long.valueOf(6L));
  }

  @Test
  public void testCount_bySchool_emptyRsult() throws AppException {
    UserCondition condition = new UserCondition();
    condition.school = "USE";
    Assert.assertEquals(userService.count(condition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName() throws AppException {
    UserCondition condition = new UserCondition();
    condition.userName = "admin";
    Assert.assertEquals(userService.count(condition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byType() throws AppException {
    UserCondition condition = new UserCondition();
    condition.type = Global.AuthenticationType.CONSTANT.ordinal();
    Assert.assertEquals(userService.count(condition), Long.valueOf(1L));
  }
}
