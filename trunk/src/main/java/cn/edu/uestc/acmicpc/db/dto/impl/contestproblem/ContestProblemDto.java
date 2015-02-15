package cn.edu.uestc.acmicpc.db.dto.impl.contestproblem;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

@Fields({ "contestProblemId", "contestId", "order", "problemId" })
public class ContestProblemDto implements BaseDto<ContestProblem> {

  public ContestProblemDto() {
  }

  private ContestProblemDto(Integer contestProblemId, Integer contestId, Integer order,
      Integer problemId) {
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
    if (!(o instanceof ContestProblemDto)) {
      return false;
    }

    ContestProblemDto that = (ContestProblemDto) o;
    return Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.contestProblemId, that.contestProblemId)
        && Objects.equals(this.order, that.order)
        && Objects.equals(this.problemId, that.problemId);
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

  public static class Builder implements BaseDtoBuilder<ContestProblemDto> {

    private Builder() {
    }

    @Override
    public ContestProblemDto build() {
      return new ContestProblemDto(contestProblemId, contestId, order, problemId);
    }

    @Override
    public ContestProblemDto build(Map<String, Object> properties) {
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
