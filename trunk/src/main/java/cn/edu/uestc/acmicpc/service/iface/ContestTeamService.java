package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamReportDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.List;

/**
 * Contest team service interface
 */
@SuppressWarnings("deprecation")
public interface ContestTeamService {

  /**
   * Check whether some user can be register in one contest. If any team
   * contains this user is accepted in the contest, this user can not register
   * in this contest again.
   *
   * @param userId
   *          user's id.
   * @param contestId
   *          contest's id.
   * @return true if user can register in specified contest.
   * @throws AppException
   */
  public Boolean checkUserCanRegisterInContest(Integer userId,
      Integer contestId)
      throws AppException;

  /**
   * Create a new contest team record.
   *
   * @param contestId
   *          contest's id.
   * @param teamId
   *          team's id.
   * @return record id in database.
   * @throws AppException
   */
  public Integer createNewContestTeam(Integer contestId,
      Integer teamId) throws AppException;

  /**
   * Count record number fit in {@link ContestTeamCondition}.
   *
   * @param contestTeamCondition
   *          search condition.
   * @return total record fit in the condition.
   * @throws AppException
   */
  @Deprecated
  public Long count(ContestTeamCondition contestTeamCondition) throws AppException;

  /**
   * Fetch all record fit in {@link ContestTeamCondition}
   *
   * @param contestTeamCondition
   *          search condition.
   * @param pageInfo
   *          page range.
   * @return list of {@link ContestTeamListDto} entities.
   * @throws AppException
   */
  @Deprecated
  public List<ContestTeamListDto> getContestTeamList(
      ContestTeamCondition contestTeamCondition, PageInfo pageInfo) throws AppException;

  /**
   * Get {@link ContestTeamDto} entity by record id.
   *
   * @param contestTeamId
   *          record id.
   * @return {@link ContestTeamDto} entity.
   * @throws AppException
   */
  public ContestTeamDto getContestTeamDto(Integer contestTeamId)
      throws AppException;

  /**
   * Update record bt {@link ContestTeamDto} entity.
   *
   * @param contestTeamDto
   *          {@link ContestTeamDto} entity.
   * @throws AppException
   */
  public void updateContestTeam(ContestTeamDto contestTeamDto)
      throws AppException;

  /**
   * Export all contest teams registered in specified contest.
   *
   * @param contestId
   *          specified contest's id.
   * @return List of {@link ContestTeamReportDto} entities.
   * @throws AppException
   */
  public List<ContestTeamReportDto> exportContestTeamReport(Integer contestId)
      throws AppException;

  /**
   * Return the team's id that user attend in the specified contest.
   *
   * @param userId
   *          user's id.
   * @param contestId
   *          contest's id.
   * @return team's id.
   * @throws AppException
   */
  public Integer getTeamIdByUserIdAndContestId(Integer userId,
      Integer contestId) throws AppException;
}
