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
    } else {
      return TrainingResultFieldType.PROBLEM;
    }
  }

  public static boolean isType(String value) {
    return value.compareToIgnoreCase("Div") == 0;
  }

  public static boolean isSuccessfulHack(String value) {
    return value.compareTo("+") == 0;
  }

  public static boolean isUnsuccessfulHack(String value) {
    return value.compareTo("-") == 0;
  }

  public static boolean isScore(String value) {
    return value.compareToIgnoreCase("score") == 0;
  }

  public static boolean isUserName(String value) {
    return value.compareToIgnoreCase("name") == 0
        || value.compareToIgnoreCase("team") == 0
        || value.compareToIgnoreCase("id") == 0
        || value.compareToIgnoreCase("nick name") == 0
        || value.compareToIgnoreCase("姓名") == 0
        || value.compareToIgnoreCase("user") == 0
        || value.compareToIgnoreCase("handle") == 0;
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
      if (platformType == TrainingPlatformType.VJ ||
          platformType == TrainingPlatformType.HDOJ) {
        VirtualJudgeRankListParser.parse(rankList, platformList);
      } else if (platformType == TrainingPlatformType.CF) {
        CodeforcesRankListParser.parse(rankList, platformList);
      }
    } else {
      // TODO
    }
  }
}
