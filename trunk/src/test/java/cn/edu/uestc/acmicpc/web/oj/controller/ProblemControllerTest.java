package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.config.WebMVCConfig;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemShowDTO;
import cn.edu.uestc.acmicpc.util.settings.Global.OnlineJudgeResultType;
import cn.edu.uestc.acmicpc.web.oj.controller.problem.ProblemController;

/**
 * Mock test for {@link ProblemController}.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class, WebMVCConfig.class})
public class ProblemControllerTest extends ControllerTest {

  private final String URL_SHOW = "/problem/show";
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
  public void testShow_successful() throws Exception {
    ProblemShowDTO problemShowDTO = ProblemShowDTO.builder().setProblemId(1000).build();
    when(problemService.getProblemShowDTO(anyInt())).thenReturn(problemShowDTO);
    when(statusService.count(Mockito.<StatusCondition>any())).thenReturn(100L);
    Map<OnlineJudgeResultType, Long> problemStatistic = new TreeMap<>();
    for (OnlineJudgeResultType type : OnlineJudgeResultType.values()) {
      if (type == OnlineJudgeResultType.OJ_WAIT) {
        continue;
      }
      problemStatistic.put(type, 100L);
    }
    mockMvc.perform(post(URL_SHOW + "/{problemId}", problemShowDTO.getProblemId()))
        .andExpect(status().isOk())
        .andExpect(view().name("problem/problemShow"))
        .andExpect(model().attribute("problemStatistic", problemStatistic))
        .andExpect(model().attribute("targetProblem", problemShowDTO))
        .andExpect(model().attribute("brToken", "\n"))
        .andExpect(model().attribute("languageList", languageList));
  }

  @Test
  public void testShow_problemNotFound() throws Exception {
    when(problemService.getProblemShowDTO(anyInt())).thenReturn(null);
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
