package cn.edu.uestc.acmicpc.service;

import static com.google.common.truth.Truth.assertThat;

import cn.edu.uestc.acmicpc.db.criteria.UserCriteria;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.testing.TestUtil;
import cn.edu.uestc.acmicpc.service.testing.UserProvider;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test cases for {@link UserService}.
 */
public class UserServiceITTest extends PersistenceITTest {

  @Autowired
  private UserService userService;

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
    assertThat(result).isNotNull();
    assertThat(result.getUserId()).isEqualTo(userId);
    assertThat(result.getUserName()).isEqualTo(user.getUserName());
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
    Integer userId = userService.createNewUser(UserProvider.createUnpersistedUser());
    UserDto user = userService.getUserDtoByUserId(userId);
    user.setLastLogin(new Timestamp(new Date().getTime()));
    userService.updateUser(user);
  }

  @Test
  public void testCount_emptyCriteria() throws AppException {
    userProvider.createUsers(6);
    UserCriteria criteria = new UserCriteria();
    assertThat(userService.count(criteria)).isEqualTo(6);
  }

  @Test
  public void testCount_byDepartmentId() throws AppException {
    userProvider.createUsers(5);
    UserDto user = UserProvider.createUnpersistedUser();
    user.setDepartmentId(2);
    userService.createNewUser(user);
    UserCriteria criteria = new UserCriteria();
    criteria.departmentId = 2;
    assertThat(userService.count(criteria)).isEqualTo(1);
  }

  @Test
  public void testCount_bySchool() throws AppException {
    userProvider.createUsers(6);
    UserCriteria criteria = new UserCriteria();
    criteria.school = "UES";
    assertThat(userService.count(criteria)).isEqualTo(6);
  }

  @Test
  public void testCount_bySchool_emptyResult() throws AppException {
    userProvider.createUsers(6);
    UserCriteria criteria = new UserCriteria();
    criteria.school = "USE";
    assertThat(userService.count(criteria)).isEqualTo(0);
  }

  @Test
  public void testCount_byUserName() throws AppException {
    for (int i = 0; i < 5; i++) {
      userProvider.createUser("user" + TestUtil.getUniqueId());
    }

    UserDto user = UserProvider.createUnpersistedUser();
    user.setUserName("KasuganoSora");
    userService.createNewUser(user);
    UserCriteria criteria = new UserCriteria();
    criteria.userName = "user";
    assertThat(userService.count(criteria)).isEqualTo(5);
  }

  @Test
  public void testCount_byType() throws AppException {
    userProvider.createUsers(5);
    UserDto user = UserProvider.createUnpersistedUser();
    user.setType(AuthenticationType.CONSTANT.ordinal());
    userService.createNewUser(user);
    UserCriteria criteria = new UserCriteria();
    criteria.type = AuthenticationType.CONSTANT.ordinal();
    Assert.assertEquals(userService.count(criteria), Long.valueOf(1L));
  }
}
