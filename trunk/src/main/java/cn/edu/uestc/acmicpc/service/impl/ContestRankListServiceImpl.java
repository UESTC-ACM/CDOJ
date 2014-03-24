package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.contest.ContestShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.contestProblem.ContestProblemSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.status.StatusListDTO;
import cn.edu.uestc.acmicpc.service.iface.ContestProblemService;
import cn.edu.uestc.acmicpc.service.iface.ContestRankListService;
import cn.edu.uestc.acmicpc.service.iface.ContestService;
import cn.edu.uestc.acmicpc.service.iface.StatusService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.rank.RankList;
import cn.edu.uestc.acmicpc.web.rank.RankListBuilder;
import cn.edu.uestc.acmicpc.web.rank.RankListStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

  @Autowired
  public ContestRankListServiceImpl(ContestProblemService contestProblemService,
                                    StatusService statusService,
                                    ContestService contestService) {
    this.contestProblemService = contestProblemService;
    this.statusService = statusService;
    this.contestService = contestService;
  }

  @Override
  public synchronized RankList getRankList(Integer contestId) throws AppException {
    RankList lastModified = rankListPool.get(contestId);
    if (lastModified == null ||
        (System.currentTimeMillis() - lastModified.lastFetched.getTime()) > FETCH_INTERVAL) {
      ContestShowDTO contestShowDTO = contestService.getContestShowDTOByContestId(contestId);
      if (contestShowDTO == null) {
        throw new AppException("No such contest.");
      }

      List<ContestProblemSummaryDTO> contestProblemList = contestProblemService.
          getContestProblemSummaryDTOListByContestId(contestId);

      StatusCondition statusCondition = new StatusCondition();
      statusCondition.contestId = contestShowDTO.getContestId();
      statusCondition.isForAdmin = false;
      // Sort by time
      statusCondition.orderFields = "time";
      statusCondition.orderAsc = "true";
      List<StatusListDTO> statusList = statusService.getStatusList(statusCondition);

      RankListBuilder rankListBuilder = new RankListBuilder();
      for (ContestProblemSummaryDTO problem : contestProblemList) {
        rankListBuilder.addRankListProblem(problem.getProblemId().toString());
      }
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
