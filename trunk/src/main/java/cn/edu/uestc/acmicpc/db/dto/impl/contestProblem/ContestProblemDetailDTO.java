package cn.edu.uestc.acmicpc.db.dto.impl.contestProblem;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for contest problem entity.
 * <br/>
 * <code>@Fields({ "order", "problemId", "problemByProblemId.title",
 * "problemByProblemId.isSpj",
 * "problemByProblemId.javaTimeLimit", "problemByProblemId.javaMemoryLimit",
 * "problemByProblemId.timeLimit", "problemByProblemId.memoryLimit",
 * "problemByProblemId.description", "problemByProblemId.input",
 * "problemByProblemId.output",
 * "problemByProblemId.sampleInput", "problemByProblemId.sampleOutput",
 * "problemByProblemId.hint",
 * "problemByProblemId.source" })</code>
 */
@Fields({"order", "problemId", "problemByProblemId.title",
    "problemByProblemId.isSpj",
    "problemByProblemId.javaTimeLimit", "problemByProblemId.javaMemoryLimit",
    "problemByProblemId.timeLimit", "problemByProblemId.memoryLimit",
    "problemByProblemId.description", "problemByProblemId.input",
    "problemByProblemId.output",
    "problemByProblemId.sampleInput", "problemByProblemId.sampleOutput",
    "problemByProblemId.hint",
    "problemByProblemId.source"})
public class ContestProblemDetailDTO implements BaseDTO<ContestProblem> {

  private Integer order;
  private Character orderCharacter;
  private Integer problemId;
  private String title;
  private Boolean isSpj;
  private Integer solved;
  private Integer tried;
  private Integer javaTimeLimit;
  private Integer javaMemoryLimit;
  private Integer timeLimit;
  private Integer memoryLimit;
  private String description;
  private String input;
  private String output;
  private String sampleInput;
  private String sampleOutput;
  private String hint;
  private String source;

  public ContestProblemDetailDTO() {

  }

  public ContestProblemDetailDTO(Integer order, Character orderCharacter,
                                 Integer problemId, String title, Boolean isSpj, Integer solved,
                                 Integer tried, Integer javaTimeLimit, Integer javaMemoryLimit,
                                 Integer timeLimit,
                                 Integer memoryLimit, String description, String input, String output,
                                 String sampleInput, String sampleOutput, String hint, String source) {

    this.order = order;
    this.orderCharacter = orderCharacter;
    this.problemId = problemId;
    this.title = title;
    this.isSpj = isSpj;
    this.solved = solved;
    this.tried = tried;
    this.javaTimeLimit = javaTimeLimit;
    this.javaMemoryLimit = javaMemoryLimit;
    this.timeLimit = timeLimit;
    this.memoryLimit = memoryLimit;
    this.description = description;
    this.input = input;
    this.output = output;
    this.sampleInput = sampleInput;
    this.sampleOutput = sampleOutput;
    this.hint = hint;
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

  public Character getOrderCharacter() {
    return orderCharacter;
  }

  public void setOrderCharacter(Character orderCharacter) {
    this.orderCharacter = orderCharacter;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean isSpj) {
    this.isSpj = isSpj;
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

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public String getSampleInput() {
    return sampleInput;
  }

  public void setSampleInput(String sampleInput) {
    this.sampleInput = sampleInput;
  }

  public String getSampleOutput() {
    return sampleOutput;
  }

  public void setSampleOutput(String sampleOutput) {
    this.sampleOutput = sampleOutput;
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestProblemDetailDTO> {

    @Override
    public ContestProblemDetailDTO build() {
      return new ContestProblemDetailDTO(order, orderCharacter, problemId, title, isSpj, solved,
          tried,
          javaTimeLimit,
          javaMemoryLimit, timeLimit, memoryLimit, description, input, output,
          sampleInput, sampleOutput, hint, source);
    }

    @Override
    public ContestProblemDetailDTO build(Map<String, Object> properties) {
      order = (Integer) properties.get("order");
      orderCharacter = (char) ('A' + order);
      problemId = (Integer) properties.get("problemId");
      title = (String) properties.get("problemByProblemId.title");
      isSpj = (Boolean) properties.get("problemByProblemId.isSpj");
      solved = 0;
      tried = 0;
      javaTimeLimit = (Integer) properties
          .get("problemByProblemId.javaTimeLimit");
      javaMemoryLimit = (Integer) properties
          .get("problemByProblemId.javaMemoryLimit");
      timeLimit = (Integer) properties.get("problemByProblemId.timeLimit");
      memoryLimit = (Integer) properties.get("problemByProblemId.memoryLimit");
      description = (String) properties.get("problemByProblemId.description");
      input = (String) properties.get("problemByProblemId.input");
      output = (String) properties.get("problemByProblemId.output");
      sampleInput = (String) properties.get("problemByProblemId.sampleInput");
      sampleOutput = (String) properties.get("problemByProblemId.sampleOutput");
      hint = (String) properties.get("problemByProblemId.hint");
      source = (String) properties.get("problemByProblemId.source");
      return build();
    }

    private Integer order;
    private Character orderCharacter;
    private Integer problemId;
    private String title;
    private Boolean isSpj;
    private Integer solved;
    private Integer tried;
    private Integer javaTimeLimit;
    private Integer javaMemoryLimit;
    private Integer timeLimit;
    private Integer memoryLimit;
    private String description;
    private String input;
    private String output;
    private String sampleInput;
    private String sampleOutput;
    private String hint;
    private String source;

    public Builder setOrder(Integer order) {
      this.order = order;
      return this;
    }

    public Builder setOrderCharacter(Character orderCharacter) {
      this.orderCharacter = orderCharacter;
      return this;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setIsSpj(Boolean isSpj) {
      this.isSpj = isSpj;
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

    public Builder setJavaTimeLimit(Integer javaTimeLimit) {
      this.javaTimeLimit = javaTimeLimit;
      return this;
    }

    public Builder setJavaMemoryLimit(Integer javaMemoryLimit) {
      this.javaMemoryLimit = javaMemoryLimit;
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

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public Builder setInput(String input) {
      this.input = input;
      return this;
    }

    public Builder setOutput(String output) {
      this.output = output;
      return this;
    }

    public Builder setSampleInput(String sampleInput) {
      this.sampleInput = sampleInput;
      return this;
    }

    public Builder setSampleOutput(String sampleOutput) {
      this.sampleOutput = sampleOutput;
      return this;
    }

    public Builder setHint(String hint) {
      this.hint = hint;
      return this;
    }

    public Builder setSource(String source) {
      this.source = source;
      return this;
    }
  }
}