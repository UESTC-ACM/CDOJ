package cn.edu.uestc.acmicpc.web.rank;

import cn.edu.uestc.acmicpc.util.settings.Global;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Description
 */
public class RankListBuilder {
  private List<RankListProblem> problemList;
  private List<RankListUser> userList;
  private Map<String, RankListProblem> problemMap;
  private Map<String, RankListUser> userMap;
  private Map<String, Integer> problemIndexMap;

  public RankListBuilder() {
    problemList = new LinkedList<>();
    userList = new LinkedList<>();
    problemMap = new HashMap<>();
    userMap = new HashMap<>();
    problemIndexMap = new HashMap<>();
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

  private RankListUser getRankListUser(String userName, String nickName) {
    RankListUser user = userMap.get(userName);
    if (user == null) {
      user = new RankListUser();
      user.userName = userName;
      user.nickName = nickName;
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
      }

      userList.add(user);
      userMap.put(userName, user);
    }
    return user;
  }

  public void addStatus(RankListStatus status) {
    Integer problemIndex = getRankListProblemIndex(status.problemTitle);
    if (problemIndex == null) {
      // Ignore
      return;
    }
    RankListProblem problem = problemList.get(problemIndex);
    RankListUser user = getRankListUser(status.userName, status.nickName);
    RankListItem item = user.itemList[problemIndex];
    if (item.solved) {
      // Has solved
      return;
    }
    problem.tried = problem.tried + 1;
    item.tried = item.tried + 1;
    user.tried = user.tried + 1;
    if (status.result == Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
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
