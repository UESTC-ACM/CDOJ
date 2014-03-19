package cn.edu.uestc.acmicpc.db.dto.impl.contestProblem;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

@Fields({"contestProblemId", "contestId", "order", "problemId"})
public class ContestProblemDTO implements BaseDTO<ContestProblem> {

  public ContestProblemDTO() {
  }

  private ContestProblemDTO(Integer contestProblemId, Integer contestId, Integer order, Integer problemId) {
    this.contestProblemId = contestProblemId;
    this.contestId = contestId;
    this.order = order;
    this.problemId = problemId;
  }

  private Integer contestProblemId;
  private Integer contestId;
  private Integer order;
  private Integer problemId;

  public Integer getContestProblemId() {
    return contestProblemId;
  }

  public void setContestProblemId(Integer contestProblemId) {
    this.contestProblemId = contestProblemId;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ContestProblemDTO that = (ContestProblemDTO) o;

    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }
    if (contestProblemId != null ? !contestProblemId.equals(that.contestProblemId) : that.contestProblemId != null) {
      return false;
    }
    if (order != null ? !order.equals(that.order) : that.order != null) {
      return false;
    }
    if (problemId != null ? !problemId.equals(that.problemId) : that.problemId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = contestProblemId != null ? contestProblemId.hashCode() : 0;
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    result = 31 * result + (order != null ? order.hashCode() : 0);
    result = 31 * result + (problemId != null ? problemId.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestProblemDTO> {

    private Builder() {
    }

    @Override
    public ContestProblemDTO build() {
      return new ContestProblemDTO(contestProblemId, contestId, order, problemId);
    }

    @Override
    public ContestProblemDTO build(Map<String, Object> properties) {
      contestProblemId = (Integer) properties.get("contestProblemId");
      contestId = (Integer) properties.get("contestId");
      order = (Integer) properties.get("order");
      problemId = (Integer) properties.get("problemId");
      return build();

    }

    private Integer contestProblemId;
    private Integer contestId;
    private Integer order;
    private Integer problemId;

    public Integer getContestProblemId() {
      return contestProblemId;
    }

    public Builder setContestProblemId(Integer contestProblemId) {
      this.contestProblemId = contestProblemId;
      return this;
    }

    public Integer getContestId() {
      return contestId;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Integer getOrder() {
      return order;
    }

    public Builder setOrder(Integer order) {
      this.order = order;
      return this;
    }

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }
  }
}
