package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Problem provider for integration testing.
 */
@Component
public class ProblemProvider {

  @Autowired
  private ProblemService problemService;

  public ProblemDto createProblem() throws AppException {
    Integer problemId = problemService.createNewProblem();
    return problemService.getProblemDtoByProblemId(problemId);
  }
}
