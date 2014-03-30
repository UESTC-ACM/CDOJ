package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDTO;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

/**
 * Team user service interface.
 */
public interface TeamUserService extends DatabaseService<TeamUser, Integer> {

  /**
   * Create a new record by {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO} entity.
   *
   * @param teamUserDTO {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO} entity.
   * @return new record's id.
   * @throws AppException
   */
  public Integer createNewTeamUser(TeamUserDTO teamUserDTO) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO} entity by id.
   *
   * @param teamUserId team user's id
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO} entity.
   * @throws AppException
   */
  public TeamUserDTO getTeamUserDTO(Integer teamUserId) throws AppException;

  /**
   * Fetch team users fit in {@link TeamUserCondition}.
   *
   * @param teamUserCondition search condition.
   * @return list of {@link TeamUserListDTO} entities.
   * @throws AppException
   */
  public List<TeamUserListDTO> getTeamUserList(TeamUserCondition teamUserCondition) throws AppException;

  /**
   * Update specified user's state in team.
   *
   * @param userId user's id.
   * @param teamId team's id.
   * @param value state
   * @throws AppException
   */
  public void changeAllowState(Integer userId, Integer teamId, Boolean value) throws AppException;

  /**
   * Fetch all team users in specified team.
   *
   * @param teamId team's id.
   * @return list of {@link TeamUserListDTO} entities.
   * @throws AppException
   */
  public List<TeamUserListDTO> getTeamUserList(Integer teamId) throws AppException;

  /**
   * Remove user from team.
   *
   * @param teamUserId team user's id
   * @throws AppException
   */
  public void removeTeamUser(Integer teamUserId) throws AppException;

  /**
   * Fetch team user report fit in {@link TeamUserCondition}
   *
   * @param teamUserCondition search condition.
   * @return list of {@link TeamUserReportDTO} entities.
   * @throws AppException
   */
  public List<TeamUserReportDTO> exportTeamUserReport(TeamUserCondition teamUserCondition) throws AppException;
}

