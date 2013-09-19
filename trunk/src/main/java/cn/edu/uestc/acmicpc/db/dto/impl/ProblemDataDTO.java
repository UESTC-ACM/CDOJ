package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link Problem} data.
 */
public class ProblemDataDTO extends BaseDTO<Problem> {

  private Integer problemId;
  private Integer timeLimit;
  private Integer memoryLimit;
  private Boolean isSpj;
  private Integer outputLimit;
  private Integer javaTimeLimit;
  private Integer javaMemoryLimit;

  public ProblemDataDTO() {
  }

  private ProblemDataDTO(Integer problemId, Integer timeLimit, Integer memoryLimit,
      Boolean isSpj, Integer outputLimit, Integer javaTimeLimit, Integer javaMemoryLimit) {
    this.problemId = problemId;
    this.timeLimit = timeLimit;
    this.memoryLimit = memoryLimit;
    this.isSpj = isSpj;
    this.outputLimit = outputLimit;
    this.javaTimeLimit = javaTimeLimit;
    this.javaMemoryLimit = javaMemoryLimit;
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

  @Ignore
  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean spj) {
    isSpj = spj;
  }

  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
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

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  @Override
  @Deprecated
  public Problem getEntity() throws AppException {
    Problem problem = super.getEntity();
    problem.setTimeLimit(1000);
    problem.setJavaTimeLimit(3000);
    problem.setMemoryLimit(65535);
    problem.setJavaMemoryLimit(65535);
    problem.setOutputLimit(8192);
    problem.setSolved(0);
    problem.setTried(0);
    problem.setDataCount(0);
    problem.setIsSpj(false);
    problem.setIsVisible(false);
    problem.setProblemId(null);
    return problem;
  }

  @Override
  public void updateEntity(Problem problem) throws AppException {
    super.updateEntity(problem);
    if (getIsSpj() != null) {
      problem.setIsSpj(getIsSpj());
    }
  }

  @Override
  protected Class<Problem> getReferenceClass() {
    return Problem.class;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link ProblemDataDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer problemId;
    private Integer timeLimit = 1000;
    private Integer memoryLimit = 65535;
    private Boolean isSpj = false;
    private Integer outputLimit = 8000;
    private Integer javaTimeLimit = 3000;
    private Integer javaMemoryLimit = 65535;

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Builder setTimeLimit(Integer timeLimit) {
      this.timeLimit = timeLimit;
      return this;
    }

    public Builder setMemoryLimit(Integer memoryLimit) {
      this.memoryLimit = memoryLimit;
      return this;
    }

    public Builder setIsSpj(Boolean isSpj) {
      this.isSpj = isSpj;
      return this;
    }

    public Builder setOutputLimit(Integer outputLimit) {
      this.outputLimit = outputLimit;
      return this;
    }

    public Builder setJavaTimeLimit(Integer javaTimeLimit) {
      this.javaTimeLimit = javaTimeLimit;
      return this;
    }

    public Builder setJavaMemoryLimit(Integer javaMemoryLimit) {
      this.javaMemoryLimit = javaMemoryLimit;
      return this;
    }

    public ProblemDataDTO build() {
      return new ProblemDataDTO(problemId, timeLimit, memoryLimit, isSpj, outputLimit,
          javaTimeLimit, javaMemoryLimit);
    }
  }
}
