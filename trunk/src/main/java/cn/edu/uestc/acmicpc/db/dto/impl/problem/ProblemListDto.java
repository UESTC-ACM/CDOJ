package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import java.util.Map;
import java.util.Objects;

/**
 * Dto used in problem list.
 */
@Fields({ "problemId", "title", "source", "solved", "tried", "isSpj", "isVisible",
    "type", "difficulty" })
public class ProblemListDto implements BaseDto<Problem> {

  private Integer problemId;
  private String title;
  private String source;
  private Integer solved;
  private Integer tried;
  private Boolean isSpj;
  private Boolean isVisible;;
  private ProblemType type;
  private Integer difficulty;
  private Integer status;

  public ProblemListDto() {
  }

  private ProblemListDto(Integer problemId, String title, String source, Integer solved,
      Integer tried, Boolean isSpj, Boolean isVisible, ProblemType type, Integer difficulty) {
    this.problemId = problemId;
    this.title = title;
    this.source = source;
    this.solved = solved;
    this.tried = tried;
    this.isSpj = isSpj;
    this.isVisible = isVisible;
    this.type = type;
    this.difficulty = difficulty;
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

  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean isSpj) {
    this.isSpj = isSpj;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public ProblemType getType() {
    return type;
  }

  public void setType(ProblemType type) {
    this.type = type;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProblemListDto)) {
      return false;
    }

    ProblemListDto that = (ProblemListDto) o;
    return Objects.equals(this.difficulty, that.difficulty)
        && Objects.equals(this.isSpj, that.isSpj)
        && Objects.equals(this.isVisible, that.isVisible)
        && Objects.equals(this.type, that.type)
        && Objects.equals(this.problemId, that.problemId)
        && Objects.equals(this.solved, that.solved)
        && Objects.equals(this.source, that.source)
        && Objects.equals(this.status, that.status)
        && Objects.equals(this.title, that.title)
        && Objects.equals(this.tried, that.tried);
  }

  @Override
  public int hashCode() {
    int result = problemId != null ? problemId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (source != null ? source.hashCode() : 0);
    result = 31 * result + (solved != null ? solved.hashCode() : 0);
    result = 31 * result + (tried != null ? tried.hashCode() : 0);
    result = 31 * result + (isSpj != null ? isSpj.hashCode() : 0);
    result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for {@link ProblemListDto}.
   */
  public static class Builder implements BaseDtoBuilder<ProblemListDto> {

    private Builder() {
    }

    private Integer problemId;
    private String title = "";
    private String source = "";
    private Integer solved;
    private Integer tried;
    private Boolean isSpj;
    private Boolean isVisible;
    private ProblemType type;
    private Integer difficulty;

    @Override
    public ProblemListDto build() {
      return new ProblemListDto(problemId, title, source, solved, tried, isSpj,
          isVisible, type, difficulty);
    }

    @Override
    public ProblemListDto build(Map<String, Object> properties) {
      problemId = (Integer) properties.get("problemId");
      title = (String) properties.get("title");
      source = (String) properties.get("source");
      solved = (Integer) properties.get("solved");
      tried = (Integer) properties.get("tried");
      isSpj = (Boolean) properties.get("isSpj");
      isVisible = (Boolean) properties.get("isVisible");
      type = (ProblemType) properties.get("type");
      difficulty = (Integer) properties.get("difficulty");
      return build();
    }

    public Builder setProblemId(Integer problemID) {
      this.problemId = problemID;
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

    public Builder setSolved(Integer solved) {
      this.solved = solved;
      return this;
    }

    public Builder setTried(Integer tried) {
      this.tried = tried;
      return this;
    }

    public Builder setIsSpj(Boolean isSpj) {
      this.isSpj = isSpj;
      return this;
    }

    public Builder setIsVisible(Boolean isVisivle) {
      this.isVisible = isVisivle;
      return this;
    }

    public Builder setType(ProblemType type) {
      this.type = type;
      return this;
    }

    public Builder setDifficulty(Integer difficulty) {
      this.difficulty = difficulty;
      return this;
    }

  }
}
