package cn.edu.uestc.acmicpc.util.parser;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.util.enums.TrainingContestType;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.enums.TrainingResultFieldType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankList;
import cn.edu.uestc.acmicpc.web.rank.TrainingRankListUser;
import java.util.LinkedList;
import java.util.List;

public class TrainingContestResultParser {

  public static TrainingResultFieldType getType(String value) {
    if (isUserName(value)) {
      return TrainingResultFieldType.USERNAME;
    } else if (isPenalty(value)) {
      return TrainingResultFieldType.PENALTY;
    } else if (isSolved(value)) {
      return TrainingResultFieldType.SOLVED;
    } else if (isUnused(value)) {
      return TrainingResultFieldType.UNUSED;
    } else if (isScore(value)) {
      return TrainingResultFieldType.SCORE;
    } else if (isSuccessfulHack(value)) {
      return TrainingResultFieldType.SUCCESSFUL_HACK;
    } else if (isUnsuccessfulHack(value)) {
      return TrainingResultFieldType.UNSUCCESSFUL_HACK;
    } else if (isType(value)) {
      return TrainingResultFieldType.TYPE;
    } else if (isDeduct(value)) {
      return TrainingResultFieldType.DEDUCT;
    } else {
      return TrainingResultFieldType.PROBLEM;
    }
  }

  public static boolean isType(String value) {
    return "Div".compareToIgnoreCase(value) == 0;
  }

  public static boolean isSuccessfulHack(String value) {
    return "+".compareTo(value) == 0;
  }

  public static boolean isUnsuccessfulHack(String value) {
    return "-".compareTo(value) == 0;
  }

  public static boolean isScore(String value) {
    return "score".compareToIgnoreCase(value) == 0;
  }

  public static boolean isUserName(String value) {
    return "name".compareToIgnoreCase(value) == 0
        || "team".compareToIgnoreCase(value) == 0
        || "id".compareToIgnoreCase(value) == 0
        || "nick name".compareToIgnoreCase(value) == 0
        || "姓名".compareToIgnoreCase(value) == 0
        || "user".compareToIgnoreCase(value) == 0
        || "handle".compareToIgnoreCase(value) == 0;
  }

  public static boolean isPenalty(String value) {
    return "penalty".compareToIgnoreCase(value) == 0;
  }

  public static boolean isSolved(String value) {
    return "solved".compareToIgnoreCase(value) == 0
        || "solve".compareToIgnoreCase(value) == 0;
  }

  public static boolean isUnused(String value) {
    return "#".compareToIgnoreCase(value) == 0
        || "rank".compareToIgnoreCase(value) == 0;
  }

  public static boolean isDeduct(String value) {
    return "deduct".compareToIgnoreCase(value) == 0;
  }

  interface AcceptedFunction {
    public boolean accepted(String userName, TrainingPlatformInfoDto platform);
  }

  public static void parseUserName(TrainingRankListUser trainingRankListUser,
      String userName,
      List<TrainingPlatformInfoDto> platformList,
      AcceptedFunction acceptedFunction) {
    List<TrainingUserDto> userList = new LinkedList<>();
    for (TrainingPlatformInfoDto platform : platformList) {
      if (acceptedFunction.accepted(userName, platform)) {
        userList.add(TrainingUserDto.builder()
            .setTrainingUserId(platform.getTrainingUserId())
            .setTrainingUserName(platform.getTrainingUserName())
            .build());
      }
    }
    trainingRankListUser.users = userList.toArray(new TrainingUserDto[userList.size()]);
  }

  private final List<TrainingPlatformInfoDto> platformList;

  public TrainingContestResultParser(List<TrainingPlatformInfoDto> platformList) {
    this.platformList = platformList;
  }

  public void parse(TrainingRankList rankList,
      TrainingContestType contestType,
      TrainingPlatformType platformType) throws AppException {
    if (contestType == TrainingContestType.CONTEST) {
      if (platformType == TrainingPlatformType.VJ) {
        VirtualJudgeRankListParser.parse(rankList, platformList);
      } else if (platformType == TrainingPlatformType.CF ||
          platformType == TrainingPlatformType.TC) {
        CodeforcesRankListParser.parse(rankList, platformList);
      }
    } else {
      // TODO
    }
  }
}
