package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import java.util.*;
import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

@Fields({ "problemId", "timeLimit", "memoryLimit", "outputLimit", "isSpj", "javaTimeLimit",
    "javaMemoryLimit", "dataCount" })
public class ProblemDataShowDTO implements BaseDTO<Problem> {

  public ProblemDataShowDTO() {
  }

  private ProblemDataShowDTO(Integer problemId, Integer timeLimit, Integer memoryLimit,
                             Integer outputLimit, Boolean isSpj, Integer javaTimeLimit,
                             Integer javaMemoryLimit, Integer dataCount) {
    this.problemId = problemId;
    this.timeLimit = timeLimit;
    this.memoryLimit = memoryLimit;
    this.outputLimit = outputLimit;
    this.isSpj = isSpj;
    this.javaTimeLimit = javaTimeLimit;
    this.javaMemoryLimit = javaMemoryLimit;
    this.dataCount = dataCount;
  }

  private Integer problemId;
  private Integer timeLimit;
  private Integer memoryLimit;
  private Integer outputLimit;
  private Boolean isSpj;
  private Integer javaTimeLimit;
  private Integer javaMemoryLimit;
  private Integer dataCount;

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

  public Integer getDataCount() {
    return dataCount;
  }

  public void setDataCount(Integer dataCount) {
    this.dataCount = dataCount;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ProblemDataShowDTO> {

    private Builder() {
    }

    @Override
    public ProblemDataShowDTO build() {
      return new ProblemDataShowDTO(problemId, timeLimit, memoryLimit, outputLimit, isSpj,
          javaTimeLimit, javaMemoryLimit, dataCount);
    }

    @Override
    public ProblemDataShowDTO build(Map<String, Object> properties) {
      problemId = (Integer) properties.get("problemId");
      timeLimit = (Integer) properties.get("timeLimit");
      memoryLimit = (Integer) properties.get("memoryLimit");
      outputLimit = (Integer) properties.get("outputLimit");
      isSpj = (Boolean) properties.get("isSpj");
      javaTimeLimit = (Integer) properties.get("javaTimeLimit");
      javaMemoryLimit = (Integer) properties.get("javaMemoryLimit");
      dataCount = (Integer) properties.get("dataCount");
      return build();

    }

    private Integer problemId;
    private Integer timeLimit;
    private Integer memoryLimit;
    private Integer outputLimit;
    private Boolean isSpj;
    private Integer javaTimeLimit;
    private Integer javaMemoryLimit;
    private Integer dataCount;

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

    public Integer getDataCount() {
      return dataCount;
    }

    public Builder setDataCount(Integer dataCount) {
      this.dataCount = dataCount;
      return this;
    }
  }
}
