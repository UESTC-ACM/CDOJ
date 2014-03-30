package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamReportDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Contest team service interface
 */
public interface ContestTeamService
    extends DatabaseService<ContestTeam, Integer> {

  /**
   * Check whether some user can be register in one contest.
   * If any team contains this user is accepted in the contest, this user can
   * not register in this contest again.
   *
   * @param userId    user's id.
   * @param contestId contest's id.
   * @return true if user can register in specified contest.
   * @throws AppException
   */
  public Boolean checkUserCanRegisterInContest(Integer userId,
                                               Integer contestId)
      throws AppException;

  /**
   * Create a new contest team record.
   *
   * @param contestId contest's id.
   * @param teamId    team's id.
   * @return record id in database.
   * @throws AppException
   */
  public Integer createNewContestTeam(Integer contestId,
                                      Integer teamId) throws AppException;

  /**
   * Count record number fit in {@link ContestTeamCondition}.
   *
   * @param contestTeamCondition search condition.
   * @return total record fit in the condition.
   * @throws AppException
   */
  public Long count(ContestTeamCondition contestTeamCondition)
      throws AppException;

  /**
   * Fetch all record fit in {@link ContestTeamCondition}
   *
   * @param contestTeamCondition search condition.
   * @param pageInfo             page range.
   * @return list of {@ContestTeamListDTO} entities.
   * @throws AppException
   */
  public List<ContestTeamListDTO>
  getContestTeamList(ContestTeamCondition contestTeamCondition,
                     PageInfo pageInfo) throws AppException;

  /**
   * Get {@link ContestTeamDTO} entity by record id.
   *
   * @param contestTeamId record id.
   * @return {@link ContestTeamDTO} entity.
   * @throws AppException
   */
  public ContestTeamDTO getContestTeamDTO(Integer contestTeamId)
      throws AppException;

  /**
   * Update record bt {@link ContestTeamDTO} entity.
   *
   * @param contestTeamDTO {@link ContestTeamDTO} entity.
   * @throws AppException
   */
  public void updateContestTeam(ContestTeamDTO contestTeamDTO)
      throws AppException;

  /**
   * Export all contest teams registered in specified contest.
   *
   * @param contestId specified contest's id.
   * @return List of {@link ContestTeamReportDTO} entities.
   * @throws AppException
   */
  public List<ContestTeamReportDTO> exportContestTeamReport(Integer contestId)
      throws AppException;

  /**
   * Return the team's id that user attend in the specified contest.
   *
   * @param userId user's id.
   * @param contestId contest's id.
   * @return team's id.
   * @throws AppException
   */
  public Integer getTeamIdByUserIdAndContestId(Integer userId,
                                              Integer contestId) throws AppException;
}
