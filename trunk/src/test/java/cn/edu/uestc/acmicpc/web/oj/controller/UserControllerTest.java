package cn.edu.uestc.acmicpc.web.oj.controller;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserLoginDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserRegisterDto;
import cn.edu.uestc.acmicpc.testing.ControllerTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.web.oj.controller.user.UserController;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;

import java.util.UUID;

/**
 * Mock test for {@link UserController}.
 */
public class UserControllerTest extends ControllerTest {

  private final String URL_LOGIN = "/user/login";
  private final String URL_LOGOUT = "/user/logout";
  private final String URL_REGISTER = "/user/register";

  private final String PASSWORD_ENCODED = "5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8";

  @Autowired
  private UserController userController;

  @Override
  @BeforeMethod
  public void init() {
    super.init();
    mockMvc = initControllers(userController);
    session = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
  }

  @Test
  public void testLogin_successful() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("admin")
        .setPassword(PASSWORD_ENCODED)
        .build();
    UserDto userDto = UserDto.builder()
        .setUserName("admin")
        .setPassword(PASSWORD_ENCODED)
        .build();
    when(userService.getUserDtoByUserName(userLoginDto.getUserName())).thenReturn(userDto);
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
    Assert.assertEquals(session.getAttribute("currentUser"), userDto);
  }

  @Test
  public void testLogin_invalidUserName_null() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName(null)
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your user name.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_empty() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("")
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_tooShort() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName(StringUtil.repeat("a", 3))
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_tooLong() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName(StringUtil.repeat("a", 25))
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_invalid() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("%$#@5%")
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidPassword_null() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("admin")
        .setPassword(null)
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidPassword_empty() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("admin")
        .setPassword("")
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidPassword_tooShort() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("admin")
        .setPassword(StringUtil.repeat("a", 5))
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidPassword_tooLong() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("admin")
        .setPassword(StringUtil.repeat("a", 41))
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_failed_wrongUserNameOrPassword() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("admin")
        .setPassword(StringUtil.encodeSHA1("wrongPassword"))
        .build();
    UserDto userDto = UserDto.builder()
        .setUserName("admin")
        .setPassword(PASSWORD_ENCODED)
        .build();
    when(userService.getUserDtoByUserName(userLoginDto.getUserName())).thenReturn(userDto);
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("password")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("User or password is wrong, please try again")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_failed_bothUserNameAndPassword_null() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName(null)
        .setPassword(null)
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(2)))
        .andExpect(jsonPath("$.field[*].field", containsInAnyOrder("password", "userName")))
        .andExpect(jsonPath("$.field[*].objectName",
            containsInAnyOrder("userLoginDto", "userLoginDto")))
        .andExpect(jsonPath("$.field[*].defaultMessage", containsInAnyOrder(
            "Please enter your password.",
            "Please enter your user name.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_failed_bothUserNameAndPassword_empty() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("")
        .setPassword("")
        .build();
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(2)))
        .andExpect(jsonPath("$.field[*].field", containsInAnyOrder("password", "userName")))
        .andExpect(jsonPath("$.field[*].objectName",
            containsInAnyOrder("userLoginDto", "userLoginDto")))
        .andExpect(jsonPath("$.field[*].defaultMessage", containsInAnyOrder(
            "Please enter your password.",
            "Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_failed_serviceError() throws Exception {
    UserLoginDto userLoginDto = UserLoginDto.builder()
        .setUserName("admin")
        .setPassword(PASSWORD_ENCODED)
        .build();
    when(userService.getUserDtoByUserName(userLoginDto.getUserName()))
        .thenThrow(new AppException("service error"));
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("service error")));
    Assert.assertNull(session.getAttribute("currentUser"));
  }

  @Test
  public void testLogout_successful() throws Exception {
    UserDto currentUser = UserDto.builder().build();
    session.putValue("currentUser", currentUser);
    mockMvc.perform(post(URL_LOGOUT)
        .contentType(APPLICATION_JSON_UTF8)
        .session(session))
        .andExpect(status().isOk());
    Assert.assertTrue(session.isInvalid());
  }

  @Test
  public void testRegister_successfully() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder().build();
    when(userService.getUserDtoByUserName(userRegisterDto.getUserName())).thenReturn(null)
        .thenReturn(mock(UserDto.class));
    when(userService.getUserDtoByEmail(userRegisterDto.getEmail())).thenReturn(null);
    when(departmentService.getDepartmentName(userRegisterDto.getDepartmentId()))
        .thenReturn("department");
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
  }

  @Test
  public void testRegister_failed_userName_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setUserName(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your user name.")));
  }

  @Test
  public void testRegister_failed_userName_empty() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setUserName("")
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
  }

  @Test
  public void testRegister_failed_userName_whiteSpaces() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setUserName(StringUtil.repeat(" ", 10))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
  }

  @Test
  public void testRegister_failed_userName_tooShort() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setUserName(StringUtil.repeat("a", 3))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
  }

  @Test
  public void testRegister_failed_userName_tooLong() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setUserName(StringUtil.repeat("a", 25))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
  }

  @Test
  public void testRegister_failed_userName_invalid() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setUserName("#userName")
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
  }

  @Test
  public void testRegister_failed_password_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPassword(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("password")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
  }

  @Test
  public void testRegister_failed_password_empty() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPassword("")
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
  }

  @Test
  public void testRegister_failed_password_tooShort() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPassword(StringUtil.repeat("a", 5))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
  }

  @Test
  public void testRegister_failed_password_tooLong() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPassword(StringUtil.repeat("a", 41))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your password.")));
  }

  @Test
  public void testRegister_failed_passwordRepeat_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPasswordRepeat(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("passwordRepeat")))
        .andExpect(jsonPath("$.field[0].objectName", is("passwordRepeat")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please repeat your password.")));
  }

  @Test
  public void testRegister_failed_passwordRepeat_empty() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPasswordRepeat("")
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("passwordRepeat")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please repeat your password.")));
  }

  @Test
  public void testRegister_failed_passwordRepeat_tooShort() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPasswordRepeat(StringUtil.repeat("a", 5))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("passwordRepeat")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please repeat your password.")));
  }

  @Test
  public void testRegister_failed_passwordRepeat_tooLong() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPasswordRepeat(StringUtil.repeat("a", 41))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("passwordRepeat")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please repeat your password.")));
  }

  @Test
  public void testRegister_failed_password_different() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setPassword(StringUtil.encodeSHA1("12345678"))
        .setPasswordRepeat(StringUtil.encodeSHA1("123456789"))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("passwordRepeat")))
        .andExpect(jsonPath("$.field[0].objectName", is("passwordRepeat")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Password do not match.")));
  }

  @Test
  public void testRegister_failed_nickName_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setNickName(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("nickName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your nick name.")));
  }

  @Test
  public void testRegister_failed_nickName_empty() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setNickName("")
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("nickName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 2-20 characters.")));
  }

  @Test
  public void testRegister_failed_nickName_whiteSpaces() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setNickName(StringUtil.repeat(" ", 10))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("nickName")))
        .andExpect(jsonPath("$.field[0].objectName", is("nickName")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Nick name should not have useless blank.")));
  }

  @Test
  public void testRegister_failed_nickName_tooShort() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setNickName(StringUtil.repeat("a", 1))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("nickName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 2-20 characters.")));
  }

  @Test
  public void testRegister_failed_nickName_tooLong() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setNickName(StringUtil.repeat("a", 21))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("nickName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 2-20 characters.")));
  }

  @Test
  public void testRegister_failed_email_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setEmail(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("email")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter a valid email address.")));
  }

  public void testRegister_failed_email_invalid(String email) throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setEmail(email)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("email")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter a valid email address.")));
  }

  @Test
  public void testRegister_failed_email_empty() throws Exception {
    testRegister_failed_email_invalid("");
  }

  @Test
  public void testRegister_failed_email_case1() throws Exception {
    testRegister_failed_email_invalid("email");
  }

  @Test
  public void testRegister_failed_email_case2() throws Exception {
    testRegister_failed_email_invalid("@uestc.edu.cn");
  }

  @Test
  public void testRegister_failed_email_case3() throws Exception {
    testRegister_failed_email_invalid("email@");
  }

  @Test
  public void testRegister_failed_email_case4() throws Exception {
    testRegister_failed_email_invalid("  email@uestc.edu.cn");
  }

  @Test
  public void testRegister_failed_email_case5() throws Exception {
    testRegister_failed_email_invalid("email@uestc.edu.cn   ");
  }

  @Test
  public void testRegister_failed_email_case6() throws Exception {
    testRegister_failed_email_invalid("email@uestc@com");
  }

  @Test
  public void testRegister_failed_school_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setSchool(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("school")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your school name.")));
  }

  @Test
  public void testRegister_failed_school_empty() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setSchool("")
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("school")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 1-100 characters.")));
  }

  @Test
  public void testRegister_failed_school_tooShort() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setSchool(StringUtil.repeat("a", 0))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("school")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 1-100 characters.")));
  }

  @Test
  public void testRegister_failed_school_tooLong() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setSchool(StringUtil.repeat("a", 101))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("school")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 1-100 characters.")));
  }

  @Test
  public void testRegister_failed_departmentId_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setDepartmentId(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("departmentId")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please select your department.")));
  }

  @Test
  public void testRegister_failed_studentId_null() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setStudentId(null)
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("studentId")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter your student ID.")));
  }

  @Test
  public void testRegister_failed_studentId_empty() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setStudentId("")
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("studentId")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 1-20 characters.")));
  }

  @Test
  public void testRegister_failed_studentId_tooShort() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setStudentId(StringUtil.repeat("a", 0))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("studentId")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 1-20 characters.")));
  }

  @Test
  public void testRegister_failed_studentId_tooLong() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder()
        .setStudentId(StringUtil.repeat("a", 21))
        .build();
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("studentId")))
        .andExpect(jsonPath("$.field[0].objectName", is("userRegisterDto")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 1-20 characters.")));
  }

  @Test
  public void testRegister_failed_usedUserName() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder().build();
    when(userService.getUserDtoByUserName(userRegisterDto.getUserName()))
        .thenReturn(mock(UserDto.class));
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userName")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("User name has been used!")));
  }

  @Test
  public void testRegister_failed_usedEmail() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder().build();
    when(userService.getUserDtoByEmail(userRegisterDto.getEmail())).thenReturn(mock(UserDto.class));
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("email")))
        .andExpect(jsonPath("$.field[0].objectName", is("email")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Email has benn used!")));
  }

  @Test
  public void testRegister_failed_departmentNotFound() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder().build();
    when(departmentService.getDepartmentName(userRegisterDto.getDepartmentId())).thenReturn(null);
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("departmentId")))
        .andExpect(jsonPath("$.field[0].objectName", is("departmentId")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please choose a validate department.")));
  }

  @Test
  public void testUser_register_login_logout() throws Exception {
    UserRegisterDto userRegisterDto = UserRegisterDto.builder().build();
    UserLoginDto userLoginDto = UserLoginDto.builder().build();
    UserDto userDto = UserDto.builder()
        .setPassword(userLoginDto.getPassword())
        .build();
    when(userService.getUserDtoByUserName(userRegisterDto.getUserName()))
        .thenReturn(null).thenReturn(userDto);
    when(userService.getUserDtoByEmail(userRegisterDto.getEmail())).thenReturn(null);
    when(departmentService.getDepartmentName(userRegisterDto.getDepartmentId()))
        .thenReturn("department");
    mockMvc.perform(post(URL_REGISTER)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userRegisterDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
    mockMvc.perform(post(URL_LOGIN)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDto))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
    Assert.assertEquals(session.getAttribute("currentUser"), userDto);
    mockMvc.perform(post(URL_LOGOUT)
        .contentType(APPLICATION_JSON_UTF8)
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
    Assert.assertTrue(session.isInvalid());
  }
}
