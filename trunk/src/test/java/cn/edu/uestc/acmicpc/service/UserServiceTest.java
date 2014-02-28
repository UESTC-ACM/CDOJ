package cn.edu.uestc.acmicpc.service;

import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.JoinedType;
import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ObjectUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases for {@link UserService}.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class})
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
        eq("userId"), eq(userDTO.getUserId()))).thenReturn(userDTO);
    Assert.assertEquals(userService.getUserDTOByUserId(userDTO.getUserId()), userDTO);
    verify(userDAO).getDTOByUniqueField(eq(UserDTO.class), Mockito.<UserDTO.Builder>any(),
        eq("userId"), eq(userDTO.getUserId()));
  }

  @Test
  public void testGetUserDTOByUserName() throws AppException {
    UserDTO userDTO = UserDTO.builder().build();
    when(userDAO.getDTOByUniqueField(eq(UserDTO.class), Mockito.<UserDTO.Builder>any(),
        eq("userName"), eq(userDTO.getUserName()))).thenReturn(userDTO);
    Assert.assertEquals(userService.getUserDTOByUserName(userDTO.getUserName()), userDTO);
    verify(userDAO).getDTOByUniqueField(eq(UserDTO.class), Mockito.<UserDTO.Builder>any(),
        eq("userName"), eq(userDTO.getUserName()));
  }

  @Test
  public void testGetUserCenterDTOByUserName() throws AppException {
    UserCenterDTO userCenterDTO = UserCenterDTO.builder().build();
    when(userDAO.getDTOByUniqueField(eq(UserCenterDTO.class), Mockito.<UserCenterDTO.Builder>any(),
        eq("userName"), eq(userCenterDTO.getUserName()))).thenReturn(userCenterDTO);
    Assert.assertEquals(userService.getUserCenterDTOByUserName(userCenterDTO.getUserName()),
        userCenterDTO);
    verify(userDAO).getDTOByUniqueField(eq(UserCenterDTO.class),
        Mockito.<UserCenterDTO.Builder>any(), eq("userName"), eq(userCenterDTO.getUserName()));
  }

  @Test
  public void testGetUserDTOByEmail() throws AppException {
    UserDTO userDTO = UserDTO.builder().build();
    when(userDAO.getDTOByUniqueField(eq(UserDTO.class), Mockito.<UserDTO.Builder>any(),
        eq("email"), eq(userDTO.getEmail()))).thenReturn(userDTO);
    Assert.assertEquals(userService.getUserDTOByEmail(userDTO.getEmail()), userDTO);
    verify(userDAO).getDTOByUniqueField(eq(UserDTO.class), Mockito.<UserDTO.Builder>any(),
        eq("email"), eq(userDTO.getEmail()));
  }

  @Test
  public void testUpdateUser() throws AppException {
    UserDTO userDTO = UserDTO.builder().build();
    User user = new User();
    user.setUserId(userDTO.getUserId());
    when(userDAO.get(userDTO.getUserId())).thenReturn(user);
    userService.updateUser(userDTO);
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userDAO).update(captor.capture());
    Assert.assertTrue(ObjectUtil.entityEquals(userDTO, captor.getValue()));
    verify(userDAO).get(userDTO.getUserId());
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateUser_userNotFound() throws AppException {
    UserDTO userDTO = UserDTO.builder().build();
    when(userDAO.get(userDTO.getUserId())).thenReturn(null);
    userService.updateUser(userDTO);
    Assert.fail();
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateUser_userFoundWithNullId() throws AppException {
    UserDTO userDTO = UserDTO.builder().build();
    User user = mock(User.class);
    when(userDAO.get(userDTO.getUserId())).thenReturn(user);
    when(user.getUserId()).thenReturn(null);
    userService.updateUser(userDTO);
    Assert.fail();
  }

  @Test
  public void testCreateNewUser() throws AppException {
    UserDTO userDTO = UserDTO.builder().build();
    userService.createNewUser(userDTO);
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userDAO).add(captor.capture());
    User user = captor.getValue();
    // the user is not persisted.
    Assert.assertNull(user.getUserId());
    user.setUserId(userDTO.getUserId());
    Assert.assertTrue(ObjectUtil.entityEquals(userDTO, user));
  }

  @Test
  public void testSearch() throws AppException {
    ArgumentCaptor<Condition> captor = ArgumentCaptor.forClass(Condition.class);
    PageInfo pageInfo = PageInfo.create(300L, Global.RECORD_PER_PAGE, 10, 2L);
    userService.getUserListDTOList(new UserCondition(), pageInfo);
    verify(userDAO).findAll(eq(UserListDTO.class),
        isA(UserListDTO.Builder.class), captor.capture());
    Condition condition = captor.getValue();
    Assert.assertEquals(condition.getJoinedType(), JoinedType.AND);
    Assert.assertEquals(condition.getPageInfo(), pageInfo);
  }

  @Test
  public void testCount() throws AppException {
    UserCondition userCondition = mock(UserCondition.class);
    Condition condition = mock(Condition.class);
    when(userCondition.getCondition()).thenReturn(condition);
    userService.count(userCondition);
    verify(userDAO).count(condition);
  }
}
