package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test cases for {@link ProblemService}.
 */
@SuppressWarnings("deprecation")
public class ProblemServiceITTest extends PersistenceITTest {

  @Autowired
  private ProblemService problemService;

  @Test
  public void testCreateNewProblem() throws AppException {
    Assert.assertNotNull(problemService.createNewProblem());
  }

  @Test
  public void testGetProblemDtoByProblemId() throws AppException {
    Integer problemId = problemService.createNewProblem();
    ProblemDto problemDto = problemService.getProblemDtoByProblemId(problemId);
    Assert.assertEquals(problemId, problemDto.getProblemId());
  }

  @Test
  public void testCount() throws AppException {
    Integer[] problemIds = problemProvider.createProblems(3);
    ProblemCondition problemCondition = new ProblemCondition();
    problemCondition.startId = problemIds[0];
    problemCondition.endId = problemIds[2];
    Assert.assertEquals(Long.valueOf(3), problemService.count(problemCondition));
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateProblem_problemNotFound() throws AppException {
    ProblemDto problemDto = ProblemDto.builder()
        .setProblemId(1234)
        .build();
    problemService.updateProblem(problemDto);
    Assert.fail();
  }
}
