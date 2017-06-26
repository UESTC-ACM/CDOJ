package cn.edu.uestc.acmicpc.web.rank;

import cn.edu.uestc.acmicpc.db.dto.impl.contestteam.ContestTeamListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Rank list builder
 */
public class RankListBuilder {
  private final List<RankListProblem> problemList;
  private final List<RankListUser> userList;
  @SuppressWarnings("unused")
  private final List<ContestTeamListDto> teamList;
  private final Map<String, RankListProblem> problemMap;
  private final Map<String, RankListUser> userMap;
  private final Map<String, Integer> problemIndexMap;
  private final Map<String, ContestTeamListDto> teamMap;
  private Boolean teamMode;

  public RankListBuilder() {
    problemList = new LinkedList<>();
    userList = new LinkedList<>();
    teamList = new LinkedList<>();
    problemMap = new HashMap<>();
    userMap = new HashMap<>();
    problemIndexMap = new HashMap<>();
    teamMap = new HashMap<>();
    teamMode = false;
  }

  public void enableTeamMode() {
    teamMode = true;
  }

  public void addRankListTeam(ContestTeamListDto team) {
    for (TeamUserListDto teamUserListDto : team.getTeamUsers()) {
      teamMap.put(teamUserListDto.getUserName(), team);
    }
  }

  public void addRankListProblem(String title) {
    RankListProblem problem = new RankListProblem();
    problem.title = title;
    problem.solved = 0;
    problem.tried = 0;

    problemIndexMap.put(title, problemList.size());
    problemList.add(problem);
    problemMap.put(title, problem);
  }

  private Integer getRankListProblemIndex(String title) {
    return problemIndexMap.get(title);
  }

  private RankListUser getRankListUser(String userName,
      String nickName,
      String email,
      String reallyName) {
    if (!teamMode) {
      RankListUser user = userMap.get(userName);
      if (user == null) {
        user = new RankListUser();
        user.name = userName;
        user.reallyName = reallyName;
        user.nickName = nickName;
        user.email = email;
        user.penalty = 0L;
        user.rank = 0;
        user.solved = 0;
        user.tried = 0;
        user.itemList = new RankListItem[problemList.size()];
        for (int index = 0; index < problemList.size(); index++) {
          user.itemList[index] = new RankListItem();
          RankListItem rankListItem = user.itemList[index];
          rankListItem.solvedTime = 0L;
          rankListItem.solved = false;
          rankListItem.penalty = 0L;
          rankListItem.tried = 0;
          rankListItem.firstBlood = false;
          rankListItem.triedAfterFrozen = false;
        }

        userList.add(user);
        userMap.put(userName, user);
      }
      return user;
    } else {
      ContestTeamListDto team = teamMap.get(userName);
      if (team == null) {
        return null;
      }
      RankListUser user = userMap.get(team.getTeamName());
      if (user == null) {
        user = new RankListUser();
        user.name = team.getTeamName();
        user.penalty = 0L;
        user.rank = 0;
        user.reallyName = reallyName;
        user.solved = 0;
        user.tried = 0;
        user.itemList = new RankListItem[problemList.size()];
        for (int index = 0; index < problemList.size(); index++) {
          user.itemList[index] = new RankListItem();
          RankListItem rankListItem = user.itemList[index];
          rankListItem.solvedTime = 0L;
          rankListItem.solved = false;
          rankListItem.penalty = 0L;
          rankListItem.tried = 0;
          rankListItem.firstBlood = false;
          rankListItem.triedAfterFrozen = false;
        }
        // Set team users
        user.teamUsers = team.getTeamUsers();

        userList.add(user);
        userMap.put(team.getTeamName(), user);
      }
      return user;
    }
  }

  public void addStatus(RankListStatus status, Boolean frozen) {
    Integer problemIndex = getRankListProblemIndex(status.problemTitle);
    if (problemIndex == null) {
      // Ignore
      return;
    }
    RankListProblem problem = problemList.get(problemIndex);
    RankListUser user = getRankListUser(status.userName, status.nickName, status.email,
        status.reallyName);
    if (user == null) {
      // Ignore
      return;
    }
    RankListItem item = user.itemList[problemIndex];
    if (item.solved) {
      // Has solved
      return;
    }
    problem.tried = problem.tried + 1;
    item.tried = item.tried + 1;
    user.tried = user.tried + 1;
    if (frozen) {
      item.triedAfterFrozen = true;
    } else {
      if (status.result == OnlineJudgeReturnType.OJ_AC.ordinal()) {
        problem.solved = problem.solved + 1;
        if (problem.solved == 1) {
          item.firstBlood = true;
        }
        item.solved = true;
        item.solvedTime = status.time;
        item.tried = item.tried - 1;
        item.penalty = item.solvedTime / 1000 + item.tried * 20 * 60;
        user.solved = user.solved + 1;
        user.penalty = user.penalty + item.penalty;
      }
    }
  }

  public RankList build() {
    RankList result = new RankList();
    result.problemList = problemList.toArray(new RankListProblem[problemList.size()]);
    result.rankList = userList.toArray(new RankListUser[userList.size()]);
    Arrays.sort(result.rankList);
    for (int index = 0; index < result.rankList.length; index++) {
      result.rankList[index].rank = index + 1;
    }
    result.lastFetched = new Timestamp(System.currentTimeMillis());
    return result;
  }
}
