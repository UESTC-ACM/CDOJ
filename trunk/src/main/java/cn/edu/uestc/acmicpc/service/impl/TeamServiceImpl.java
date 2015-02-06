package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.TeamDao;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamTypeAHeadDto;
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

  private final TeamDao teamDao;

  @Autowired
  public TeamServiceImpl(TeamDao teamDao) {
    this.teamDao = teamDao;
  }

  @Override
  public TeamDao getDao() {
    return teamDao;
  }

  @Override
  public Boolean checkTeamExists(String teamName) throws AppException {
    AppExceptionUtil.assertNotNull(teamName);
    TeamDto teamDto = teamDao.getDtoByUniqueField(TeamDto.class, TeamDto.builder(), "teamName",
        teamName);
    if (teamDto != null) {
      AppExceptionUtil.assertTrue(teamDto.getTeamName().compareTo(teamName) == 0);
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
    teamDao.addOrUpdate(team);
    return team.getTeamId();
  }

  @Override
  public TeamDto getTeamDtoByTeamId(Integer teamId) throws AppException {
    return teamDao.getDtoByUniqueField(TeamDto.class, TeamDto.builder(), "teamId", teamId);
  }

  @Override
  public Long count(TeamCondition teamCondition) throws AppException {
    return teamDao.count(getHQLString(teamCondition));
  }

  @Override
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
      hqlBuilder.append(" team.teamId = teamUser.teamId and teamUser.userId = ").append(
          teamCondition.userId);
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
  public List<TeamTypeAHeadDto> getTeamTypeAHeadList(TeamCondition teamCondition,
      PageInfo pageInfo) throws AppException {
    return teamDao.findAll(TeamTypeAHeadDto.class, TeamTypeAHeadDto.builder(),
        getHQLString(teamCondition), pageInfo);
  }

  @Override
  public Integer getTeamIdByTeamName(String teamName) throws AppException {
    TeamDto teamDto = teamDao.getDtoByUniqueField(TeamDto.class, TeamDto.builder(), "teamName",
        teamName);
    AppExceptionUtil.assertNotNull(teamDto, "Team not found.");
    return teamDto.getTeamId();
  }

  @Override
  public void deleteTeam(TeamDto teamDto) throws AppException {
    teamDao.deleteEntitiesByField("teamId", teamDto.getTeamId().toString());
  }

  @Override
  public List<TeamListDto> getTeamList(TeamCondition teamCondition, PageInfo pageInfo)
      throws AppException {
    return teamDao.findAll(TeamListDto.class, TeamListDto.builder(), getHQLString(teamCondition),
        pageInfo);
  }
}
