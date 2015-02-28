package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.TeamCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.TeamDao;
import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Implementation for {@link cn.edu.uestc.acmicpc.service.iface.TeamService}
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl extends AbstractService implements TeamService {

  private final TeamDao teamDao;

  @Autowired
  public TeamServiceImpl(TeamDao teamDao) {
    this.teamDao = teamDao;
  }

  @Override
  public Boolean checkTeamExists(String teamName) throws AppException {
    AppExceptionUtil.assertNotNull(teamName);
    TeamCriteria criteria = new TeamCriteria();
    TeamDto teamDto = teamDao.getDtoByUniqueField(criteria, TeamFields.BASIC_FIELDS);
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
  public TeamDto getTeamDtoByTeamId(Integer teamId, Set<TeamFields> fields) throws AppException {
    TeamCriteria criteria = new TeamCriteria();
    criteria.teamId = teamId;
    return teamDao.getDtoByUniqueField(criteria, fields);
  }

  @Override
  public Long count(TeamCriteria criteria) throws AppException {
    return teamDao.count(criteria);
  }

  @Override
  public List<TeamDto> getTeams(TeamCriteria criteria, PageInfo pageInfo, Set<TeamFields> fields)
      throws AppException {
    return teamDao.findAll(criteria, pageInfo, fields);
  }

  @Override
  public Integer getTeamIdByTeamName(String teamName) throws AppException {
    TeamCriteria criteria = new TeamCriteria();
    criteria.teamName = teamName;
    TeamDto teamDto = teamDao.getDtoByUniqueField(criteria, TeamFields.BASIC_FIELDS);
    AppExceptionUtil.assertNotNull(teamDto, "Team not found.");
    return teamDto.getTeamId();
  }

  @Override
  public void deleteTeam(TeamDto teamDto) throws AppException {
    teamDao.deleteEntitiesByField("teamId", teamDto.getTeamId().toString());
  }
}
