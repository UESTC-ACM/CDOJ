package cn.edu.uestc.acmicpc.util.parser;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.util.enums.ProblemSolveStatusType;
import cn.edu.uestc.acmicpc.util.enums.TrainingResultFieldType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.rank.RankListProblem;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankList;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankListItem;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankListUser;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CodeforcesRankListParser {

  public static void parse(TrainingRankList rankList, List<TrainingPlatformInfoDto> platformList)
      throws AppException {
    // Count problem number
    int totalProblems = 0;
    for (int col = 0; col < rankList.fieldType.length; col++) {
      if (rankList.fieldType[col] == TrainingResultFieldType.PROBLEM.ordinal()) {
        totalProblems++;
      }
    }
    LinkedList<RankListProblem> problemList = new LinkedList<>();
    for (int col = 0; col < rankList.fieldType.length; col++) {
      if (rankList.fieldType[col] == TrainingResultFieldType.PROBLEM.ordinal()) {
        problemList.add(new RankListProblem(rankList.fields[col], 0, 0));
      }
    }
    rankList.problems = problemList.toArray(new RankListProblem[totalProblems]);

    // Parse raw data
    for (TrainingRankListUser user : rankList.users) {
      List<TrainingRankListItem> itemList = new LinkedList<>();
      for (int col = 0; col < user.rawData.length; col++) {
        if (rankList.fieldType[col] == TrainingResultFieldType.USERNAME.ordinal()) {
          // parse user name
          user.userName = user.rawData[col];
          TrainingContestResultParser.parseUserName(user, user.rawData[col], platformList,
              new TrainingContestResultParser.AcceptedFunction() {
                @Override
                public boolean accepted(String userName, TrainingPlatformInfoDto platform) {
                  return Objects.equals(platform.getUserName(), userName);
                }
              });
        } else if (rankList.fieldType[col] == TrainingResultFieldType.SCORE.ordinal()) {
          user.score = Double.valueOf(user.rawData[col]);
        } else if (rankList.fieldType[col] == TrainingResultFieldType.SUCCESSFUL_HACK.ordinal()) {
          user.successfulHack = Integer.valueOf(user.rawData[col]);
        } else if (rankList.fieldType[col] == TrainingResultFieldType.UNSUCCESSFUL_HACK.ordinal()) {
          user.unsuccessfulHack = Integer.valueOf(user.rawData[col]);
        } else if (rankList.fieldType[col] == TrainingResultFieldType.TYPE.ordinal()) {
          user.type = user.rawData[col];
        } else if (rankList.fieldType[col] == TrainingResultFieldType.PROBLEM.ordinal()) {
          Double score = Double.valueOf(user.rawData[col]);
          ProblemSolveStatusType state = ProblemSolveStatusType.NONE;
          if (score > 0) {
            state = ProblemSolveStatusType.PASS;
          } else if (score < 0) {
            state = ProblemSolveStatusType.FAIL;
          }
          itemList.add(new TrainingRankListItem(state, score));
        } else if (rankList.fieldType[col] == TrainingResultFieldType.DEDUCT.ordinal()) {
          if (user.rawData[col].trim().compareTo("") == 0) {
            user.deductRating = null;
          } else {
            user.deductRating = Integer.valueOf(user.rawData[col]);
          }
        }
      }
      for (int id = 0; id < totalProblems; id++) {
        if (itemList.get(id).status == ProblemSolveStatusType.PASS) {
          rankList.problems[id].maximalScore = Math.max(rankList.problems[id].maximalScore,
              itemList.get(id).score);
        }
      }
      user.itemList = itemList.toArray(new TrainingRankListItem[itemList.size()]);
    }

    // Set first blood.
    for (TrainingRankListUser user : rankList.users) {
      for (int id = 0; id < totalProblems; id++) {
        if (user.itemList[id].status == ProblemSolveStatusType.PASS
            && Objects.equals(rankList.problems[id].maximalScore, user.itemList[id].score)) {
          user.itemList[id].status = ProblemSolveStatusType.FB;
        }
      }
    }

    // Sort by solved problems and penalty
    Arrays.sort(rankList.users, (a, b) -> b.score.compareTo(a.score));
    // Set rank
    int currentRank = 1;
    for (int i = 0; i < rankList.users.length; i++) {
      if (i <= 0 || !rankList.users[i].score.equals(rankList.users[i - 1].score)) {
        currentRank = i + 1;
      }
      rankList.users[i].rank = currentRank;
    }
  }
}
