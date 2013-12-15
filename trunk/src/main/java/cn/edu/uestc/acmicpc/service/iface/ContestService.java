package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestStatusShowDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

public interface ContestService extends DatabaseService<Contest, Integer> {

  /**
   * Get all visible contest's id.
   *
   * @return all contest id list.
   * @throws AppException
   */
  public List<Integer> getAllVisibleContestIds() throws AppException;

  /**
   * Get contestDTO by contest id.
   *
   * @param ContestId
   * @return
   * @throws AppException
   */
  public ContestStatusShowDTO getContestStatusShowDTOByContestId(
      Integer contestId) throws AppException;

  /**
   * Count number of contests that meet the condition.
   *
   * @param contestCondition
   * @return
   * @throws AppException
   */
  public Long count(ContestCondition contestCondition) throws AppException;

  /**
   * Get contest's list that meet the condition and inside the range of page
   *
   * @param contestCondition
   * @param pageInfo
   * @return
   * @throws AppException
   */
  public List<ContestListDTO> getContestListDTOList(
      ContestCondition contestCondition,
      PageInfo pageInfo) throws AppException;

  /**
   * TODO(mzry1992)
   *
   * @param field
   * @param ids
   * @param value
   * @throws AppException
   */
  public void operator(String field, String ids, String value)
      throws AppException;
}
