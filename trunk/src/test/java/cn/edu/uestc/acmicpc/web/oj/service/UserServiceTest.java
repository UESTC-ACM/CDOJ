package cn.edu.uestc.acmicpc.web.oj.service;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.UserService;

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
  public void testFish() {
  }
  /*
  TODO(fish)
  @Test
  public void testLogin_successful() throws AppException, FieldNotUniqueException {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder().build();
    User user = mock(User.class);
    when(user.getUserName()).thenReturn("admin");
    when(user.getPassword()).thenReturn(StringUtil.encodeSHA1("password"));
    when(userDAO.getEntityByUniqueField("userName", userLoginDTO.getUserName()))
        .thenReturn(user);
    Assert.assertNotNull(userService.login(userLoginDTO));
  }

  @Test
  public void testLogin_failed_notSuchUser() throws AppException, FieldNotUniqueException {
    try {
      UserLoginDTO userLoginDTO = UserLoginDTO.builder()
          .setUserName("nullUser")
          .build();
      when(userDAO.getEntityByUniqueField("userName", userLoginDTO.getUserName()))
          .thenReturn(null);
      userService.login(userLoginDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(
          new FieldException("password", "User or password is wrong, please try again"),
          e);
    }
  }

  @Test
  public void testLogin_failed_wrongPassword() throws AppException, FieldNotUniqueException {
    try {
      UserLoginDTO userLoginDTO = UserLoginDTO.builder()
          .setPassword("wrongPassword")
          .build();
      User user = mock(User.class);
      when(user.getUserName()).thenReturn("admin");
      when(user.getPassword()).thenReturn(StringUtil.encodeSHA1("password"));
      when(userDAO.getEntityByUniqueField("userName", userLoginDTO.getUserName()))
          .thenReturn(user);
      userService.login(userLoginDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(
          new FieldException("password", "User or password is wrong, please try again"),
          e);
    }
  }

  @Test
  public void testRegister_successful() throws AppException {
    UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder().build();
    when(globalService.getDepartmentById(userRegisterDTO.getDepartmentId()))
        .thenReturn(mock(Department.class));
    Assert.assertTrue(ObjectUtils.equals(userRegisterDTO, userService.register(userRegisterDTO)));
  }

  @Test
  public void testRegister_failed_passwordDifferent() throws AppException {
    try {
      UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
          .setPassword("password")
          .setPasswordRepeat("passwordRepeat")
          .build();
      userService.register(userRegisterDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(new FieldException("passwordRepeat", "Password do not match."), e);
    }
  }

  @Test
  public void testRegister_failed_containsUnusedWhiteSpaces() throws AppException {
    try {
      UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder()
          .setNickName("nick name  ")
          .build();
      userService.register(userRegisterDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(
          new FieldException("nickName", "Nick name should not have useless blank."),
          e);
    }
  }

  @Test
  public void testRegister_failed_userIsInUsed() throws AppException, FieldNotUniqueException {
    try {
      UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder().build();
      when(userDAO.getEntityByUniqueField("userName", userRegisterDTO.getUserName()))
          .thenReturn(mock(User.class));
      userService.register(userRegisterDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(new FieldException("userName", "User name has been used!"), e);
    }
  }

  @Test
  public void testRegister_failed_emailIsInUsed() throws AppException, FieldNotUniqueException {
    try {
      UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder().build();
      when(userDAO.getEntityByUniqueField("email", userRegisterDTO.getEmail()))
          .thenReturn(mock(User.class));
      userService.register(userRegisterDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(new FieldException("email", "Email has benn used!"), e);
    }
  }

  @Test
  public void testRegister_failed_noSuchDepartment() throws AppException, FieldNotUniqueException {
    try {
      UserRegisterDTO userRegisterDTO = UserRegisterDTO.builder().build();
      when(globalService.getDepartmentById(userRegisterDTO.getDepartmentId())).thenReturn(null);
      userService.register(userRegisterDTO);
      Assert.fail();
    } catch (FieldException e) {
      Assert.assertEquals(
          new FieldException("departmentId", "Please choose a validate department."),
          e);
    }
  }
  */
}
