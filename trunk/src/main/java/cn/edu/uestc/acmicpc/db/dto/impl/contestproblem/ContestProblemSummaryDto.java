package cn.edu.uestc.acmicpc.db.dto.impl.contestproblem;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

/**
 * Dto for contest problem entity. <br/>
 * <code>@Fields({ "problemId", "problemByProblemId.title",
 * "problemByProblemId.source" })</code>
 */
@Fields({ "problemId", "order", "problemByProblemId.title",
    "problemByProblemId.source" })
public class ContestProblemSummaryDto implements BaseDto<ContestProblem> {

  private Integer problemId;
  private Integer order;
  private String title;
  private String source;

  public ContestProblemSummaryDto() {

  }

  public ContestProblemSummaryDto(Integer problemId, Integer order, String title, String source) {
    this.problemId = problemId;
    this.order = order;
    this.title = title;
    this.source = source;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContestProblemSummaryDto)) {
      return false;
    }

    ContestProblemSummaryDto that = (ContestProblemSummaryDto) o;
    return Objects.equals(this.order, that.order)
        && Objects.equals(this.problemId, that.problemId)
        && Objects.equals(this.source, that.source)
        && Objects.equals(this.title, that.title);
  }

  @Override
  public int hashCode() {
    int result = problemId != null ? problemId.hashCode() : 0;
    result = 31 * result + (order != null ? order.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (source != null ? source.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<ContestProblemSummaryDto> {

    @Override
    public ContestProblemSummaryDto build() {
      return new ContestProblemSummaryDto(problemId, order, title, source);
    }

    @Override
    public ContestProblemSummaryDto build(Map<String, Object> properties) {
      problemId = (Integer) properties.get("problemId");
      order = (Integer) properties.get("order");
      title = (String) properties.get("problemByProblemId.title");
      source = (String) properties.get("problemByProblemId.source");
      return build();
    }

    private Integer problemId;
    private Integer order;
    private String title;
    private String source;

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Builder setOrder(Integer order) {
      this.order = order;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setSource(String source) {
      this.source = source;
      return this;
    }
  }
}