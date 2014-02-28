package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for problem entity.
 * <br/>
 * <code>@Fields({"problemId", "title", "description", "input", "output", "sampleInput",
 * "sampleOutput", "hint", "source", "timeLimit", "memoryLimit", "solved", "tried",
 * "isSpj", "isVisible", "outputLimit", "javaTimeLimit", "javaMemoryLimit", "dataCount",
 * "difficulty"})</code>
 */
@Fields({"problemId", "title", "description", "input", "output", "sampleInput",
    "sampleOutput", "hint", "source", "timeLimit", "memoryLimit", "solved", "tried",
    "isSpj", "isVisible", "outputLimit", "javaTimeLimit", "javaMemoryLimit", "dataCount",
    "difficulty"})
public class ProblemDTO implements BaseDTO<Problem> {

  private Integer problemId;
  private String title;
  private String description;
  private String input;
  private String output;
  private String sampleInput;
  private String sampleOutput;
  private String hint;
  private String source;
  private Integer timeLimit;
  private Integer memoryLimit;
  private Integer solved;
  private Integer tried;
  private Boolean isSpj;
  private Boolean isVisible;
  private Integer outputLimit;
  private Integer javaTimeLimit;
  private Integer javaMemoryLimit;
  private Integer dataCount;
  private Integer difficulty;

  public ProblemDTO() {
  }

  private ProblemDTO(Integer problemId, String title, String description, String input,
                     String output, String sampleInput, String sampleOutput, String hint, String source,
                     Integer timeLimit, Integer memoryLimit, Integer solved, Integer tried, Boolean isSpj,
                     Boolean isVisible, Integer outputLimit, Integer javaTimeLimit, Integer javaMemoryLimit,
                     Integer dataCount, Integer difficulty) {
    this.problemId = problemId;
    this.title = title;
    this.description = description;
    this.input = input;
    this.output = output;
    this.sampleInput = sampleInput;
    this.sampleOutput = sampleOutput;
    this.hint = hint;
    this.source = source;
    this.timeLimit = timeLimit;
    this.memoryLimit = memoryLimit;
    this.solved = solved;
    this.tried = tried;
    this.isSpj = isSpj;
    this.isVisible = isVisible;
    this.outputLimit = outputLimit;
    this.javaTimeLimit = javaTimeLimit;
    this.javaMemoryLimit = javaMemoryLimit;
    this.dataCount = dataCount;
    this.difficulty = difficulty;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    isVisible = visible;
  }

  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
  }

  public Integer getDataCount() {
    return dataCount;
  }

  public void setDataCount(Integer dataCount) {
    this.dataCount = dataCount;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
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

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
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

  /**
   * Builder for {@link ProblemDTO}.
   */
  public static class Builder implements BaseBuilder<ProblemDTO> {

    private Builder() {
    }

    private Integer problemId = 1000;
    private String title = "a + b";
    private String description = "Calculate a + b.";
    private String input = "1 2";
    private String output = "3";
    private String sampleInput = "1 2";
    private String sampleOutput = "3";
    private String hint = "Sample code is here.";
    private String source = "Classcal problem";
    private Integer timeLimit = 1000;
    private Integer memoryLimit = 65536;
    private Integer solved = 0;
    private Integer tried = 0;
    private Boolean isSpj = false;
    private Boolean isVisible = true;
    private Integer outputLimit = 8000;
    private Integer javaTimeLimit = 3000;
    private Integer javaMemoryLimit = 65536;
    private Integer dataCount = 1;
    private Integer difficulty = 1;

    @Override
    public ProblemDTO build() {
      return new ProblemDTO(problemId, title, description, input, output, sampleInput,
          sampleOutput, hint, source, timeLimit, memoryLimit, solved, tried, isSpj, isVisible,
          outputLimit, javaTimeLimit, javaMemoryLimit, dataCount, difficulty);
    }

    @Override
    public ProblemDTO build(Map<String, Object> properties) {
      problemId = (Integer) properties.get("problemId");
      title = (String) properties.get("title");
      description = (String) properties.get("description");
      input = (String) properties.get("input");
      output = (String) properties.get("output");
      sampleInput = (String) properties.get("sampleInput");
      sampleOutput = (String) properties.get("sampleOutput");
      hint = (String) properties.get("hint");
      source = (String) properties.get("source");
      timeLimit = (Integer) properties.get("timeLimit");
      memoryLimit = (Integer) properties.get("memoryLimit");
      solved = (Integer) properties.get("solved");
      tried = (Integer) properties.get("tried");
      isSpj = (Boolean) properties.get("isSpj");
      isVisible = (Boolean) properties.get("isVisible");
      outputLimit = (Integer) properties.get("outputLimit");
      javaTimeLimit = (Integer) properties.get("javaTimeLimit");
      javaMemoryLimit = (Integer) properties.get("javaMemoryLimit");
      dataCount = (Integer) properties.get("dataCount");
      difficulty = (Integer) properties.get("difficulty");
      return build();
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
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

    public Builder setTimeLimit(Integer timeLimit) {
      this.timeLimit = timeLimit;
      return this;
    }

    public Builder setMemoryLimit(Integer memoryLimit) {
      this.memoryLimit = memoryLimit;
      return this;
    }

    public Builder setSolved(Integer integer) {
      this.solved = integer;
      return this;
    }

    public Builder setTried(Integer integer) {
      this.tried = integer;
      return this;
    }

    public Builder setIsSpj(Boolean isSpj) {
      this.isSpj = isSpj;
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

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }

    public Builder setOutputLimit(Integer outputLimit) {
      this.outputLimit = outputLimit;
      return this;
    }

    public Builder setDataCount(Integer dataCount) {
      this.dataCount = dataCount;
      return this;
    }

    public Builder setDifficulty(Integer difficulty) {
      this.difficulty = difficulty;
      return this;
    }
  }
}
