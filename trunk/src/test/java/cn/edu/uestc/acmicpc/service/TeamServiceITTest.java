package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.TeamCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TeamFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TeamDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.TeamService;
import cn.edu.uestc.acmicpc.service.testing.TeamProvider;
import cn.edu.uestc.acmicpc.service.testing.TeamUserProvider;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.google.common.truth.Truth.assertThat;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Integration test cases for {@link cn.edu.uestc.acmicpc.service.iface.TeamService}
 */
public class TeamServiceITTest extends PersistenceITTest {

  @Autowired private TeamService teamService;
  @Autowired private TeamUserProvider teamUserProvider;
  @Autowired private TeamProvider teamProvider;

  @Test
  public void testCountTeams_byLeaderId() throws AppException {
    setUpCountData();
    TeamCriteria teamCriteria = new TeamCriteria();
    teamCriteria.leaderId = getTestUserId();
    assertThat(teamService.count(teamCriteria)).isEqualTo(6L);
  }

  @Test
  public void testCountTeam_byUserId() throws AppException {
    setUpCountData();
    TeamCriteria teamCriteria = new TeamCriteria();
    teamCriteria.userId = getTestUserId();
    assertThat(teamService.count(teamCriteria)).isEqualTo(12L);
  }

  @Test
  public void testGetTeam() throws AppException {
    TeamDto team = teamProvider.createTeam(getTestUserId());
    // Invite one user
    UserDto user2 = userProvider.createUser();
    teamUserProvider.createTeamUser(team.getTeamId(), user2.getUserId(), false);

    TeamDto result =
        teamService.getTeamDtoByTeamId(team.getTeamId(), TeamFields.FIELDS_FOR_LIST_PAGE);

    assertThat(result.getTeamId()).isEqualTo(team.getTeamId());
    assertThat(result.getTeamName()).isEqualTo(team.getTeamName());
    assertThat(result.getLeaderId()).isEqualTo(getTestUserId());
    assertThat(result.getTeamUsers()).hasSize(1);
    assertThat(result.getTeamUsers().get(0).getUserId()).isEqualTo(getTestUserId());
    assertThat(result.getInvitedUsers()).hasSize(1);
    assertThat(result.getInvitedUsers().get(0).getUserId()).isEqualTo(user2.getUserId());
  }

  @Test
  public void testGetTeams_byLeaderId() throws AppException {
    setUpCountData();
    TeamCriteria teamCriteria = new TeamCriteria();
    teamCriteria.leaderId = getTestUserId();
    List<TeamDto> result =
        teamService.getTeams(teamCriteria, null, TeamFields.FIELDS_FOR_LIST_PAGE);
    assertThat(result).hasSize(6);
  }

  @Test
  public void testGetTeams_byUserId() throws AppException {
    setUpCountData();
    TeamCriteria teamCriteria = new TeamCriteria();
    teamCriteria.userId = getTestUserId();
    List<TeamDto> result =
        teamService.getTeams(teamCriteria, null, TeamFields.FIELDS_FOR_LIST_PAGE);
    assertThat(result).hasSize(12);
  }

  private void setUpCountData() throws AppException {
    UserDto user2 = userProvider.createUser();

    // Current test user owes 6 teams
    for (int teamCount = 0; teamCount < 6; teamCount++) {
      teamProvider.createTeam(getTestUserId());
    }
    // Current test user attend in 6 teams
    for (int teamCount = 0; teamCount < 6; teamCount++) {
      TeamDto team = teamProvider.createTeam(user2.getUserId());
      teamUserProvider.createTeamUser(team.getTeamId(), getTestUserId());
    }
  }
}
