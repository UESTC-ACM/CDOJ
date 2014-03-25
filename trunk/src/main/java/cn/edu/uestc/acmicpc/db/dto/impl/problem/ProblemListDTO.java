package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO used in problem list.
 * <br/>
 * <code>@Fields({"problemId", "title", "source", "solved", "tried", "isSpj", "isVisible",
 * "difficulty"})</code>
 */
@Fields({"problemId", "title", "source", "solved", "tried", "isSpj", "isVisible",
    "difficulty"})
public class ProblemListDTO implements BaseDTO<Problem> {

  private Integer problemId;
  private String title;
  private String source;
  private Integer solved;
  private Integer tried;
  private Boolean isSpj;
  private Boolean isVisible;
  private Integer difficulty;
  private Integer status;

  public ProblemListDTO() {
  }

  private ProblemListDTO(Integer problemId, String title, String source, Integer solved,
                         Integer tried, Boolean isSpj, Boolean isVisible, Integer difficulty) {
    this.problemId = problemId;
    this.title = title;
    this.source = source;
    this.solved = solved;
    this.tried = tried;
    this.isSpj = isSpj;
    this.isVisible = isVisible;
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ProblemListDTO that = (ProblemListDTO) o;

    if (difficulty != null ? !difficulty.equals(that.difficulty) : that.difficulty != null) {
      return false;
    }
    if (isSpj != null ? !isSpj.equals(that.isSpj) : that.isSpj != null) {
      return false;
    }
    if (isVisible != null ? !isVisible.equals(that.isVisible) : that.isVisible != null) {
      return false;
    }
    if (problemId != null ? !problemId.equals(that.problemId) : that.problemId != null) {
      return false;
    }
    if (solved != null ? !solved.equals(that.solved) : that.solved != null) {
      return false;
    }
    if (source != null ? !source.equals(that.source) : that.source != null) {
      return false;
    }
    if (status != null ? !status.equals(that.status) : that.status != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }
    if (tried != null ? !tried.equals(that.tried) : that.tried != null) {
      return false;
    }

    return true;
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
    result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for {@link ProblemListDTO}.
   */
  public static class Builder implements BaseBuilder<ProblemListDTO> {

    private Builder() {
    }

    private Integer problemId;
    private String title = "";
    private String source = "";
    private Integer solved;
    private Integer tried;
    private Boolean isSpj;
    private Boolean isVisible;
    private Integer difficulty;

    @Override
    public ProblemListDTO build() {
      return new ProblemListDTO(problemId, title, source, solved, tried, isSpj,
          isVisible, difficulty);
    }

    @Override
    public ProblemListDTO build(Map<String, Object> properties) {
      problemId = (Integer) properties.get("problemId");
      title = (String) properties.get("title");
      source = (String) properties.get("source");
      solved = (Integer) properties.get("solved");
      tried = (Integer) properties.get("tried");
      isSpj = (Boolean) properties.get("isSpj");
      isVisible = (Boolean) properties.get("isVisible");
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

    public Builder setDifficulty(Integer difficulty) {
      this.difficulty = difficulty;
      return this;
    }

  }
}
