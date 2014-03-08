package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITeamDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamListDTO;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    return teamDAO;
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

  @Override
  public Integer createNewTeam(String teamName, Integer leaderId) throws AppException {
    Team team = new Team();
    team.setTeamId(null);
    team.setTeamName(teamName);
    team.setLeaderId(leaderId);
    teamDAO.add(team);
    return team.getTeamId();
  }

  @Override
  public TeamDTO getTeamDTOByTeamId(Integer teamId) throws AppException {
    return teamDAO.getDTOByUniqueField(TeamDTO.class, TeamDTO.builder(), "teamId", teamId);
  }

  @Override
  public Long count(TeamCondition teamCondition) throws AppException {
    if (teamCondition.userId == null) {
      return teamDAO.count(teamCondition.getCondition());
    } else {
      return teamDAO.count(getHQLString(teamCondition));
    }
  }

  public String getHQLString(TeamCondition teamCondition) throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder.append("from Team team, TeamUser teamUser ")
        .append("where team.teamId = teamUser.teamId and teamUser.userId = ").append(teamCondition.userId);
    if (teamCondition.teamName != null) {
      hqlBuilder.append(" and team.teamName like '%").append(teamCondition.teamName).append("%'");
    }
    hqlBuilder.append(teamCondition.getCondition().getOrdersString());
    return hqlBuilder.toString();
  }

  @Override
  public List<TeamListDTO> getTeamList(TeamCondition teamCondition, PageInfo pageInfo) throws AppException {
    if (teamCondition.userId == null) {
      Condition condition = teamCondition.getCondition();
      condition.setPageInfo(pageInfo);
      return teamDAO.findAll(TeamListDTO.class, TeamListDTO.builder(), condition);
    } else {
      return teamDAO.findAll(TeamListDTO.class, TeamListDTO.builder(), getHQLString(teamCondition), pageInfo);
    }
  }
}
