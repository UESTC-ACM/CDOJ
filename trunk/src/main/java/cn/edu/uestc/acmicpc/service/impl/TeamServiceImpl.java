package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ITeamDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamTypeAHeadDTO;
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
    return teamDAO.count(getHQLString(teamCondition));
  }

  public String getHQLString(TeamCondition teamCondition) throws AppException {
    StringBuilder hqlBuilder = new StringBuilder();
    hqlBuilder.append("from Team team");
    if (teamCondition.userId != null) {
      hqlBuilder.append(", TeamUser teamUser");
    }
    hqlBuilder.append(" where");
    Boolean needAnd = false;
    if (teamCondition.userId != null) {
      needAnd = true;
      hqlBuilder.append(" team.teamId = teamUser.teamId and teamUser.userId = ").append(teamCondition.userId);
    }
    if (teamCondition.teamId != null) {
      if (needAnd) {
        hqlBuilder.append(" and");
      }
      needAnd = true;
      hqlBuilder.append(" team.teamId = ").append(teamCondition.teamId);
    }
    if (teamCondition.allow != null) {
      if (needAnd) {
        hqlBuilder.append(" and");
      }
      needAnd = true;
      hqlBuilder.append(" teamUser.allow = ").append(teamCondition.allow);
    }
    if (teamCondition.teamName != null) {
      if (needAnd) {
        hqlBuilder.append(" and");
      }
      needAnd = true;
      hqlBuilder.append(" team.teamName like '%").append(teamCondition.teamName).append("%'");
    }
    if (teamCondition.leaderId != null) {
      if (needAnd) {
        hqlBuilder.append(" and");
      }
      hqlBuilder.append(" team.leaderId = ").append(teamCondition.leaderId);
    }
    hqlBuilder.append(teamCondition.getCondition().getOrdersString());
    return hqlBuilder.toString();
  }

  @Override
  public List<TeamTypeAHeadDTO> getTeamTypeAHeadList(TeamCondition teamCondition,
                                                     PageInfo pageInfo) throws AppException {
    return teamDAO.findAll(TeamTypeAHeadDTO.class, TeamTypeAHeadDTO.builder(),
        getHQLString(teamCondition), pageInfo);
  }

  @Override
  public Integer getTeamIdByTeamName(String teamName) throws AppException {
    TeamDTO teamDTO = teamDAO.getDTOByUniqueField(TeamDTO.class, TeamDTO.builder(), "teamName",
        teamName);
    AppExceptionUtil.assertNotNull(teamDTO, "Team not found.");
    return teamDTO.getTeamId();
  }

  @Override
  public void deleteTeam(TeamDTO teamDTO) throws AppException {
    teamDAO.deleteEntitiesByField("teamId", teamDTO.getTeamId().toString());
  }

  @Override
  public List<TeamListDTO> getTeamList(TeamCondition teamCondition, PageInfo pageInfo)
      throws AppException {
    return teamDAO.findAll(TeamListDTO.class, TeamListDTO.builder(), getHQLString(teamCondition),
        pageInfo);
  }
}
