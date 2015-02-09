package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Contest service interface.
 */
public interface ContestService {

  /**
   * Get all visible contests' id.
   *
   * @return all contest id list.
   * @throws AppException
   */
  public List<Integer> getAllVisibleContestIds() throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDto} entity by
   * contest id.
   *
   * @param contestId
   *          contest id.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDto} entity.
   * @throws AppException
   */
  public ContestDto getContestDtoByContestId(Integer contestId)
      throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDto} entity
   * by contest id.
   *
   * @param contestId
   *          contest id.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDto}
   *         entity.
   * @throws AppException
   */
  public ContestShowDto getContestShowDtoByContestId(Integer contestId)
      throws AppException;

  /**
   * Check if specified contest exists.
   *
   * @param contestId
   *          contest's id.
   * @return true if this contest exists.
   * @throws AppException
   */
  public Boolean checkContestExists(Integer contestId) throws AppException;

  /**
   * Updates contest record by
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDto} entity.
   *
   * @param contestDto
   *          {@link cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDto}
   *          entity.
   * @throws AppException
   */
  public void updateContest(ContestDto contestDto) throws AppException;

  /**
   * Counts the number of contests fit in condition.
   *
   * @param condition
   *          {@link ContestCondition} entity.
   * @return total number of contests fit in the condition.
   * @throws AppException
   */
  public Long count(ContestCondition condition) throws AppException;

  /**
   * Get the contests fit in condition and page range.
   *
   * @param condition
   *          {@link ContestCondition} entity.
   * @param pageInfo
   *          {@link PageInfo} entity.
   * @return List of {@link ContestListDto} entities.
   * @throws AppException
   */
  public List<ContestListDto> getContestListDtoList(
      ContestCondition condition,
      PageInfo pageInfo) throws AppException;

  /**
   * Modify one filed of multiply entities as value.
   *
   * @param field
   *          filed need to modified.
   * @param ids
   *          entities' ID split by <code>,</code>.
   * @param value
   *          new value.
   * @throws AppException
   */
  public void operator(String field, String ids, String value)
      throws AppException;

  /**
   * Creates a new contest record.
   *
   * @return the new contest's id.
   * @throws AppException
   */
  public Integer createNewContest() throws AppException;
}
