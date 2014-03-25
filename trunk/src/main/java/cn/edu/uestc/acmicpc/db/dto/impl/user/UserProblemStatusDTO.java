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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserProblemStatusDTO that = (UserProblemStatusDTO) o;

    if (problemId != null ? !problemId.equals(that.problemId) : that.problemId != null) {
      return false;
    }
    if (status != null ? !status.equals(that.status) : that.status != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = problemId != null ? problemId.hashCode() : 0;
    result = 31 * result + (status != null ? status.hashCode() : 0);
    return result;
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
