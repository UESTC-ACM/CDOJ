package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.List;

/**
 * ContestProblem service interface.
 */
public interface ContestProblemService {

  /**
   * Gets
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto}
   * by contest id and problem id, with basic fields filled.
   *
   * @param contestId the contest id
   * @param problemId the problem id.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto}
   * entity.
   */
  ContestProblemDto getBasicContestProblemDto(Integer contestId, Integer problemId) throws AppException;

  /**
   * Gets
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto}
   * of contest.
   *
   * @param contestId contest's id.
   * @return all
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto}
   * entities as a {@link List} of the contest.
   * @throws AppException
   */
  List<ContestProblemDto> getContestProblemDetailDtoListByContestId(Integer contestId)
      throws AppException;

  /**
   * Gets {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto} of contest.
   *
   * @param contestId contest's id.
   * @return all {@link ContestProblemDto} entities as a {@link List} of
   * the contest.
   * @throws AppException
   */
  List<ContestProblemDto> getContestProblemSummaryDtoListByContestId(Integer contestId)
      throws AppException;

  /**
   * Remove contest problems by contest id.
   *
   * @param contestId contest's id.
   * @throws AppException
   */
  void removeContestProblemByContestId(Integer contestId) throws AppException;

  /**
   * Create a new record by {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto} entity.
   *
   * @param contestProblemDto {@link cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto} entity.
   * @return new record's id.
   * @throws AppException
   */
  Integer createNewContestProblem(ContestProblemDto contestProblemDto) throws AppException;

  /**
   * Check whether one problem exist in specified contest.
   *
   * @param problemId problem's id.
   * @param contestId contest's id.
   * @return <code>True</code> if specified contest content specified problem.
   * @throws AppException
   */
  Boolean checkContestProblemInContest(Integer problemId, Integer contestId)
      throws AppException;
}