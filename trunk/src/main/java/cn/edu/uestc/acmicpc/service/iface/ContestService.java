package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

/**
 * Contest service interface.
 */
public interface ContestService extends DatabaseService<Contest, Integer> {

  /**
   * Get all visible contests' id.
   *
   * @return all contest id list.
   * @throws AppException
   */
  public List<Integer> getAllVisibleContestIds() throws AppException;

  /**
   * Get {@link ContestDTO} entity by contest id.
   *
   * @param contestId
   *          contest id.
   * @return {@link ContestDTO} entity.
   * @throws AppException
   */
  public ContestDTO getContestDTO(Integer contestId)
      throws AppException;

  /**
   * Counts the number of contests fit in condition.
   *
   * @param condition {@link ContestCondition} entity.
   * @return total number of contests fit in the condition.
   * @throws AppException
   */
  public Long count(ContestCondition condition) throws AppException;

  /**
   * Get the contests fit in condition and page range.
   *
   * @param condition {@link ContestCondition} entity.
   * @param pageInfo {@link PageInfo} entity.
   * @return List of {@link ContestListDTO} entities.
   * @throws AppException
   */
  public List<ContestListDTO> getContestListDTOList(
      ContestCondition condition,
      PageInfo pageInfo) throws AppException;

  /**
   * Modify one filed of multiply entities as value.
   *
   * @param field filed need to modified.
   * @param ids entities' ID split by <code>,</code>.
   * @param value new value.
   * @throws AppException
   */
  public void operator(String field, String ids, String value)
      throws AppException;

  /**
   * Get {@link ContestEditorShowDTO} by contest id.
   *
   * @param contestId contest's id.
   * @return {@link ContestEditorShowDTO} entity.
   * @throws AppException
   */
  public ContestEditorShowDTO getContestEditorShowDTO(Integer contestId) throws AppException;
}
