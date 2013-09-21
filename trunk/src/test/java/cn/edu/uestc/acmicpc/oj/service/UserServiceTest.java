package cn.edu.uestc.acmicpc.oj.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import cn.edu.uestc.acmicpc.config.MockDAOContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/** Test cases for {@link UserService}. */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MockDAOContext.class })
public class UserServiceTest {

  private static final Logger LOGGER = LogManager.getLogger(UserServiceTest.class);

  @Autowired
  private UserService userService;
  @Autowired
  private IUserDAO userDAO;

  @Test
  public void testLogin_successful() throws AppException, FieldNotUniqueException {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder().build();
    User user = mock(User.class);
    when(user.getUserName()).thenReturn("admin");
    when(user.getPassword()).thenReturn(StringUtil.encodeSHA1("password"));
    // when(userDAO.getEntityByUniqueField("userName", userLoginDTO.getUserName()))
    // .thenReturn(user);
    LOGGER.warn("userDAO: " + userDAO);
    LOGGER.warn("userService: " + userService);
    Assert.assertNotNull(userService.login(userLoginDTO));
  }
}
