package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

public interface ContestProblemService extends DatabaseService<ContestProblem, Integer> {

  /**
   * Gets problems of contest.
   *
   * @param contestId
   *          contest id.
   * @return all problems as a {@link List} of the contest.
   * @throws AppException
   */
  public List<ContestProblemDTO> getContestProblemDTOListByContestId(Integer contestId)
      throws AppException;

  /**
   * Check whether one problem exist in specified contest
   *
   * @param contestProblemId
   * @param contestId
   * @return
   * @throws AppException
   */
  public Boolean checkContestProblemInContest(Integer contestProblemId, Integer contestId)
      throws AppException;
}