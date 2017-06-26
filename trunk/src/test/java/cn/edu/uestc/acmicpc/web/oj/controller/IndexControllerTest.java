package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cn.edu.uestc.acmicpc.testing.ControllerTest;
import cn.edu.uestc.acmicpc.web.oj.controller.index.IndexController;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases for {@link IndexController}.
 */
public class IndexControllerTest extends ControllerTest {

  @Autowired private IndexController indexController;

  @Override
  @BeforeMethod
  public void init() {
    super.init();
    mockMvc = initControllers(indexController);
  }

  @Test
  public void testVisitIndex() throws Exception {
    mockMvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"));
  }
}
