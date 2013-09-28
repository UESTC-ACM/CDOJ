package cn.edu.uestc.acmicpc.oj.controller;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.config.WebMVCConfig;
import cn.edu.uestc.acmicpc.db.dto.impl.DepartmentDTO;
import cn.edu.uestc.acmicpc.oj.controller.index.IndexController;
import cn.edu.uestc.acmicpc.oj.service.iface.DepartmentService;

/** Test cases for {@link IndexController}. */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { TestContext.class, WebMVCConfig.class })
public class IndexControllerTest extends ControllerTest {

  private MockMvc mockMvc;

  @Autowired
  @Qualifier("mockDepartmentService")
  private DepartmentService departmentService;

  @Before
  public void init() {
    Mockito.reset(departmentService);
    mockMvc = initControllers(new IndexController(departmentService));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testVisitIndex() throws Exception {
    List<DepartmentDTO> list = mock(List.class);
    when(departmentService.getDepartmentList()).thenReturn(list);
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index/index"))
        .andExpect(model().attribute("message", "home page."))
        .andExpect(model().attribute("departmentList", list));
  }
}
