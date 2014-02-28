package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDetailDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

/**
 * ContestProblem service interface.
 */
public interface ContestProblemService extends DatabaseService<ContestProblem, Integer> {

  /**
   * Gets {@link cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO} by contest problem id.
   *
   * @param contestProblemId contest problem id.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDTO} entity.
   * @throws AppException
   */
  public ContestProblemDTO getContestProblemDTO(Integer contestProblemId) throws AppException;

  /**
   * Gets {@link cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDetailDTO} of contest.
   *
   * @param contestId contest's id.
   * @return all {@link cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemDetailDTO} entities as a {@link List} of the contest.
   * @throws AppException
   */
  public List<ContestProblemDetailDTO> getContestProblemDetailDTOListByContestId(Integer contestId)
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
   * Remove contest problems by contest id.
   *
   * @param contestId contest's id.
   * @throws AppException
   */
  public void removeContestProblemByContestId(Integer contestId) throws AppException;

  /**
   * Create a new record by {@link ContestProblemDTO} entity.
   *
   * @param contestProblemDTO {@link ContestProblemDTO} entity.
   * @return new record's id.
   * @throws AppException
   */
  public Integer createNewContestProblem(ContestProblemDTO contestProblemDTO) throws AppException;

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