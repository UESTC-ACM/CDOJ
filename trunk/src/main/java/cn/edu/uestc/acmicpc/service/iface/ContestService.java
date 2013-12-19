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
   * Get the number of contests that meet the condition.
   *
   * @param contestCondition
   *          {@link ContestCondition} entity.
   * @return number of records for DB query.
   * @throws AppException
   */
  public Long count(ContestCondition contestCondition) throws AppException;

  /**
   *
   * @param contestCondition
   *          DB query condition.
   * @param pageInfo
   *          page information.
   * @return contest's list that meets the condition and inside the range of
   *         page
   * @throws AppException
   */
  public List<ContestListDTO> getContestListDTOList(
      ContestCondition contestCondition,
      PageInfo pageInfo) throws AppException;

  /**
   *
   * @param field
   * @param ids
   * @param value
   * @throws AppException
   */
  public void operator(String field, String ids, String value)
      throws AppException;

  /**
   *
   * @param contestId
   * @return
   * @throws AppException
   */
  public ContestEditorShowDTO getContestEditorShowDTO(Integer contestId) throws AppException;
}
