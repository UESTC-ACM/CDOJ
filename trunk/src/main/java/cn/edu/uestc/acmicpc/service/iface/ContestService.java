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
   * @param contestId
   *          contest id.
   * @return a DTO for contest.
   * @throws AppException
   */
  public ContestStatusShowDTO getcontestStatusShowDTOByContestId(Integer contestId)
      throws AppException;

  /**
   * @param contestCondition
   *          DB query conditoin.
   * @return number of records for DB query.
   * @throws AppException
   */
  public Long count(ContestCondition contestCondition) throws AppException;

  /**
   * @param contestCondition
   *          DB query condition.
   * @param pageInfo
   *          page information.
   * @return contest's list that meets the condition and inside the range of
   *         page
   * @throws AppException
   */
  public List<ContestListDTO> getContestListDTOList(ContestCondition contestCondition,
      PageInfo pageInfo) throws AppException;

}
