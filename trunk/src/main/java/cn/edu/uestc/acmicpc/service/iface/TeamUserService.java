package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
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

  public List<TeamUserListDTO> getTeamUserList(TeamUserCondition teamUserCondition) throws AppException;

  public void changeAllowState(Integer userId, Integer teamId, Boolean value) throws AppException;

  public List<TeamUserListDTO> getTeamUserList(Integer teamId) throws AppException;

  public void removeTeamUser(Integer teamUserId) throws AppException;
}
