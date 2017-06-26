package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.TeamUserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDto;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link cn.edu.uestc.acmicpc.service.iface.TeamUserService}
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamUserServiceImpl extends AbstractService implements TeamUserService {

  private final TeamUserDao teamUserDao;

  @Autowired
  public TeamUserServiceImpl(TeamUserDao teamUserDao) {
    this.teamUserDao = teamUserDao;
  }

  @Override
  public Integer createNewTeamUser(TeamUserDto teamUserDto) throws AppException {
    TeamUser teamUser = new TeamUser();
    teamUser.setTeamId(teamUserDto.getTeamId());
    teamUser.setUserId(teamUserDto.getUserId());
    teamUser.setAllow(teamUserDto.getAllow());
    teamUserDao.addOrUpdate(teamUser);
    return teamUser.getTeamUserId();
  }

  @Override
  public TeamUserDto getTeamUserDto(Integer teamUserId) throws AppException {
    return teamUserDao.getDtoByUniqueField(TeamUserDto.class, TeamUserDto.builder(), "teamUserId",
        teamUserId);
  }

  @Override
  public List<TeamUserListDto> getTeamUserList(TeamUserCondition teamUserCondition)
      throws AppException {
    return teamUserDao.findAll(TeamUserListDto.class, TeamUserListDto.builder(),
        teamUserCondition.getCondition());
  }

  @Override
  public void changeAllowState(Integer userId, Integer teamId, Boolean value) throws AppException {
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.userId = userId;
    teamUserCondition.teamId = teamId;
    teamUserDao.updateEntitiesByCondition("allow", value, teamUserCondition.getCondition());
  }

  @Override
  public List<TeamUserListDto> getTeamUserList(Integer teamId) throws AppException {
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.teamId = teamId;
    return teamUserDao.findAll(TeamUserListDto.class, TeamUserListDto.builder(),
        teamUserCondition.getCondition());
  }

  @Override
  public void removeTeamUser(Integer teamUserId) throws AppException {
    teamUserDao.deleteEntitiesByField("teamUserId", teamUserId.toString());
  }

  @Override
  public List<TeamUserReportDto> exportTeamUserReport(TeamUserCondition teamUserCondition)
      throws AppException {
    return teamUserDao.findAll(TeamUserReportDto.class, TeamUserReportDto.builder(),
        teamUserCondition.getCondition());
  }
}
