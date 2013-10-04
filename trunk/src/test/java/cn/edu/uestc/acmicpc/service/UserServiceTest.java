package cn.edu.uestc.acmicpc.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/** Test cases for {@link UserService}. */
@WebAppConfiguration
@ContextConfiguration(classes = { TestContext.class })
public class UserServiceTest extends AbstractTestNGSpringContextTests {

  @Autowired
  @Qualifier("realUserService")
  private UserService userService;

  @Autowired
  @Qualifier("mockUserDAO")
  private IUserDAO userDAO;

  @Autowired
  @Qualifier("mockGlobalService")
  private GlobalService globalService;

  @Autowired
  @Qualifier("mockEmailService")
  private EmailService emailService;

  @BeforeMethod
  public void init() {
    Mockito.reset(userDAO, globalService);
  }

  @Test
  public void testGetUserDTOByUserId() throws AppException {
    UserDTO userDTO = UserDTO.builder().build();
    when(userDAO.getDTOByUniqueField(eq(UserDTO.class), Mockito.<UserDTO.Builder>any(),
        eq("userId"), eq(Integer.valueOf(1)))).thenReturn(userDTO);
    Assert.assertEquals(userService.getUserDTOByUserId(1), userDTO);
  }
}
