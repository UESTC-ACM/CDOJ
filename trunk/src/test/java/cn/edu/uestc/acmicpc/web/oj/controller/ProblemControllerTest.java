package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.testing.ControllerTest;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import cn.edu.uestc.acmicpc.web.oj.controller.problem.ProblemController;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.testng.annotations.BeforeMethod;

import java.util.UUID;

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

  @Test
  public void testVisitProblemByNormalUser() throws Exception {
    ProblemDto problemDto = ProblemDto.builder().setProblemId(1234).setTitle("test").setDescription("description test").
        setInput("1 2").setOutput("3"). setSampleInput("SampleInput test").setSampleOutput("SampleOutput test").
        setHint("Hint Test").setSource("Source Test").setTimeLimit(1000).setMemoryLimit(65536).setSolved(0).setTried(0)
        .setIsSpj(Boolean.FALSE).setIsVisible(Boolean.TRUE).setOutputLimit(8000).setJavaTimeLimit(3000).
        setJavaMemoryLimit(65536).setDataCount(1).setDifficulty(1).setType(ProblemType.NORMAL).build();

    UserDto userDto = UserDto.builder().setType(ProblemType.NORMAL.ordinal()).build();
    when(problemService.getProblemDtoByProblemId(1234))
        .thenReturn(problemDto);
    mockMvc.perform(post("/problem/data/{problemId}", 1234))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.problem.dataCount", is(problemDto.getDataCount())))
        .andExpect(jsonPath("$.problem.description", is(problemDto.getDescription())))
        .andExpect(jsonPath("$.problem.difficulty", is(problemDto.getDifficulty())))
        .andExpect(jsonPath("$.problem.hint", is(problemDto.getHint())))
        .andExpect(jsonPath("$.problem.input", is(problemDto.getInput())))
        .andExpect(jsonPath("$.problem.isSpj", is(problemDto.getIsSpj())))
        .andExpect(jsonPath("$.problem.isVisible", is(problemDto.getIsVisible())))
        .andExpect(jsonPath("$.problem.javaMemoryLimit", is(problemDto.getJavaMemoryLimit())))
        .andExpect(jsonPath("$.problem.javaTimeLimit", is(problemDto.getJavaTimeLimit())))
        .andExpect(jsonPath("$.problem.memoryLimit", is(problemDto.getMemoryLimit())))
        .andExpect(jsonPath("$.problem.output", is(problemDto.getOutput())))
        .andExpect(jsonPath("$.problem.outputLimit", is(problemDto.getOutputLimit())))
        .andExpect(jsonPath("$.problem.problemId", is(problemDto.getProblemId())))
        .andExpect(jsonPath("$.problem.sampleInput", is(problemDto.getSampleInput())))
        .andExpect(jsonPath("$.problem.sampleOutput", is(problemDto.getSampleOutput())))
        .andExpect(jsonPath("$.problem.solved", is(problemDto.getSolved())))
        .andExpect(jsonPath("$.problem.source", is(problemDto.getSource())))
        .andExpect(jsonPath("$.problem.timeLimit", is(problemDto.getTimeLimit())))
        .andExpect(jsonPath("$.problem.title", is(problemDto.getTitle())))
        .andExpect(jsonPath("$.problem.tried", is(problemDto.getTried())));
  }
}
