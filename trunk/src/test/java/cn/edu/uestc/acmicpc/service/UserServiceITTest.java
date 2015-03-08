package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.testing.UserProvider;
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
  private UserService userService;

  @Autowired
  private UserProvider userProvider;

  @Test
  public void testGetUserByUserId() throws AppException {
    UserDto user = userProvider.createUser();
    Integer userId = userService.createNewUser(user);
    UserDto result = userService.getUserDtoByUserId(userId);
    Assert.assertEquals(result.getUserId(), userId);
    Assert.assertEquals(result.getUserName(), user.getUserName());
  }

  @Test
  public void testGetUserByUserName() throws AppException {
    UserDto user = userProvider.createUser();
    Integer userId = userService.createNewUser(user);
    UserDto result = userService.getUserDtoByUserName(user.getUserName());
    Assert.assertEquals(result.getUserId(), userId);
    Assert.assertEquals(result.getUserName(), user.getUserName());
  }

  @Test
  public void testGetUserByUserEmail() throws AppException {
    UserDto user = userProvider.createUser();
    Integer userId = userService.createNewUser(user);
    UserDto result = userService.getUserDtoByEmail(user.getEmail());
    Assert.assertEquals(result.getUserId(), userId);
    Assert.assertEquals(result.getEmail(), user.getEmail());
  }

  @Test
  public void testUpdateUser() throws AppException, FieldNotUniqueException {
    Integer userId = userService.createNewUser(userProvider.createUnpersistedUser());
    UserDto user = userService.getUserDtoByUserId(userId);
    user.setLastLogin(new Timestamp(new Date().getTime()));
    userService.updateUser(user);
  }

  @Test
  public void testCount_emptyCondition() throws AppException {
    userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    Assert.assertEquals(userService.count(condition), Long.valueOf(6L));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    Integer[] userIds = userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.startId = userIds[1];
    Assert.assertEquals(userService.count(condition), Long.valueOf(5L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    Integer[] userIds = userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.endId = userIds[1];
    Assert.assertEquals(userService.count(condition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    Integer[] userIds = userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.startId = userIds[1];
    condition.endId = userIds[5] + 100;
    Assert.assertEquals(userService.count(condition), Long.valueOf(5L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    Integer[] userIds = userProvider.createUsers(5);
    UserCondition condition = new UserCondition();
    condition.startId = userIds[2];
    condition.endId = userIds[1];
    Assert.assertEquals(userService.count(condition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byDepartmentId() throws AppException {
    userProvider.createUsers(5);
    UserDto user = userProvider.createUnpersistedUser();
    user.setDepartmentId(2);
    userService.createNewUser(user);
    UserCondition condition = new UserCondition();
    condition.departmentId = 2;
    Assert.assertEquals(userService.count(condition), Long.valueOf(1L));
  }

  @Test
  public void testCount_bySchool() throws AppException {
    userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.school = "UES";
    Assert.assertEquals(userService.count(condition), Long.valueOf(6L));
  }

  @Test
  public void testCount_bySchool_emptyResult() throws AppException {
    userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.school = "USE";
    Assert.assertEquals(userService.count(condition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byUserName() throws AppException {
    userProvider.createUsers(5);
    UserDto user = userProvider.createUnpersistedUser();
    user.setUserName("another name");
    userService.createNewUser(user);
    UserCondition condition = new UserCondition();
    condition.userName = "user";
    Assert.assertEquals(userService.count(condition), Long.valueOf(5L));
  }

  @Test
  public void testCount_byType() throws AppException {
    userProvider.createUsers(5);
    UserDto user = userProvider.createUnpersistedUser();
    user.setType(AuthenticationType.CONSTANT.ordinal());
    userService.createNewUser(user);
    UserCondition condition = new UserCondition();
    condition.type = AuthenticationType.CONSTANT.ordinal();
    Assert.assertEquals(userService.count(condition), Long.valueOf(1L));
  }
}
