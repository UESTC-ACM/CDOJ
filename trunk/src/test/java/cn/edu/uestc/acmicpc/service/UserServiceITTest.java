package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Integration test cases for {@link UserService}.
 */
@SuppressWarnings("deprecation")
public class UserServiceITTest extends PersistenceITTest {

  @Autowired
  UserService userService;

  @Test
  public void testGetUserByUserId() throws AppException {
    UserDto user = userService.getUserDtoByUserId(2);
    Assert.assertEquals(user.getUserName(), "admin");
  }

  @Test
  public void testGetUserByUserName() throws AppException {
    UserDto user = userService.getUserDtoByUserName("admin");
    Assert.assertEquals(user.getUserName(), "admin");
  }

  @Test
  public void testGetUserByUserEmail() throws AppException {
    UserDto user = userService.getUserDtoByEmail("acm_admin@uestc.edu.cn");
    Assert.assertEquals(user.getEmail(), "acm_admin@uestc.edu.cn");
  }

  @Test
  public void testUpdateUser() throws AppException, FieldNotUniqueException {
    UserDto user = userService.getUserDtoByUserName("administrator");
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
    condition.type = AuthenticationType.CONSTANT.ordinal();
    Assert.assertEquals(userService.count(condition), Long.valueOf(1L));
  }
}
