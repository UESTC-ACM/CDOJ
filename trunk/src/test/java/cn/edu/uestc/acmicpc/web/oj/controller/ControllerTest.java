package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import cn.edu.uestc.acmicpc.config.WebMVCResource;
import cn.edu.uestc.acmicpc.db.dto.impl.department.DepartmentDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.util.settings.Global.AuthenticationType;

/**
 * Abstract test to define constant variables for controller tests.
 */
public abstract class ControllerTest extends AbstractTestNGSpringContextTests {

  protected MockMvc mockMvc;
  protected MockHttpSession session;

  protected List<DepartmentDTO> departmentList = new ArrayList<DepartmentDTO>();

  @Autowired
  protected DepartmentService departmentService;

  protected List<AuthenticationType> authenticationTypeList = new ArrayList<AuthenticationType>();

  @Autowired
  protected GlobalService globalService;

  /**
   * Application JSON type with UTF-8 character set.
   */
  protected static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(),
      MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8"));

  protected void init() {
    when(departmentService.getDepartmentList()).thenReturn(departmentList);
    when(globalService.getAuthenticationTypeList()).thenReturn(authenticationTypeList);
  }

  @Autowired
  protected WebApplicationContext context;

  protected MockMvc initControllers(Object... objects) {
    return standaloneSetup(objects)
        .setViewResolvers(WebMVCResource.viewResolver())
        .setMessageConverters(WebMVCResource.messageConverters())
        .build();
  }
}
