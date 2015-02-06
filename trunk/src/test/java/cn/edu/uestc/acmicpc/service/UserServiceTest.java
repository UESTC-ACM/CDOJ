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
import cn.edu.uestc.acmicpc.db.dao.iface.UserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDto;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ObjectUtil;
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
  @Qualifier("mockUserDao")
  private UserDao userDao;

  @Autowired
  @Qualifier("mockEmailService")
  private EmailService emailService;

  @BeforeMethod
  public void init() {
    Mockito.reset(userDao);
  }

  @Test
  public void testGetUserDtoByUserId() throws AppException {
    UserDto userDto = UserDto.builder().build();
    when(userDao.getDtoByUniqueField(eq(UserDto.class), Mockito.<UserDto.Builder>any(),
        eq("userId"), eq(userDto.getUserId()))).thenReturn(userDto);
    Assert.assertEquals(userService.getUserDtoByUserId(userDto.getUserId()), userDto);
    verify(userDao).getDtoByUniqueField(eq(UserDto.class), Mockito.<UserDto.Builder>any(),
        eq("userId"), eq(userDto.getUserId()));
  }

  @Test
  public void testGetUserDtoByUserName() throws AppException {
    UserDto userDto = UserDto.builder().build();
    when(userDao.getDtoByUniqueField(eq(UserDto.class), Mockito.<UserDto.Builder>any(),
        eq("userName"), eq(userDto.getUserName()))).thenReturn(userDto);
    Assert.assertEquals(userService.getUserDtoByUserName(userDto.getUserName()), userDto);
    verify(userDao).getDtoByUniqueField(eq(UserDto.class), Mockito.<UserDto.Builder>any(),
        eq("userName"), eq(userDto.getUserName()));
  }

  @Test
  public void testGetUserCenterDtoByUserName() throws AppException {
    UserCenterDto userCenterDto = UserCenterDto.builder().build();
    when(userDao.getDtoByUniqueField(eq(UserCenterDto.class), Mockito.<UserCenterDto.Builder>any(),
        eq("userName"), eq(userCenterDto.getUserName()))).thenReturn(userCenterDto);
    Assert.assertEquals(userService.getUserCenterDtoByUserName(userCenterDto.getUserName()),
        userCenterDto);
    verify(userDao).getDtoByUniqueField(eq(UserCenterDto.class),
        Mockito.<UserCenterDto.Builder>any(), eq("userName"), eq(userCenterDto.getUserName()));
  }

  @Test
  public void testGetUserDtoByEmail() throws AppException {
    UserDto userDto = UserDto.builder().build();
    when(userDao.getDtoByUniqueField(eq(UserDto.class), Mockito.<UserDto.Builder>any(),
        eq("email"), eq(userDto.getEmail()))).thenReturn(userDto);
    Assert.assertEquals(userService.getUserDtoByEmail(userDto.getEmail()), userDto);
    verify(userDao).getDtoByUniqueField(eq(UserDto.class), Mockito.<UserDto.Builder>any(),
        eq("email"), eq(userDto.getEmail()));
  }

  @Test
  public void testUpdateUser() throws AppException {
    UserDto userDto = UserDto.builder().build();
    User user = new User();
    user.setUserId(userDto.getUserId());
    when(userDao.get(userDto.getUserId())).thenReturn(user);
    userService.updateUser(userDto);
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userDao).addOrUpdate(captor.capture());
    Assert.assertTrue(ObjectUtil.entityEquals(userDto, captor.getValue()));
    verify(userDao).get(userDto.getUserId());
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateUser_userNotFound() throws AppException {
    UserDto userDto = UserDto.builder().build();
    when(userDao.get(userDto.getUserId())).thenReturn(null);
    userService.updateUser(userDto);
    Assert.fail();
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateUser_userFoundWithNullId() throws AppException {
    UserDto userDto = UserDto.builder().build();
    User user = mock(User.class);
    when(userDao.get(userDto.getUserId())).thenReturn(user);
    when(user.getUserId()).thenReturn(null);
    userService.updateUser(userDto);
    Assert.fail();
  }

  @Test
  public void testCreateNewUser() throws AppException {
    UserDto userDto = UserDto.builder().build();
    userService.createNewUser(userDto);
    ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
    verify(userDao).addOrUpdate(captor.capture());
    User user = captor.getValue();
    // the user is not persisted.
    Assert.assertNull(user.getUserId());
    user.setUserId(userDto.getUserId());
    Assert.assertTrue(ObjectUtil.entityEquals(userDto, user));
  }

  @Test
  public void testSearch() throws AppException {
    ArgumentCaptor<Condition> captor = ArgumentCaptor.forClass(Condition.class);
    PageInfo pageInfo = PageInfo.create(300L, 20L, 10, 2L);
    userService.getUserListDtoList(new UserCondition(), pageInfo);
    verify(userDao).findAll(eq(UserListDto.class),
        isA(UserListDto.Builder.class), captor.capture());
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
    verify(userDao).count(condition);
  }
}
