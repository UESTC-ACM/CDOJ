package cn.edu.uestc.acmicpc.util.parser;

import cn.edu.uestc.acmicpc.util.enums.TrainingContestType;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankList;

public class TrainingContestResultParser {

  public static boolean isUserName(String value) {
    return value.compareToIgnoreCase("name") == 0
        || value.compareToIgnoreCase("team") == 0
        || value.compareToIgnoreCase("id") == 0
        || value.compareToIgnoreCase("nick name") == 0
        || value.compareToIgnoreCase("姓名") == 0
        || value.compareToIgnoreCase("user") == 0;
  }

  public static boolean isPenalty(String value) {
    return value.compareToIgnoreCase("penalty") == 0;
  }

  public static boolean isSolved(String value) {
    return value.compareToIgnoreCase("solved") == 0
        || value.compareToIgnoreCase("solve") == 0;
  }

  public static boolean isUnused(String value) {
    return value.compareToIgnoreCase("#") == 0
        || value.compareToIgnoreCase("rank") == 0;
  }

  public TrainingContestResultParser() {
  }

  public void parse(TrainingRankList rankList,
      TrainingContestType contestType,
      TrainingPlatformType platformType) {
    if (contestType == TrainingContestType.CONTEST) {
      if (platformType == TrainingPlatformType.VJ) {
        VirtualJudgeRankListParser.parse(rankList);
      } else {
        // TODO
      }
    } else {
      // TODO
    }
  }
}
