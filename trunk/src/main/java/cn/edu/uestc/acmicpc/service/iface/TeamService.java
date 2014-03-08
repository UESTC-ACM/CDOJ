package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Team service interface.
 */
public interface TeamService extends DatabaseService<Team, Integer> {

  /**
   * Check whether a team exists.
   *
   * @param teamName team name
   * @return true if exists
   */
  public Boolean checkTeamExists(String teamName) throws AppException;

  /**
   * Create a new team.
   *
   * @param teamName team's name
   * @param leaderId team leader's user id
   * @return team id
   * @throws AppException
   */
  public Integer createNewTeam(String teamName, Integer leaderId) throws AppException;

  /**
   * Get {@link TeamDTO} entity by team's id
   *
   * @param teamId team's id
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO} entity
   * @throws AppException
   */
  public TeamDTO getTeamDTOByTeamId(Integer teamId) throws AppException;
}
