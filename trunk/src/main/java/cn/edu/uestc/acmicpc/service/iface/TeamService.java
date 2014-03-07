package cn.edu.uestc.acmicpc.service.iface;

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
}
