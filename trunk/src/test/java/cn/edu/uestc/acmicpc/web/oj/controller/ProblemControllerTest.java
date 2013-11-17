package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemShowDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.EmailService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.iface.UserService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.problem.ProblemController;

import com.alibaba.fastjson.JSON;

/** Mock test for {@link ProblemController}. */
@WebAppConfiguration
@ContextConfiguration(classes = { TestContext.class })
public class ProblemControllerTest extends ControllerTest {

  private final String URL_SHOW = "/problem/show";

  @Autowired
  @Qualifier("mockProblemService")
  private ProblemService problemService;

  @Autowired
  @Qualifier("mockGlobalService")
  private GlobalService globalService;

  @Autowired
  @Qualifier("mockLanguageService")
  private LanguageService languageService;

  @Autowired
  @Qualifier("mockStatusService")
  private StatusService statusService;

  @Autowired
  @Qualifier("mockDepartmentService")
  private DepartmentService departmentService;

  private MockMvc mockMvc;
  private MockHttpSession session;

  @BeforeMethod
  public void init() {
    Mockito.reset(problemService, globalService,
        statusService, languageService, departmentService);
    ProblemController problemController = new ProblemController();
    problemController.setGlobalService(globalService);
    problemController.setProblemService(problemService);
    problemController.setStatusService(statusService);
    problemController.setLanguageService(languageService);
    problemController.setDepartmentService(departmentService);
    mockMvc = initControllers(problemController);
    session = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
  }

  @Test
  public void testShow_successful() throws Exception {
    ProblemShowDTO problemShowDTO = ProblemShowDTO.builder().setProblemId(1000).build();
    when(problemService.getProblemShowDTO(anyInt())).thenReturn(problemShowDTO);
    mockMvc.perform(post(URL_SHOW + "/{problemId}", problemShowDTO.getProblemId())).andExpect(status().isOk());
  }

}
