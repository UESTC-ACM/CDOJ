package cn.edu.uestc.acmicpc.oj.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Service interface for {@link Problem}.
 */
public interface ProblemService extends OnlineJudgeService<Problem, Integer> {

  /**
   * Get all visible problems' id without any statements.
   *
   * @return all problem id list
   * @throws AppException
   */
  List<Integer> getAllVisibleProblemIds() throws AppException;
}
