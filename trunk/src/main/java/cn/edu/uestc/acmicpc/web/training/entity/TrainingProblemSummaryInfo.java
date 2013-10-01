package cn.edu.uestc.acmicpc.web.training.entity;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingProblemSummaryInfo {

  private Integer tried;
  private Boolean isSolved;
  private Boolean isFirstSolved;
  private Integer penalty;
  private Integer solutionTime;

  public TrainingProblemSummaryInfo() {
    isSolved = isFirstSolved = false;
    penalty = solutionTime = tried = 0;
  }

  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  public Boolean getSolved() {
    return isSolved;
  }

  public void setSolved(Boolean solved) {
    isSolved = solved;
  }

  public Boolean getFirstSolved() {
    return isFirstSolved;
  }

  public void setFirstSolved(Boolean firstSolved) {
    isFirstSolved = firstSolved;
  }

  public Integer getPenalty() {
    return penalty;
  }

  public void setPenalty(Integer penalty) {
    this.penalty = penalty;
  }

  public Integer getSolutionTime() {
    return solutionTime;
  }

  public void setSolutionTime(Integer solutionTime) {
    this.solutionTime = solutionTime;
  }
}
