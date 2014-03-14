package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITeamUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
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

  private final ITeamUserDAO teamUserDAO;

  @Autowired
  public TeamUserServiceImpl(ITeamUserDAO teamUserDAO) {
    this.teamUserDAO = teamUserDAO;
  }

  @Override
  public ITeamUserDAO getDAO() {
    return teamUserDAO;
  }

  @Override
  public Integer createNewTeamUser(TeamUserDTO teamUserDTO) throws AppException {
    TeamUser teamUser = new TeamUser();
    teamUser.setTeamId(teamUserDTO.getTeamId());
    teamUser.setUserId(teamUserDTO.getUserId());
    teamUser.setAllow(teamUserDTO.getAllow());
    teamUserDAO.add(teamUser);
    return teamUser.getTeamUserId();
  }

  @Override
  public TeamUserDTO getTeamUserDTO(Integer teamUserId) throws AppException {
    return teamUserDAO.getDTOByUniqueField(TeamUserDTO.class, TeamUserDTO.builder(), "teamUserId", teamUserId);
  }

  @Override
  public List<TeamUserListDTO> getTeamUserList(TeamUserCondition teamUserCondition) throws AppException {
    return teamUserDAO.findAll(TeamUserListDTO.class, TeamUserListDTO.builder(), teamUserCondition.getCondition());
  }

  @Override
  public void changeAllowState(Integer userId, Integer teamId, Boolean value) throws AppException {
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.userId = userId;
    teamUserCondition.teamId = teamId;
    teamUserDAO.updateEntitiesByCondition("allow", value, teamUserCondition.getCondition());
  }

  @Override
  public List<TeamUserListDTO> getTeamUserList(Integer teamId) throws AppException {
    TeamUserCondition teamUserCondition = new TeamUserCondition();
    teamUserCondition.teamId = teamId;
    return teamUserDAO.findAll(TeamUserListDTO.class, TeamUserListDTO.builder(),
        teamUserCondition.getCondition());
  }
}
