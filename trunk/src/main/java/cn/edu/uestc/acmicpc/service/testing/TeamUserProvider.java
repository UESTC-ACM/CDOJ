package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Team user provider
 */
@Component
public class TeamUserProvider {

  @Autowired private TeamUserService teamUserService;

  public void createTeamUser(Integer teamId, Integer userId) throws AppException {
    createTeamUser(teamId, userId, true);
  }

  public void createTeamUser(Integer teamId, Integer userId, boolean allow) throws AppException {
    teamUserService.createNewTeamUser(TeamUserDto.builder()
        .setTeamId(teamId)
        .setUserId(userId)
        .setAllow(allow)
        .build());
  }
}
