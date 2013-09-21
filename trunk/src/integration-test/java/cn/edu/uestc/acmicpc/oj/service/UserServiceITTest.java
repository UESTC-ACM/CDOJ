package cn.edu.uestc.acmicpc.oj.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;

/** Integration test cases for {@link UserService}. */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { IntegrationTestContext.class })
public class UserServiceITTest {

  @Autowired
  UserService userService;

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
    } catch (FieldException e) {
      Assert.assertEquals(new FieldException("password",
          "User or password is wrong, please try again"), e);
    }
  }
}
