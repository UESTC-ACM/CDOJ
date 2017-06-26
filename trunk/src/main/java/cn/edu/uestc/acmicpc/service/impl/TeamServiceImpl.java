package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.criteria.TeamCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.TeamDao;
import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterators;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link cn.edu.uestc.acmicpc.service.iface.TeamService}
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(rollbackFor = Exception.class)
public class TeamServiceImpl extends AbstractService implements TeamService {

  private static final Set<TeamFields> FIELDS_REQUIRE_MEMBER_LIST = ImmutableSet.of(
      TeamFields.TEAM_USERS, TeamFields.INVITED_USERS);
  private final TeamDao teamDao;
  private final TeamUserService teamUserService;

  @Autowired
  public TeamServiceImpl(TeamDao teamDao, TeamUserService teamUserService) {
    this.teamDao = teamDao;
    this.teamUserService = teamUserService;
  }

  @Override
  public Boolean checkTeamExists(String teamName) throws AppException {
    AppExceptionUtil.assertNotNull(teamName);
    TeamCriteria criteria = new TeamCriteria();
    criteria.teamName = teamName;
    TeamDto teamDto = teamDao.getUniqueDto(criteria, TeamFields.SUMMARY_FIELDS);
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

    // Create team user for team leader
    Integer teamId = team.getTeamId();
    teamUserService.createNewTeamUser(TeamUserDto.builder()
        .setTeamId(teamId)
        .setUserId(leaderId)
        .setAllow(true)
        .build());
    return teamId;
  }

  @Override
  public TeamDto getTeamDtoByTeamId(Integer teamId, Set<TeamFields> fields) throws AppException {
    TeamCriteria criteria = new TeamCriteria();
    criteria.teamId = teamId;
    return getTeamDto(criteria, fields);
  }

  @Override
  public TeamDto getTeamDtoByTeamName(String teamName, Set<TeamFields> fields) throws AppException {
    TeamCriteria criteria = new TeamCriteria();
    criteria.teamName = teamName;
    return getTeamDto(criteria, fields);
  }

  private TeamDto getTeamDto(TeamCriteria criteria, Set<TeamFields> fields) throws AppException {
    return Iterators.getOnlyElement(fillTeamUsers(
        Lists.newArrayList(teamDao.getUniqueDto(criteria, fields)), fields).iterator());
  }

  @Override
  public Long count(TeamCriteria criteria) throws AppException {
    return teamDao.count(criteria);
  }

  @Override
  public List<TeamDto> getTeams(TeamCriteria criteria, PageInfo pageInfo, Set<TeamFields> fields)
      throws AppException {
    return fillTeamUsers(teamDao.findAll(criteria, pageInfo, fields), fields);
  }

  private List<TeamDto> fillTeamUsers(List<TeamDto> teamList, Set<TeamFields> fields)
      throws AppException {
    if (!Sets.intersection(FIELDS_REQUIRE_MEMBER_LIST, fields).isEmpty()) {
      // At most 20 records
      List<Integer> teamIdList = new LinkedList<>();
      for (TeamDto teamListDto : teamList) {
        teamIdList.add(teamListDto.getTeamId());
      }
      TeamUserCondition teamUserCondition = new TeamUserCondition();
      teamUserCondition.orderFields = "id";
      teamUserCondition.orderAsc = "true";
      teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
      // Search team users
      List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

      LinkedListMultimap<Integer, TeamUserListDto> teamIdToTeamUsers = LinkedListMultimap.create();
      for (TeamUserListDto teamUser : teamUserList) {
        teamIdToTeamUsers.put(teamUser.getTeamId(), teamUser);
      }

      // Put users into teams
      for (TeamDto team : teamList) {
        List<TeamUserListDto> teamUser = teamIdToTeamUsers.get(team.getTeamId());
        team.setTeamUsers(
            teamUser.stream().filter((user) -> user.getAllow()).collect(Collectors.toList()));
        team.setInvitedUsers(
            teamUser.stream().filter((user) -> !user.getAllow()).collect(Collectors.toList()));
      }
    }
    return teamList;
  }

  @Override
  public void deleteTeam(TeamDto teamDto) throws AppException {
    teamDao.deleteEntitiesByField("teamId", teamDto.getTeamId().toString());
  }
}
