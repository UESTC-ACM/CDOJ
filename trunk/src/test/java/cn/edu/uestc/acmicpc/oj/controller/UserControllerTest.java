package cn.edu.uestc.acmicpc.oj.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.nio.charset.Charset;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

import com.alibaba.fastjson.JSON;

/** Mock test for {@link UserController}. */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { MockMVCContext.class, WebMVCConfig.class })
public class UserControllerTest {

  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8")
      );
  @Autowired
  private WebApplicationContext context;
  @Autowired
  private UserService userService;

  private MockMvc mockMvc;
  private MockHttpSession session;

  @Before
  public void init() {
    mockMvc = webAppContextSetup(context).build();
    session = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
  }

  @Test
  public void testLogin_successful() throws Exception {
    UserLoginDTO userLoginDTO = new UserLoginDTO();
    UserDTO userDTO = new UserDTO();
    userLoginDTO.setUserName("admin");
    userLoginDTO.setPassword("password");
    when(userService.login(userLoginDTO)).thenReturn(userDTO);
    mockMvc.perform(post("/user/login")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(userLoginDTO))
        .session(session))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
     Assert.assertEquals(userDTO, session.getAttribute("currentUser"));
  }
}
