package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ContestProblem service interface.
 */
public interface ContestProblemService extends DatabaseService<ContestProblem, Integer> {

  /**
   * Gets {@link ContestProblemDTO} of contest.
   *
   * @param contestId
   *          contest's id.
   * @return all {@link ContestProblemDTO} entities as a {@link List} of the contest.
   * @throws AppException
   */
  public List<ContestProblemDTO> getContestProblemDTOListByContestId(Integer contestId)
      throws AppException;

  /**
   * Gets {@link ContestProblemSummaryDTO} of contest.
   *
   * @param contestId contest's id.
   * @return all {@link ContestProblemSummaryDTO} entities as a {@link List} of the contest.
   * @throws AppException
   */
  public List<ContestProblemSummaryDTO> getContestProblemSummaryDTOListByContestId(Integer contestId)
      throws AppException;

  /**
   * Check whether one problem exist in specified contest.
   *
   * @param problemId problem's id.
   * @param contestId contest's id.
   * @return <code>True</code> if specified contest content specified problem.
   * @throws AppException
   */
  public Boolean checkContestProblemInContest(Integer problemId, Integer contestId)
      throws AppException;
}