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
  public void testGetProblemDtoByProblemId() throws AppException {
    ProblemDto problemDto = problemService.getProblemDtoByProblemId(1);
    Assert.assertEquals(Integer.valueOf(1), problemDto.getProblemId());
    Assert.assertEquals("a+b problem", problemDto.getTitle());
  }

  @Test
  public void testCount() throws AppException {
    ProblemCondition problemCondition = new ProblemCondition();
    problemCondition.startId = 1;
    problemCondition.endId = 3;
    Assert.assertEquals(Long.valueOf(3), problemService.count(problemCondition));
  }

  @Test(expectedExceptions = AppException.class)
  public void testUpdateProblem_problemNotFound() throws AppException {
    ProblemDto problemDto = ProblemDto.builder()
        .setProblemId(6)
        .build();
    problemService.updateProblem(problemDto);
    Assert.fail();
  }
}
