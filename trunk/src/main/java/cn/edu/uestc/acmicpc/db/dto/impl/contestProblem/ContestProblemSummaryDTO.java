package cn.edu.uestc.acmicpc.db.dto.impl.contestProblem;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for contest problem entity.
 * <br/>
 * <code>@Fields({ "problemId", "problemByProblemId.title",
 * "problemByProblemId.source" })</code>
 */
@Fields({"problemId", "order", "problemByProblemId.title",
    "problemByProblemId.source"})
public class ContestProblemSummaryDTO implements BaseDTO<ContestProblem> {

  private Integer problemId;
  private Integer order;
  private String title;
  private String source;

  public ContestProblemSummaryDTO() {

  }

  public ContestProblemSummaryDTO(Integer problemId, Integer order, String title, String source) {
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ContestProblemSummaryDTO that = (ContestProblemSummaryDTO) o;

    if (order != null ? !order.equals(that.order) : that.order != null) {
      return false;
    }
    if (problemId != null ? !problemId.equals(that.problemId) : that.problemId != null) {
      return false;
    }
    if (source != null ? !source.equals(that.source) : that.source != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }

    return true;
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

  public static class Builder implements BaseBuilder<ContestProblemSummaryDTO> {

    @Override
    public ContestProblemSummaryDTO build() {
      return new ContestProblemSummaryDTO(problemId, order, title, source);
    }

    @Override
    public ContestProblemSummaryDTO build(Map<String, Object> properties) {
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