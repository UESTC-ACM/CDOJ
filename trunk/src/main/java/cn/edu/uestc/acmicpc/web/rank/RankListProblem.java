package cn.edu.uestc.acmicpc.web.rank;

/**
 * Description
 */
public class RankListProblem {
  public String title;
  public Integer solved;
  public Integer tried;
  public Integer firstSolvedTime;
  public Double maximalScore;

  public RankListProblem() {
  }

  public RankListProblem(String title, Integer solved, Integer tried) {
    this.title = title;
    this.solved = solved;
    this.tried = tried;
    this.firstSolvedTime = Integer.MAX_VALUE;
    this.maximalScore = 0.0;
  }
}
