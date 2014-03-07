package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.ITeamDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation for {@link cn.edu.uestc.acmicpc.service.iface.TeamService}
 */
@Service
public class TeamServiceImpl extends AbstractService implements TeamService {

  private final ITeamDAO teamDAO;

  @Autowired
  public TeamServiceImpl(ITeamDAO teamDAO) {
    this.teamDAO = teamDAO;
  }

  @Override
  public ITeamDAO getDAO() {
    return null;
  }

  @Override
  public Boolean checkTeamExists(String teamName) throws AppException {
    AppExceptionUtil.assertNotNull(teamName);
    TeamDTO teamDTO = teamDAO.getDTOByUniqueField(TeamDTO.class, TeamDTO.builder(), "teamName", teamName);
    if (teamDTO != null) {
      AppExceptionUtil.assertTrue(teamDTO.getTeamName().compareTo(teamName) == 0);
      return true;
    }
    return false;
  }
}
