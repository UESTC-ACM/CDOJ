package cn.edu.uestc.acmicpc.web.rank;

import cn.edu.uestc.acmicpc.util.enums.ProblemSolveStatusType;

public class TrainingRankListItem {
  public ProblemSolveStatusType status;
  public Integer solvedTime;
  public Integer tried;
  public Integer penalty;
  public Double score;

  public TrainingRankListItem() {
  }

  public TrainingRankListItem(ProblemSolveStatusType status, Integer solvedTime, Integer tried, Integer penalty) {
    this.status = status;
    this.solvedTime = solvedTime;
    this.tried = tried;
    this.penalty = penalty;
  }

  public TrainingRankListItem(ProblemSolveStatusType status, Double score) {
    this.status = status;
    this.score = score;
  }
}
