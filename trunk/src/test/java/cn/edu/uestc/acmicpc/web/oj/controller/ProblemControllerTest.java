package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemShowDTO;
import cn.edu.uestc.acmicpc.service.iface.DepartmentService;
import cn.edu.uestc.acmicpc.service.iface.FileService;
import cn.edu.uestc.acmicpc.service.iface.GlobalService;
import cn.edu.uestc.acmicpc.service.iface.LanguageService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.web.oj.controller.problem.ProblemController;

/**
 * Mock test for {@link ProblemController}.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class})
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

  @Autowired
  @Qualifier("mockPictureService")
  private PictureService pictureService;

  @Autowired
  @Qualifier("mockFileService")
  private FileService fileService;

  @Override
  @BeforeMethod
  public void init() {
    super.init();
    ProblemController problemController = new ProblemController(departmentService, globalService,
        problemService, statusService, languageService, pictureService, fileService);
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
