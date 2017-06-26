package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.List;

/**
 * Team user service interface.
 */
@SuppressWarnings("deprecation")
public interface TeamUserService {

  /**
   * Create a new record by
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto} entity.
   *
   * @param teamUserDto {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto}
   *                    entity.
   * @return new record's id.
   * @throws AppException
   */
  public Integer createNewTeamUser(TeamUserDto teamUserDto) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto} entity by
   * id.
   *
   * @param teamUserId team user's id
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto}
   * entity.
   * @throws AppException
   */
  public TeamUserDto getTeamUserDto(Integer teamUserId) throws AppException;

  /**
   * Fetch team users fit in {@link TeamUserCondition}.
   *
   * @param teamUserCondition search condition.
   * @return list of {@link TeamUserListDto} entities.
   * @throws AppException
   */
  @Deprecated
  public List<TeamUserListDto> getTeamUserList(TeamUserCondition teamUserCondition)
      throws AppException;

  /**
   * Update specified user's state in team.
   *
   * @param userId user's id.
   * @param teamId team's id.
   * @param value  state
   * @throws AppException
   */
  public void changeAllowState(Integer userId, Integer teamId, Boolean value) throws AppException;

  /**
   * Fetch all team users in specified team.
   *
   * @param teamId team's id.
   * @return list of {@link TeamUserListDto} entities.
   * @throws AppException
   */
  public List<TeamUserListDto> getTeamUserList(Integer teamId) throws AppException;

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
   * @return list of {@link TeamUserReportDto} entities.
   * @throws AppException
   */
  @Deprecated
  public List<TeamUserReportDto> exportTeamUserReport(TeamUserCondition teamUserCondition)
      throws AppException;
}
