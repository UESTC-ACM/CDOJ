package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import java.util.Map;
import java.util.Objects;

/**
 * Dto for problem entity.
 */
@Fields({ "problemId", "title", "description", "input", "output", "sampleInput",
    "sampleOutput", "hint", "source", "timeLimit", "memoryLimit", "solved", "tried",
    "isSpj", "isVisible", "outputLimit", "dataCount",
    "difficulty", "type" })
public class ProblemDto implements BaseDto<Problem> {

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
  private Integer dataCount;
  private Integer difficulty;
  private ProblemType type;

  public ProblemDto() {
  }

  private ProblemDto(Integer problemId, String title, String description, String input,
      String output, String sampleInput, String sampleOutput, String hint, String source,
      Integer timeLimit, Integer memoryLimit, Integer solved, Integer tried, Boolean isSpj,
      Boolean isVisible, Integer outputLimit,
      Integer dataCount, Integer difficulty, ProblemType type) {
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
    this.dataCount = dataCount;
    this.difficulty = difficulty;
    this.type = type;
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

  public ProblemType getType() {
    return type;
  }

  public void setType(ProblemType type) {
    this.type = type;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProblemDto)) {
      return false;
    }

    ProblemDto that = (ProblemDto) o;
    return Objects.equals(this.dataCount, that.dataCount)
        && Objects.equals(this.description, that.description)
        && Objects.equals(this.difficulty, that.difficulty)
        && Objects.equals(this.hint, that.hint)
        && Objects.equals(this.input, that.input)
        && Objects.equals(this.isSpj, that.isSpj)
        && Objects.equals(this.isVisible, that.isVisible)
        && Objects.equals(this.memoryLimit, that.memoryLimit)
        && Objects.equals(this.output, that.output)
        && Objects.equals(this.outputLimit, that.outputLimit)
        && Objects.equals(this.problemId, that.problemId)
        && Objects.equals(this.sampleInput, that.sampleInput)
        && Objects.equals(this.sampleOutput, that.sampleOutput)
        && Objects.equals(this.solved, that.solved)
        && Objects.equals(this.source, that.source)
        && Objects.equals(this.timeLimit, that.timeLimit)
        && Objects.equals(this.title, that.title)
        && Objects.equals(this.tried, that.tried)
        && Objects.equals(this.type, that.type);
  }

  @Override
  public int hashCode() {
    int result = problemId != null ? problemId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (input != null ? input.hashCode() : 0);
    result = 31 * result + (output != null ? output.hashCode() : 0);
    result = 31 * result + (sampleInput != null ? sampleInput.hashCode() : 0);
    result = 31 * result + (sampleOutput != null ? sampleOutput.hashCode() : 0);
    result = 31 * result + (hint != null ? hint.hashCode() : 0);
    result = 31 * result + (source != null ? source.hashCode() : 0);
    result = 31 * result + (timeLimit != null ? timeLimit.hashCode() : 0);
    result = 31 * result + (memoryLimit != null ? memoryLimit.hashCode() : 0);
    result = 31 * result + (solved != null ? solved.hashCode() : 0);
    result = 31 * result + (tried != null ? tried.hashCode() : 0);
    result = 31 * result + (isSpj != null ? isSpj.hashCode() : 0);
    result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
    result = 31 * result + (outputLimit != null ? outputLimit.hashCode() : 0);
    result = 31 * result + (dataCount != null ? dataCount.hashCode() : 0);
    result = 31 * result + (difficulty != null ? difficulty.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builder for {@link ProblemDto}.
   */
  public static class Builder implements BaseDtoBuilder<ProblemDto> {

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
    private Integer memoryLimit = 64;
    private Integer solved = 0;
    private Integer tried = 0;
    private Boolean isSpj = false;
    private Boolean isVisible = true;
    private Integer outputLimit = 64;
    private Integer dataCount = 1;
    private Integer difficulty = 1;
    private ProblemType type = ProblemType.NORMAL;

    @Override
    public ProblemDto build() {
      return new ProblemDto(problemId, title, description, input, output, sampleInput,
          sampleOutput, hint, source, timeLimit, memoryLimit, solved, tried, isSpj, isVisible,
          outputLimit, dataCount, difficulty, type);
    }

    @Override
    public ProblemDto build(Map<String, Object> properties) {
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
      dataCount = (Integer) properties.get("dataCount");
      difficulty = (Integer) properties.get("difficulty");
      type = (ProblemType) properties.get("type");
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

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }

    public Builder setType(ProblemType type) {
      this.type = type;
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
