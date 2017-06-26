package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.ContestTeamCondition;
import cn.edu.uestc.acmicpc.db.condition.impl.TeamUserCondition;
import cn.edu.uestc.acmicpc.db.criteria.StatusCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ContestFields;
import cn.edu.uestc.acmicpc.db.dto.field.StatusFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDto;
import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestRankListService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.ContestTeamService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.iface.TeamUserService;
import cn.edu.uestc.acmicpc.util.enums.ContestRegistryStatusType;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.web.rank.RankList;
import cn.edu.uestc.acmicpc.web.rank.RankListBuilder;
import cn.edu.uestc.acmicpc.web.rank.RankListStatus;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description
 */
@SuppressWarnings("deprecation")
@Service
public class ContestRankListServiceImpl implements ContestRankListService {

  private final Map<String, RankList> rankListPool = new HashMap<>();
  private final long FETCH_INTERVAL = 10 * 1000; // 10 seconds

  private final ContestProblemService contestProblemService;
  private final StatusService statusService;
  private final ContestService contestService;
  private final ContestTeamService contestTeamService;
  private final TeamUserService teamUserService;

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

  private List<StatusDto> fetchStatusList(Integer contestId) throws AppException {
    StatusCriteria statusCriteria = new StatusCriteria();
    statusCriteria.contestId = contestId;
    statusCriteria.isForAdmin = false;
    // Sort by time
    statusCriteria.orderFields = "time";
    statusCriteria.orderAsc = "true";
    return statusService.getStatusList(statusCriteria, null, StatusFields.FIELDS_FOR_LIST_PAGE);
  }

  private List<ContestTeamListDto> fetchTeamList(Integer contestId) throws AppException {
    ContestDto contestDto = contestService.getContestDtoByContestId(
        contestId, ContestFields.BASIC_FIELDS);
    if (contestDto.getType() == ContestType.INHERIT.ordinal()) {
      contestDto = contestService.getContestDtoByContestId(
          contestDto.getParentId(), ContestFields.BASIC_FIELDS);
    }
    contestId = contestDto.getContestId();

    ContestTeamCondition contestTeamCondition = new ContestTeamCondition();
    contestTeamCondition.contestId = contestId;
    // Fetch accepted teams
    contestTeamCondition.status = ContestRegistryStatusType.ACCEPTED.ordinal();

    // Fetch all
    List<ContestTeamListDto> contestTeamList = contestTeamService.getContestTeamList(
        contestTeamCondition, null);

    if (contestTeamList.size() > 0) {
      List<Integer> teamIdList = new LinkedList<>();
      for (ContestTeamListDto team : contestTeamList) {
        teamIdList.add(team.getTeamId());
      }
      TeamUserCondition teamUserCondition = new TeamUserCondition();
      teamUserCondition.orderFields = "id";
      teamUserCondition.orderAsc = "true";
      teamUserCondition.teamIdList = ArrayUtil.join(teamIdList.toArray(), ",");
      // Search team users
      List<TeamUserListDto> teamUserList = teamUserService.getTeamUserList(teamUserCondition);

      // Put users into teams
      for (ContestTeamListDto team : contestTeamList) {
        team.setTeamUsers(new LinkedList<>());
        team.setInvitedUsers(new LinkedList<>());
        for (TeamUserListDto teamUserListDto : teamUserList) {
          if (team.getTeamId().compareTo(teamUserListDto.getTeamId()) == 0) {
            // Put users
            if (teamUserListDto.getAllow()) {
              team.getTeamUsers().add(teamUserListDto);
            }
          }
        }
      }
    }

    return contestTeamList;
  }

  @Override
  public synchronized RankList getRankList(Integer contestId,
                                           Integer contestType,
                                           Boolean frozen, Integer frozenTime) throws AppException {
    String rankListName = contestId.toString() + ":" + frozen;
    RankList lastModified = rankListPool.get(rankListName);
    if (lastModified == null ||
        (System.currentTimeMillis() - lastModified.lastFetched.getTime()) > FETCH_INTERVAL) {
      ContestDto contestShowDto = contestService.getContestDtoByContestId(
          contestId, ContestFields.FIELDS_FOR_SHOWING);
      if (contestShowDto == null) {
        throw new AppException("No such contest.");
      }

      // Fetch problem list
      List<ContestProblemDto> contestProblemList = contestProblemService.
          getContestProblemSummaryDtoListByContestId(contestId);

      // Fetch status list
      List<StatusDto> statusList = fetchStatusList(contestId);

      RankListBuilder rankListBuilder = new RankListBuilder();
      // Set problem
      for (ContestProblemDto problem : contestProblemList) {
        rankListBuilder.addRankListProblem(problem.getProblemId().toString());
      }

      if (contestType == ContestType.INVITED.ordinal()) {
        // Invited type contest, should include team information
        rankListBuilder.enableTeamMode();

        for (ContestTeamListDto team : fetchTeamList(contestId)) {
          rankListBuilder.addRankListTeam(team);
        }
      }

      // Set status
      for (StatusDto status : statusList) {
        if (contestShowDto.getStartTime().after(status.getTime()) ||
            contestShowDto.getEndTime().before(status.getTime())) {
          // Out of time.
          continue;
        }
        Boolean isFrozen = false;
        if (frozen
            && contestShowDto.getEndTime().getTime() - status.getTime().getTime() <= frozenTime) {
          isFrozen = true;
        }
        rankListBuilder.addStatus(new RankListStatus(
                1, // Total tried
                status.getResultId(), // Return type id
                status.getProblemId().toString(), // Problem id
                status.getUserName(), // User name
                status.getNickName(), // Nick name
                status.getEmail(), // Email
                status.getName(),
                status.getTime().getTime() - contestShowDto.getStartTime().getTime()),
            isFrozen); // Time
      }

      RankList result = rankListBuilder.build();

      rankListPool.put(rankListName, result);
      return result;
    } else {
      return lastModified;
    }
  }
}
