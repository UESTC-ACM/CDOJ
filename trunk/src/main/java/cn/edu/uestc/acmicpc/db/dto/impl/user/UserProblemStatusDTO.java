package cn.edu.uestc.acmicpc.db.dto.impl.user;

/**
 * DTO used in problem status list.
 */
public class UserProblemStatusDTO {
  private Integer problemId;
  private Integer status;

  public UserProblemStatusDTO(Integer problemId, Integer status) {
    this.problemId = problemId;
    this.status = status;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }
}
