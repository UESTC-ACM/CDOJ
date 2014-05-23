package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.TeamUserDao;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDTO;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for {@link cn.edu.uestc.acmicpc.service.iface.TeamUserService}
 */
@Service
public class TeamUserServiceImpl extends AbstractService implements TeamUserService {

  private final TeamUserDao teamUserDao;

  @Autowired
  public TeamUserServiceImpl(TeamUserDao teamUserDao) {
    this.teamUserDao = teamUserDao;
  }

  @Override
  public TeamUserDao getDao() {
    return teamUserDao;
  }

  @Override
  public Integer createNewTeamUser(TeamUserDTO teamUserDTO) throws AppException {
    TeamUser teamUser = new TeamUser();
    teamUser.setTeamId(teamUserDTO.getTeamId());
    teamUser.setUserId(teamUserDTO.getUserId());
    teamUser.setAllow(teamUserDTO.getAllow());
    teamUserDao.add(teamUser);
    return teamUser.getTeamUserId();
  }

  @Override
  public TeamUserDTO getTeamUserDTO(Integer teamUserId) throws AppException {
    return teamUserDao.getDTOByUniqueField(TeamUserDTO.class, TeamUserDTO.builder(), "teamUserId", teamUserId);
  }

  @Override
  public List<TeamUserListDTO> getTeamUserList(TeamUserCondition teamUserCondition) throws AppException {
    return teamUserDao.findAll(TeamUserListDTO.class, TeamUserListDTO.builder(), teamUserCondition.getCondition());
  }

  @Override
  public void changeAllowState(Integer userId, Integer teamId, Boolean value) throws AppException {
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.userId = userId;
    teamUserCondition.teamId = teamId;
    teamUserDao.updateEntitiesByCondition("allow", value, teamUserCondition.getCondition());
  }

  @Override
  public List<TeamUserListDTO> getTeamUserList(Integer teamId) throws AppException {
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.teamId = teamId;
    return teamUserDao.findAll(TeamUserListDTO.class, TeamUserListDTO.builder(),
        teamUserCondition.getCondition());
  }

  @Override
  public void removeTeamUser(Integer teamUserId) throws AppException {
    teamUserDao.deleteEntitiesByField("teamUserId", teamUserId.toString());
  }

  @Override
  public List<TeamUserReportDTO> exportTeamUserReport(TeamUserCondition teamUserCondition) throws AppException {
    return teamUserDao.findAll(TeamUserReportDTO.class, TeamUserReportDTO.builder(), teamUserCondition.getCondition());
  }
}
