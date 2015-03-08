package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDetailDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemSummaryDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

/**
 * ContestProblem service interface.
 */
public interface ContestProblemService {

  /**
   * Gets
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDto}
   * by contest problem id.
   *
   * @param contestProblemId contest problem id.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDto}
   * entity.
   * @throws AppException
   */
  public ContestProblemDto getContestProblemDto(Integer contestProblemId) throws AppException;

  /**
   * Gets
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDetailDto}
   * of contest.
   *
   * @param contestId contest's id.
   * @return all
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.contestproblem.ContestProblemDetailDto}
   * entities as a {@link List} of the contest.
   * @throws AppException
   */
  public List<ContestProblemDetailDto> getContestProblemDetailDtoListByContestId(Integer contestId)
      throws AppException;

  /**
   * Gets {@link ContestProblemSummaryDto} of contest.
   *
   * @param contestId contest's id.
   * @return all {@link ContestProblemSummaryDto} entities as a {@link List} of
   * the contest.
   * @throws AppException
   */
  public List<ContestProblemSummaryDto> getContestProblemSummaryDtoListByContestId(Integer contestId)
      throws AppException;

  /**
   * Remove contest problems by contest id.
   *
   * @param contestId contest's id.
   * @throws AppException
   */
  public void removeContestProblemByContestId(Integer contestId) throws AppException;

  /**
   * Create a new record by {@link ContestProblemDto} entity.
   *
   * @param contestProblemDto {@link ContestProblemDto} entity.
   * @return new record's id.
   * @throws AppException
   */
  public Integer createNewContestProblem(ContestProblemDto contestProblemDto) throws AppException;

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