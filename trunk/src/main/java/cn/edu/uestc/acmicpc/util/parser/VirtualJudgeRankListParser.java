package cn.edu.uestc.acmicpc.util.parser;

import cn.edu.uestc.acmicpc.util.enums.TrainingResultFieldType;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankList;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankListUser;

/**
 * Description
 */
public class VirtualJudgeRankListParser {

  public static void parse(TrainingRankList rankList) {
    for (TrainingRankListUser user: rankList.users) {
      for (int col = 0; col < user.rawData.length; col++) {
        if (rankList.fieldType[col] == TrainingResultFieldType.USERNAME.ordinal()) {
          // parse user name
        } else if (rankList.fieldType[col] == TrainingResultFieldType.SOLVED.ordinal()) {

        } else if (rankList.fieldType[col] == TrainingResultFieldType.PENALTY.ordinal()) {

        } else if (rankList.fieldType[col] == TrainingResultFieldType.PROBLEM.ordinal()) {

        }
      }
    }
  }
}
