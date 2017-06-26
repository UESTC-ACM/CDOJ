package cn.edu.uestc.acmicpc.web.oj.controller;

import cn.edu.uestc.acmicpc.testing.ControllerTest;
import cn.edu.uestc.acmicpc.web.oj.controller.problem.ProblemController;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.BeforeMethod;

/**
 * Mock test for {@link ProblemController}.
 */
public class ProblemControllerTest extends ControllerTest {

  @Autowired private ProblemController problemController;

  @Override
  @BeforeMethod
  public void init() {
    super.init();
    mockMvc = initControllers(problemController);
    session = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
  }

}
