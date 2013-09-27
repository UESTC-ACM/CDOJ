package cn.edu.uestc.acmicpc.oj.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;

/** Integration test cases for {@link UserService}. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class UserServiceITTest {

  @Autowired
  UserService userService;

  @Test
  public void testStub() {
  }

  /*
  TODO(fish)
  @Test
  public void testLogin_successful() throws AppException {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder().build();
    Assert.assertNotNull(userService.login(userLoginDTO));
  }

  @Test
  public void testLogin_wrongPassword() throws AppException {
    try {
      UserLoginDTO userLoginDTO = UserLoginDTO.builder()
          .setPassword("password2")
          .build();
      userService.login(userLoginDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(new FieldException("password",
          "User or password is wrong, please try again"), e);
    }
  }

  @Test
  public void testLogin_noSuchUser() throws AppException {
    try {
      UserLoginDTO userLoginDTO = UserLoginDTO.builder()
          .setUserName("wrongUserName")
          .build();
      userService.login(userLoginDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(new FieldException("password",
          "User or password is wrong, please try again"), e);
    }
  }

  @Test
  public void testLogin_wrongUserName() throws AppException {
    try {
      UserLoginDTO userLoginDTO = UserLoginDTO.builder()
          .setUserName("nullUserName")
          .build();
      userService.login(userLoginDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(new FieldException("password",
          "User or password is wrong, please try again"), e);
    }
  }

  @Test
  @Ignore("broken with transaction issue")
  public void testRegister_successful() throws AppException {
    UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
        .setUserName("newUser")
        .setEmail("userName@uestc.edu.cn")
        .build();
    userService.register(userRegisterDTO);
  }

  @Test
  public void testGetUserByUserName() throws AppException {
    User user = userService.getUserByUserName("admin");
    Assert.assertEquals("admin", user.getUserName());
  }

  @Test
  public void testGetUserByUserEmail() throws AppException {
    User user = userService.getUserByEmail("acm_admin@uestc.edu.cn");
    Assert.assertEquals("acm_admin@uestc.edu.cn", user.getEmail());
  }

  @Test
  public void testUpdateUser() throws AppException, FieldNotUniqueException {
    User user = userService.getUserByUserName("administrator");
    user.setLastLogin(new Timestamp(new Date().getTime()));
    userService.updateUser(user);
  }
  */
}
