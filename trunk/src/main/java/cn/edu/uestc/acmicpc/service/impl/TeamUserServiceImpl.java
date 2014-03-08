package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.ITeamUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDTO;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    teamUserDAO.add(teamUser);
    return teamUser.getTeamUserId();
  }

  @Override
  public TeamUserDTO getTeamUserDTO(Integer teamUserId) throws AppException {
    return teamUserDAO.getDTOByUniqueField(TeamUserDTO.class, TeamUserDTO.builder(), "teamUserId", teamUserId);
  }
}
