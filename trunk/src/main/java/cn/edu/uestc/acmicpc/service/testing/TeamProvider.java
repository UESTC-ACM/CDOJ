package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Team provider
 */
@Component
public class TeamProvider {

  @Autowired private TeamService teamService;

  public TeamDto createTeam(Integer leaderId) throws AppException {
    Integer teamId = teamService.createNewTeam("Team" + TestUtil.getUniqueId(), leaderId);
    return teamService.getTeamDtoByTeamId(teamId, TeamFields.BASIC_FIELDS);
  }

}
