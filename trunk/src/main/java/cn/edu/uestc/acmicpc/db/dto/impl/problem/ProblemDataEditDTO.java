package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import javax.validation.constraints.NotNull;

/**
 * DTO post from problem data editor.
 */
public class ProblemDataEditDTO {

  public ProblemDataEditDTO() {
  }

  private ProblemDataEditDTO(Integer problemId, Integer timeLimit, Integer memoryLimit,
                             Integer outputLimit, Boolean isSpj, Integer javaTimeLimit,
                             Integer javaMemoryLimit) {
    this.problemId = problemId;
    this.timeLimit = timeLimit;
    this.memoryLimit = memoryLimit;
    this.outputLimit = outputLimit;
    this.isSpj = isSpj;
    this.javaTimeLimit = javaTimeLimit;
    this.javaMemoryLimit = javaMemoryLimit;
  }

  @NotNull(message = "No such problem!")
  private Integer problemId;

  @NotNull(message = "Please tell me the time limit!")
  private Integer timeLimit;

  @NotNull(message = "Please tell me the memory limit!")
  private Integer memoryLimit;

  @NotNull(message = "Please tell me the output limit!")
  private Integer outputLimit;

  @NotNull(message = "!")
  private Boolean isSpj;

  @NotNull(message = "Please tell me the time limit!")
  private Integer javaTimeLimit;

  @NotNull(message = "Please tell me the memory limit!")
  private Integer javaMemoryLimit;

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public Integer getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(Integer timeLimit) {
    this.timeLimit = timeLimit;
  }

  public Integer getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(Integer memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
  }

  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean isSpj) {
    this.isSpj = isSpj;
  }

  public Integer getJavaTimeLimit() {
    return javaTimeLimit;
  }

  public void setJavaTimeLimit(Integer javaTimeLimit) {
    this.javaTimeLimit = javaTimeLimit;
  }

  public Integer getJavaMemoryLimit() {
    return javaMemoryLimit;
  }

  public void setJavaMemoryLimit(Integer javaMemoryLimit) {
    this.javaMemoryLimit = javaMemoryLimit;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public ProblemDataEditDTO build() {
      return new ProblemDataEditDTO(problemId, timeLimit, memoryLimit, outputLimit, isSpj,
          javaTimeLimit, javaMemoryLimit);
    }

    private Integer problemId;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer outputLimit;
    private Boolean isSpj;
    private Integer javaTimeLimit;
    private Integer javaMemoryLimit;

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Integer getTimeLimit() {
      return timeLimit;
    }

    public Builder setTimeLimit(Integer timeLimit) {
      this.timeLimit = timeLimit;
      return this;
    }

    public Integer getMemoryLimit() {
      return memoryLimit;
    }

    public Builder setMemoryLimit(Integer memoryLimit) {
      this.memoryLimit = memoryLimit;
      return this;
    }

    public Integer getOutputLimit() {
      return outputLimit;
    }

    public Builder setOutputLimit(Integer outputLimit) {
      this.outputLimit = outputLimit;
      return this;
    }

    public Boolean getIsSpj() {
      return isSpj;
    }

    public Builder setIsSpj(Boolean isSpj) {
      this.isSpj = isSpj;
      return this;
    }

    public Integer getJavaTimeLimit() {
      return javaTimeLimit;
    }

    public Builder setJavaTimeLimit(Integer javaTimeLimit) {
      this.javaTimeLimit = javaTimeLimit;
      return this;
    }

    public Integer getJavaMemoryLimit() {
      return javaMemoryLimit;
    }

    public Builder setJavaMemoryLimit(Integer javaMemoryLimit) {
      this.javaMemoryLimit = javaMemoryLimit;
      return this;
    }

  }
}
