package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

/**
 * DTO used in problem page.
 * <br/>
 * <code>@Fields({ "problemId", "title", "isSpj", "timeLimit", "javaTimeLimit", "memoryLimit",
 *  "javaMemoryLimit", "solved", "tried", "description", "input", "output", "sampleInput",
 *  "sampleOutput", "hint", "source" })</code>
 */
@Fields({ "problemId", "title", "isSpj", "timeLimit", "javaTimeLimit", "memoryLimit",
    "javaMemoryLimit", "solved", "tried", "description", "input", "output", "sampleInput",
    "sampleOutput", "hint", "source" })
public class ProblemShowDTO implements BaseDTO<Problem> {

  public ProblemShowDTO() {
  }

  private ProblemShowDTO(Integer problemId, String title, Boolean isSpj, Integer timeLimit,
                         Integer javaTimeLimit, Integer memoryLimit, Integer javaMemoryLimit,
                         Integer solved, Integer tried, String description, String input,
                         String output, String sampleInput, String sampleOutput, String hint,
                         String source) {
    this.problemId = problemId;
    this.title = title;
    this.isSpj = isSpj;
    this.timeLimit = timeLimit;
    this.javaTimeLimit = javaTimeLimit;
    this.memoryLimit = memoryLimit;
    this.javaMemoryLimit = javaMemoryLimit;
    this.solved = solved;
    this.tried = tried;
    this.description = description;
    this.input = input;
    this.output = output;
    this.sampleInput = sampleInput;
    this.sampleOutput = sampleOutput;
    this.hint = hint;
    this.source = source;
  }

  private Integer problemId;
  private String title;
  private Boolean isSpj;
  private Integer timeLimit;
  private Integer javaTimeLimit;
  private Integer memoryLimit;
  private Integer javaMemoryLimit;
  private Integer solved;
  private Integer tried;
  private String description;
  private String input;
  private String output;
  private String sampleInput;
  private String sampleOutput;
  private String hint;
  private String source;

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

  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean isSpj) {
    this.isSpj = isSpj;
  }

  public Integer getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(Integer timeLimit) {
    this.timeLimit = timeLimit;
  }

  public Integer getJavaTimeLimit() {
    return javaTimeLimit;
  }

  public void setJavaTimeLimit(Integer javaTimeLimit) {
    this.javaTimeLimit = javaTimeLimit;
  }

  public Integer getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(Integer memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  public Integer getJavaMemoryLimit() {
    return javaMemoryLimit;
  }

  public void setJavaMemoryLimit(Integer javaMemoryLimit) {
    this.javaMemoryLimit = javaMemoryLimit;
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

  public static class Builder implements BaseBuilder<ProblemShowDTO> {

    private Builder() {
    }

    @Override
    public ProblemShowDTO build() {
      return new ProblemShowDTO(problemId, title, isSpj, timeLimit, javaTimeLimit, memoryLimit,
          javaMemoryLimit, solved, tried, description, input, output, sampleInput, sampleOutput,
          hint, source);
    }

    @Override
    public ProblemShowDTO build(Map<String, Object> properties) {
      problemId = (Integer) properties.get("problemId");
      title = (String) properties.get("title");
      isSpj = (Boolean) properties.get("isSpj");
      timeLimit = (Integer) properties.get("timeLimit");
      javaTimeLimit = (Integer) properties.get("javaTimeLimit");
      memoryLimit = (Integer) properties.get("memoryLimit");
      javaMemoryLimit = (Integer) properties.get("javaMemoryLimit");
      solved = (Integer) properties.get("solved");
      tried = (Integer) properties.get("tried");
      description = (String) properties.get("description");
      input = (String) properties.get("input");
      output = (String) properties.get("output");
      sampleInput = (String) properties.get("sampleInput");
      sampleOutput = (String) properties.get("sampleOutput");
      hint = (String) properties.get("hint");
      source = (String) properties.get("source");
      return build();

    }

    private Integer problemId;
    private String title;
    private Boolean isSpj;
    private Integer timeLimit;
    private Integer javaTimeLimit;
    private Integer memoryLimit;
    private Integer javaMemoryLimit;
    private Integer solved;
    private Integer tried;
    private String description;
    private String input;
    private String output;
    private String sampleInput;
    private String sampleOutput;
    private String hint;
    private String source;

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public String getTitle() {
      return title;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Boolean getIsSpj() {
      return isSpj;
    }

    public Builder setIsSpj(Boolean isSpj) {
      this.isSpj = isSpj;
      return this;
    }

    public Integer getTimeLimit() {
      return timeLimit;
    }

    public Builder setTimeLimit(Integer timeLimit) {
      this.timeLimit = timeLimit;
      return this;
    }

    public Integer getJavaTimeLimit() {
      return javaTimeLimit;
    }

    public Builder setJavaTimeLimit(Integer javaTimeLimit) {
      this.javaTimeLimit = javaTimeLimit;
      return this;
    }

    public Integer getMemoryLimit() {
      return memoryLimit;
    }

    public Builder setMemoryLimit(Integer memoryLimit) {
      this.memoryLimit = memoryLimit;
      return this;
    }

    public Integer getJavaMemoryLimit() {
      return javaMemoryLimit;
    }

    public Builder setJavaMemoryLimit(Integer javaMemoryLimit) {
      this.javaMemoryLimit = javaMemoryLimit;
      return this;
    }

    public Integer getSolved() {
      return solved;
    }

    public Builder setSolved(Integer solved) {
      this.solved = solved;
      return this;
    }

    public Integer getTried() {
      return tried;
    }

    public Builder setTried(Integer tried) {
      this.tried = tried;
      return this;
    }

    public String getDescription() {
      return description;
    }

    public Builder setDescription(String description) {
      this.description = description;
      return this;
    }

    public String getInput() {
      return input;
    }

    public Builder setInput(String input) {
      this.input = input;
      return this;
    }

    public String getOutput() {
      return output;
    }

    public Builder setOutput(String output) {
      this.output = output;
      return this;
    }

    public String getSampleInput() {
      return sampleInput;
    }

    public Builder setSampleInput(String sampleInput) {
      this.sampleInput = sampleInput;
      return this;
    }

    public String getSampleOutput() {
      return sampleOutput;
    }

    public Builder setSampleOutput(String sampleOutput) {
      this.sampleOutput = sampleOutput;
      return this;
    }

    public String getHint() {
      return hint;
    }

    public Builder setHint(String hint) {
      this.hint = hint;
      return this;
    }

    public String getSource() {
      return source;
    }

    public Builder setSource(String source) {
      this.source = source;
      return this;
    }
  }
}
