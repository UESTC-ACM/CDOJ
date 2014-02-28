package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.config.WebMVCConfig;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDTO;
import cn.edu.uestc.acmicpc.web.oj.controller.problem.ProblemController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.UUID;

/**
 * Mock test for {@link ProblemController}.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class, WebMVCConfig.class})
public class ProblemControllerTest extends ControllerTest {

  private final String URL_SHOW = "/problem/show";
  private final String URL_DATA = "/problem/data";
  private final String URL_LIST = "/problem/list";

  @Autowired private ProblemController problemController;

  @Override
  @BeforeMethod
  public void init() {
    super.init();
    mockMvc = initControllers(problemController);
    session = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
  }

  @Test
  // TODO(Ruinshe) Now I get problem data use /problem/data/{problemId}, see ProblemController.java for more details.
  public void testShow_successful() throws Exception {
    ProblemDTO problemDTO = ProblemDTO.builder().setProblemId(1000).build();
    when(problemService.checkProblemExists(1000)).thenReturn(true);
    when(problemService.getProblemDTOByProblemId(1000)).thenReturn(problemDTO);
    /*when(statusService.count(Mockito.<StatusCondition>any())).thenReturn(100L);
    Map<OnlineJudgeResultType, Long> problemStatistic = new TreeMap<>();
    for (OnlineJudgeResultType type : OnlineJudgeResultType.values()) {
      if (type == OnlineJudgeResultType.OJ_WAIT) {
        continue;
      }
      problemStatistic.put(type, 100L);
    }*/
    mockMvc.perform(post(URL_SHOW + "/{problemId}", problemDTO.getProblemId()))
        .andExpect(status().isOk())
        .andExpect(view().name("problem/problemShow"))
        .andExpect(model().attribute("problemId", problemDTO.getProblemId()))
        //.andExpect(model().attribute("problemStatistic", problemStatistic))
        //.andExpect(model().attribute("targetProblem", problemShowDTO))
        //.andExpect(model().attribute("brToken", "\n"))
        .andExpect(model().attribute("languageList", languageList));
  }

  @Test
  public void testShow_problemNotFound() throws Exception {
    when(problemService.checkProblemExists(anyInt())).thenReturn(false);
    mockMvc.perform(post(URL_SHOW + "/{problemId}", 1000))
        .andExpect(status().isOk())
        .andExpect(view().name("error/404"));
  }

  @Test
  public void testList() throws Exception {
    mockMvc.perform(get(URL_LIST))
        .andExpect(status().isOk())
        .andExpect(view().name("problem/problemList"));
  }
}
