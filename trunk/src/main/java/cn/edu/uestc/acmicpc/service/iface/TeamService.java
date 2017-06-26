package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.TeamCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.List;
import java.util.Set;

/**
 * Team service interface.
 */
public interface TeamService {

  /**
   * Check whether a team exists.
   *
   * @param teamName
   *          team name
   * @return true if exists
   * @throws AppException
   */
  public Boolean checkTeamExists(String teamName) throws AppException;

  /**
   * Create a new team.
   *
   * @param teamName
   *          team's name
   * @param leaderId
   *          team leader's user id
   * @return team id
   * @throws AppException
   */
  public Integer createNewTeam(String teamName, Integer leaderId) throws AppException;

  /**
   * Get {@link TeamDto} entity by team's id
   *
   * @param teamId
   *          team's id
   * @param fields
   *          result fields to be fetched
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.TeamDto} entity
   * @throws AppException
   */
  public TeamDto getTeamDtoByTeamId(Integer teamId, Set<TeamFields> fields) throws AppException;

  /**
   * Get {@link TeamDto} entity by team's name
   *
   * @param teamName
   *          team's name
   * @param fields
   *          result fields to be fetched
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.TeamDto} entity
   * @throws AppException
   */
  public TeamDto getTeamDtoByTeamName(String teamName, Set<TeamFields> fields) throws AppException;

  /**
   * Counts the number of team fit in condition.
   *
   * @param criteria
   *          {@link cn.edu.uestc.acmicpc.db.criteria.TeamCriteria} entity.
   * @return total number of team fit in the condition.
   * @throws AppException
   */
  public Long count(TeamCriteria criteria) throws AppException;

  /**
   * Get the teams fit in the criteria and page range.
   *
   * @param criteria
   *          {@link TeamCriteria} entity.
   * @param pageInfo
   *          {@link PageInfo} entity.
   * @param fields
   *          result fields to be fetched
   * @return List of {@link cn.edu.uestc.acmicpc.db.dto.impl.TeamDto} entities.
   * @throws AppException
   */
  public List<TeamDto> getTeams(TeamCriteria criteria, PageInfo pageInfo, Set<TeamFields> fields)
      throws AppException;

  /**
   * Delete team in database.
   *
   * @param teamDto
   *          {@link TeamDto} entity
   * @throws AppException
   */
  public void deleteTeam(TeamDto teamDto) throws AppException;
}
