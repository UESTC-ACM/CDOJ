package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestTeam.ContestTeamListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestRankListService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.rank.RankList;
import cn.edu.uestc.acmicpc.web.rank.RankListBuilder;
import cn.edu.uestc.acmicpc.web.rank.RankListStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description
 */
@Service
public class ContestRankListServiceImpl extends AbstractService implements ContestRankListService {

  private final Map<Integer, RankList> rankListPool = new HashMap<>();
  private final long FETCH_INTERVAL = 10 * 1000; //10 seconds

  private ContestProblemService contestProblemService;
  private StatusService statusService;
  private ContestService contestService;
  private ContestTeamService contestTeamService;
  private TeamUserService teamUserService;

  @Autowired
  public ContestRankListServiceImpl(ContestProblemService contestProblemService,
                                    StatusService statusService,
                                    ContestService contestService,
                                    ContestTeamService contestTeamService,
                                    TeamUserService teamUserService) {
    this.contestProblemService = contestProblemService;
    this.statusService = statusService;
    this.contestService = contestService;
    this.contestTeamService = contestTeamService;
    this.teamUserService = teamUserService;
  }

  private List<StatusListDTO> fetchStatusList(Integer contestId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.contestId = contestId;
    statusCondition.isForAdmin = false;
    // Sort by time
    statusCondition.orderFields = "time";
    statusCondition.orderAsc = "true";
    return statusService.getStatusList(statusCondition);
  }

  private List<ContestTeamListDTO> fetchTeamList(Integer contestId) throws AppException {
    ContestDTO contestDTO = contestService.getContestDTOByContestId(contestId);
    if (contestDTO.getType() == Global.ContestType.INHERIT.ordinal()) {
      contestDTO = contestService.getContestDTOByContestId(contestDTO.getParentId());
    }
    contestId = contestDTO.getContestId();

    ContestTeamCondition contestTeamCondition = new ContestTeamCondition();
    contestTeamCondition.contestId = contestId;
    // Fetch accepted teams
    contestTeamCondition.status = Global.ContestRegistryStatus.ACCEPTED.ordinal();

    // Fetch all
    List<ContestTeamListDTO> contestTeamList = contestTeamService.getContestTeamList(
        contestTeamCondition, null);

    if (contestTeamList.size() > 0) {
      List<Integer> teamIdList = new LinkedList<>();
      for (ContestTeamListDTO team : contestTeamList) {
        teamIdList.add(team.getTeamId());
      }
      TeamUserCondition teamUserCondition = new TeamUserCondition();
      teamUserCondition.orderFields = "id";
      teamUserCondition.orderAsc = "true";
      teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
      // Search team users
      List<TeamUserListDTO> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

      // Put users into teams
      for (ContestTeamListDTO team : contestTeamList) {
        team.setTeamUsers(new LinkedList<TeamUserListDTO>());
        team.setInvitedUsers(new LinkedList<TeamUserListDTO>());
        for (TeamUserListDTO teamUserListDTO : teamUserList) {
          if (team.getTeamId().compareTo(teamUserListDTO.getTeamId()) == 0) {
            // Put users
            if (teamUserListDTO.getAllow()) {
              team.getTeamUsers().add(teamUserListDTO);
            }
          }
        }
      }
    }

    return contestTeamList;
  }

  @Override
  public synchronized RankList getRankList(Integer contestId,
                                           Boolean invitedContest) throws AppException {
    RankList lastModified = rankListPool.get(contestId);
    if (lastModified == null ||
        (System.currentTimeMillis() - lastModified.lastFetched.getTime()) > FETCH_INTERVAL) {
      ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(contestId);
      if (contestShowDTO == null) {
        throw new AppException("No such contest.");
      }

      // Fetch problem list
      List<ContestProblemSummaryDTO> contestProblemList = contestProblemService.
          getContestProblemSummaryDTOListByContestId(contestId);

      // Fetch status list
      List<StatusListDTO> statusList = fetchStatusList(contestId);

      RankListBuilder rankListBuilder = new RankListBuilder();
      // Set problem
      for (ContestProblemSummaryDTO problem : contestProblemList) {
        rankListBuilder.addRankListProblem(problem.getProblemId().toString());
      }

      if (invitedContest) {
        // Invited type contest, should include team information
        rankListBuilder.enableTeamMode();

        for (ContestTeamListDTO team: fetchTeamList(contestId)) {
          rankListBuilder.addRankListTeam(team);
        }
      }

      // Set status
      for (StatusListDTO status : statusList) {
        if (contestShowDTO.getStartTime().after(status.getTime()) ||
            contestShowDTO.getEndTime().before(status.getTime())) {
          // Out of time.
          continue;
        }
        rankListBuilder.addStatus(new RankListStatus(
            1, // Total tried
            status.getReturnTypeId(), // Return type id
            status.getProblemId().toString(), // Problem id
            status.getUserName(), // User name
            status.getNickName(), // Nick name
            status.getEmail(), // Email
            status.getTime().getTime() - contestShowDTO.getStartTime().getTime())); // Time
      }

      RankList result = rankListBuilder.build();
      rankListPool.put(contestId, result);
      return result;
    } else {
      return lastModified;
    }
  }
}
