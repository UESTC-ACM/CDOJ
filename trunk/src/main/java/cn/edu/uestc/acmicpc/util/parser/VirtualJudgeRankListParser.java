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
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VirtualJudgeRankListParser {

  public static void parse(TrainingRankList rankList, List<TrainingPlatformInfoDto> platformList)
      throws AppException {
    // Count problem number
    int totalProblems = 0;
    for (int col = 0; col < rankList.fieldType.length; col++) {
      if (rankList.fieldType[col] == TrainingResultFieldType.PROBLEM.ordinal()) {
        totalProblems++;
      }
    }
    rankList.problems = new RankListProblem[totalProblems];
    for (int id = 0; id < totalProblems; id++) {
      rankList.problems[id] = new RankListProblem("" + (char) ('A' + id), 0, 0);
    }

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
                  return platform.getUserName().equals(userName);
                }
              });
        } else if (rankList.fieldType[col] == TrainingResultFieldType.SOLVED.ordinal()) {
          user.solved = Integer.valueOf(user.rawData[col]);
        } else if (rankList.fieldType[col] == TrainingResultFieldType.PENALTY.ordinal()) {
          user.penalty = parsePenalty(user.rawData[col]);
        } else if (rankList.fieldType[col] == TrainingResultFieldType.PROBLEM.ordinal()) {
          itemList.add(parseTrainingRankListItem(user.rawData[col]));
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
          rankList.problems[id].tried += itemList.get(id).tried + 1;
        } else {
          rankList.problems[id].tried += itemList.get(id).tried;
        }

        if (itemList.get(id).status == ProblemSolveStatusType.PASS) {
          rankList.problems[id].solved++;
          rankList.problems[id].firstSolvedTime = Math.min(rankList.problems[id].firstSolvedTime,
              itemList.get(id).solvedTime);
        }
      }
      user.itemList = itemList.toArray(new TrainingRankListItem[itemList.size()]);
    }

    // Set first blood.
    for (TrainingRankListUser user : rankList.users) {
      for (int id = 0; id < totalProblems; id++) {
        if (user.itemList[id].status == ProblemSolveStatusType.PASS) {
          if (rankList.problems[id].firstSolvedTime.equals(user.itemList[id].solvedTime)) {
            user.itemList[id].status = ProblemSolveStatusType.FB;
          }
        }
      }
    }

    // Sort by solved problems and penalty
    Arrays.sort(rankList.users, new Comparator<TrainingRankListUser>() {
      @Override
      public int compare(TrainingRankListUser a, TrainingRankListUser b) {
        if (a.solved.equals(b.solved)) {
          return a.penalty.compareTo(b.penalty);
        }
        return b.solved.compareTo(a.solved);
      }
    });
    // Set rank
    int currentRank = 1;
    for (int i = 0; i < rankList.users.length; i++) {
      if (i <= 0 || !rankList.users[i].solved.equals(rankList.users[i - 1].solved)
          || !rankList.users[i].penalty.equals(rankList.users[i - 1].penalty)) {
        currentRank = i + 1;
      }
      rankList.users[i].rank = currentRank;
    }
  }

  private static String VJ_PENALTY = "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})$";
  private static String VJ_1Y = "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})$";
  private static String VJ_NORMAL = "^(\\d{1,2})\\s*:\\s*(\\d{2})\\s*:\\s*(\\d{2})\\s*\\(\\s*-\\s*(\\d+)\\s*\\)$";
  private static String VJ_FAIL = "^\\(\\s*-\\s*(\\d+)\\s*\\)$";

  private static Long parsePenalty(String data) {
    Pattern pattern;
    Matcher matcher;

    // hh:mm:ss
    pattern = Pattern.compile(VJ_PENALTY);
    matcher = pattern.matcher(data);
    if (matcher.find()) {
      Integer hh = Integer.parseInt(matcher.group(1));
      Integer mm = Integer.parseInt(matcher.group(2));
      Integer ss = Integer.parseInt(matcher.group(3));
      return (long) (hh * 60 * 60 + mm * 60 + ss);
    }
    return 0L;
  }

  private static TrainingRankListItem parseTrainingRankListItem(String data) {
    data = data.trim();

    Pattern pattern;
    Matcher matcher;

    // hh:mm:ss
    pattern = Pattern.compile(VJ_1Y);
    matcher = pattern.matcher(data);
    if (matcher.find()) {
      Integer hh = Integer.parseInt(matcher.group(1));
      Integer mm = Integer.parseInt(matcher.group(2));
      Integer ss = Integer.parseInt(matcher.group(3));
      return new TrainingRankListItem(ProblemSolveStatusType.PASS,
          hh * 60 * 60 + mm * 60 + ss,
          0,
          hh * 60 * 60 + mm * 60 + ss);
    }
    // hh:mm:ss(-tried)
    pattern = Pattern.compile(VJ_NORMAL);
    matcher = pattern.matcher(data);
    if (matcher.find()) {
      Integer hh = Integer.parseInt(matcher.group(1));
      Integer mm = Integer.parseInt(matcher.group(2));
      Integer ss = Integer.parseInt(matcher.group(3));
      Integer tried = Integer.parseInt(matcher.group(4));
      return new TrainingRankListItem(ProblemSolveStatusType.PASS,
          hh * 60 * 60 + mm * 60 + ss,
          tried,
          hh * 60 * 60 + mm * 60 + ss + tried * 20);
    }
    // (-tried)
    pattern = Pattern.compile(VJ_FAIL);
    matcher = pattern.matcher(data);
    if (matcher.find()) {
      Integer tried = Integer.parseInt(matcher.group(1));
      return new TrainingRankListItem(ProblemSolveStatusType.FAIL, Integer.MAX_VALUE, tried, 0);
    }

    return new TrainingRankListItem(ProblemSolveStatusType.NONE, Integer.MAX_VALUE, 0, 0);
  }
}
