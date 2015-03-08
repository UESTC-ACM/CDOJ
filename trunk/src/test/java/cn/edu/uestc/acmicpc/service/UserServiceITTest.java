package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.testing.TestUtil;
import cn.edu.uestc.acmicpc.service.testing.UserProvider;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import static com.google.common.truth.Truth.assertThat;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Integration test cases for {@link UserService}.
 */
@SuppressWarnings("deprecation")
public class UserServiceITTest extends PersistenceITTest {

  @Autowired private UserService userService;

  @Override
  public void setUpDefaultUser() throws Exception {
    // Do nothing
  }

  @Test
  public void testGetUserByUserId() throws AppException {
    UserDto user = userProvider.createUser();
    Integer userId = user.getUserId();
    UserDto result = userService.getUserDtoByUserId(userId);
    Assert.assertEquals(result.getUserId(), userId);
    Assert.assertEquals(result.getUserName(), user.getUserName());
  }

  @Test
  public void testGetUserByUserName() throws AppException {
    UserDto user = userProvider.createUser();
    Integer userId = user.getUserId();
    UserDto result = userService.getUserDtoByUserName(user.getUserName());
    Assert.assertEquals(result.getUserId(), userId);
    Assert.assertEquals(result.getUserName(), user.getUserName());
  }

  @Test
  public void testGetUserByUserEmail() throws AppException {
    UserDto user = userProvider.createUser();
    Integer userId = user.getUserId();
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
    assertThat(userService.count(condition)).isEqualTo(6);
  }

  @Test
  public void testCount_byStartId() throws AppException {
    List<Integer> userIds = userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.startId = userIds.get(1);
    assertThat(userService.count(condition)).isEqualTo(5);
  }

  @Test
  public void testCount_byEndId() throws AppException {
    List<Integer> userIds = userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.endId = userIds.get(1);
    assertThat(userService.count(condition)).isEqualTo(2);
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    List<Integer> userIds = userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.startId = userIds.get(1);
    condition.endId = userIds.get(4);
    assertThat(userService.count(condition)).isEqualTo(4);
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    List<Integer> userIds = userProvider.createUsers(5);
    UserCondition condition = new UserCondition();
    condition.startId = userIds.get(2);
    condition.endId = userIds.get(1);
    assertThat(userService.count(condition)).isEqualTo(0);
  }

  @Test
  public void testCount_byDepartmentId() throws AppException {
    userProvider.createUsers(5);
    UserDto user = userProvider.createUnpersistedUser();
    user.setDepartmentId(2);
    userService.createNewUser(user);
    UserCondition condition = new UserCondition();
    condition.departmentId = 2;
    assertThat(userService.count(condition)).isEqualTo(1);
  }

  @Test
  public void testCount_bySchool() throws AppException {
    userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.school = "UES";
    assertThat(userService.count(condition)).isEqualTo(6);
  }

  @Test
  public void testCount_bySchool_emptyResult() throws AppException {
    userProvider.createUsers(6);
    UserCondition condition = new UserCondition();
    condition.school = "USE";
    assertThat(userService.count(condition)).isEqualTo(0);
  }

  @Test
  public void testCount_byUserName() throws AppException {
    for (int i = 0; i < 5; i++) {
      userProvider.createUser("user" + TestUtil.getUniqueId());
    }

    UserDto user = userProvider.createUnpersistedUser();
    user.setUserName("KasuganoSora");
    userService.createNewUser(user);
    UserCondition condition = new UserCondition();
    condition.userName = "user";
    assertThat(userService.count(condition)).isEqualTo(5);
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
