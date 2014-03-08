package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamListDTO;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

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

  /**
   * Counts the number of team fit in condition.
   *
   * @param condition {@link cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition} entity.
   * @return total number of team fit in the condition.
   * @throws AppException
   */
  public Long count(TeamCondition condition) throws AppException;

  /**
   * Get the teams fit in condition and page range.
   *
   * @param condition {@link TeamCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of {@link TeamDTO} entities.
   * @throws AppException
   */
  public List<TeamListDTO> getTeamList(TeamCondition condition,
                                       PageInfo pageInfo) throws AppException;
}
