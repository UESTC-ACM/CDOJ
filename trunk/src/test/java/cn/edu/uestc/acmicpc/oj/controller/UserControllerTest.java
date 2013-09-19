package cn.edu.uestc.acmicpc.oj.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import cn.edu.uestc.acmicpc.config.MockMVCContext;
import cn.edu.uestc.acmicpc.config.WebMVCConfig;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import com.alibaba.fastjson.JSON;

/** Mock test for {@link UserController}. */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MockMVCContext.class, WebMVCConfig.class })
public class UserControllerTest extends ControllerTest {

  @Autowired
  private WebApplicationContext context;
  @Autowired
  private UserService userService;

  private MockMvc mockMvc;
  private MockHttpSession session;

  @Before
  public void init() {
    Mockito.reset(userService);
    mockMvc = webAppContextSetup(context).build();
    session = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
  }

  @Test
  public void testLogin_successful() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("admin")
        .setPassword("password")
        .build();
    UserDTO userDTO = UserDTO.builder()
        .setUserName("admin")
        .setPassword("password")
        .build();
    when(userService.login(Mockito.<UserLoginDTO> any())).thenReturn(userDTO);
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
    Assert.assertEquals(userDTO, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_empty() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("")
        .setPassword("password")
        .build();
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDTO")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_tooShort() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName(StringUtil.repeat("a", 3))
        .setPassword("password")
        .build();
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDTO")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_tooLong() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName(StringUtil.repeat("a", 25))
        .setPassword("password")
        .build();
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDTO")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidUserName_invalid() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("%$#@5%")
        .setPassword("password")
        .build();
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("userName")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDTO")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidPassword_empty() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("admin")
        .setPassword("")
        .build();
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDTO")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 6-20 characters.")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidPassword_tooShort() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("admin")
        .setPassword(StringUtil.repeat("a", 5))
        .build();
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDTO")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 6-20 characters.")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_invalidPassword_tooLong() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("admin")
        .setPassword(StringUtil.repeat("a", 21))
        .build();
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("userLoginDTO")))
        .andExpect(jsonPath("$.field[0].defaultMessage", is("Please enter 6-20 characters.")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_failed_wrongUserNameOrPassword() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("admin")
        .setPassword("wrongPassword")
        .build();
    when(userService.login(Mockito.<UserLoginDTO> any())).thenReturn(null);
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("password")))
        .andExpect(jsonPath("$.field[0].objectName", is("password")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("User or password is wrong, please try again")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }

  @Test
  public void testLogin_failed_serviceError() throws Exception {
    UserLoginDTO userLoginDTO = UserLoginDTO.builder()
        .setUserName("admin")
        .setPassword("password")
        .build();
    when(userService.login(Mockito.<UserLoginDTO> any()))
        .thenThrow(new AppException("service error"));
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("service error")));
    Assert.assertEquals(null, session.getAttribute("currentUser"));
  }
}
