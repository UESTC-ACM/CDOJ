package cn.edu.uestc.acmicpc.training.entity;

import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingUserView;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.training.parser.TrainingRankListFormatParser;
import cn.edu.uestc.acmicpc.util.exception.ParserException;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingUserRankSummary {

  private Integer penalty;
  private Integer solved;

  private Integer rank;
  private TrainingUserView user;
  private Integer userId;
  private String nickName;
  private TrainingProblemSummaryInfo[] trainingProblemSummaryInfoList;

  public TrainingUserRankSummary(TrainingUser user, String[] userInfo, Integer type)
      throws ParserException {
    setUser(new TrainingUserView(user));
    userId = user.getTrainingUserId();
    nickName = user.getName();
    penalty = solved = 0;

    if (type == Global.TrainingContestType.NORMAL.ordinal()
        || type == Global.TrainingContestType.TEAM.ordinal()) {
      Integer problemCount = userInfo.length - 1;
      trainingProblemSummaryInfoList = new TrainingProblemSummaryInfo[problemCount];
      for (int i = 0; i < problemCount; i++) {
        TrainingProblemSummaryInfo trainingProblemSummaryInfo =
            TrainingRankListFormatParser.getProblemSummaryInfo(userInfo[i + 1]);
        if (trainingProblemSummaryInfo.getSolved()) {
          solved++;
          penalty += trainingProblemSummaryInfo.getPenalty();
        }
        trainingProblemSummaryInfoList[i] = trainingProblemSummaryInfo;
      }
    } else if (type == Global.TrainingContestType.ADJUST.ordinal()) {
      trainingProblemSummaryInfoList = new TrainingProblemSummaryInfo[0];
      penalty = Integer.parseInt(userInfo[1]);
    } else if (type == Global.TrainingContestType.TC.ordinal()
        || type == Global.TrainingContestType.CF.ordinal()) {
      Integer problemCount = userInfo.length - 3;
      trainingProblemSummaryInfoList = new TrainingProblemSummaryInfo[problemCount];
      for (int i = 0; i < problemCount; i++) {
        TrainingProblemSummaryInfo trainingProblemSummaryInfo =
            TrainingRankListFormatParser.getProblemScore(userInfo[i + 3]);
        if (trainingProblemSummaryInfo.getSolved())
          solved++;
        trainingProblemSummaryInfoList[i] = trainingProblemSummaryInfo;
      }
      if (userInfo[1].equals(""))
        penalty = 0;
      else
        penalty = (int) Math.floor(Double.parseDouble(userInfo[1]));
      //*3 ?
      if (userInfo[2].equals("div1"))
        penalty *= 3;
    } else {
      if (userInfo[1].equals(""))
        solved = 0;
      else
        solved = (int) Math.floor(Double.parseDouble(userInfo[1]));
      if (userInfo[2].equals(""))
        penalty = 0;
      else
        penalty = (int) Math.floor(Double.parseDouble(userInfo[2]));
    }
  }

  public Integer getPenalty() {
    return penalty;
  }

  public void setPenalty(Integer penalty) {
    this.penalty = penalty;
  }

  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public TrainingUserView getUser() {
    return user;
  }

  public void setUser(TrainingUserView user) {
    this.user = user;
  }

  public TrainingProblemSummaryInfo[] getTrainingProblemSummaryInfoList() {
    return trainingProblemSummaryInfoList;
  }

  public void setTrainingProblemSummaryInfoList(
      TrainingProblemSummaryInfo[] trainingProblemSummaryInfoList) {
    this.trainingProblemSummaryInfoList = trainingProblemSummaryInfoList;
  }
}
