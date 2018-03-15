package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import cn.edu.uestc.acmicpc.util.enums.ProblemType;
import java.util.Objects;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

/**
 * Dto post from problem editor.
 */
public class ProblemEditDto {

  public ProblemEditDto() {
  }

  private String action;

  private Integer problemId;

  private String description;

  @NotNull(message = "Please enter a validate title.")
  @Length(min = 1, max = 50, message = "Please enter a validate title.")
  private String title;

  private Boolean isSpj;

  @NotNull(message = "Time limit should between 0 and 60000")
  @Range(min = 0, max = 60000, message = "Time limit should between 0 and 60000")
  private Integer timeLimit;

  @NotNull(message = "Memory limit should between 0 and 512")
  @Range(min = 0, max = 512, message = "Memory limit should between 0 and 512")
  private Integer memoryLimit;

  @NotNull(message = "Output limit should between 0 and 512")
  @Range(min = 0, max = 512, message = "Output limit should between 0 and 512")
  private Integer outputLimit;

  private Boolean isVisible;

  private String input;

  private String output;

  private String sampleInput;

  private String sampleOutput;

  private String hint;

  private String source;

  private ProblemType type;

  public ProblemEditDto(String action, Integer problemId, String description, String title,
      Boolean isSpj, Integer timeLimit, Integer memoryLimit,
      Integer outputLimit, Boolean isVisible, String input, String output,
      String sampleInput, String sampleOutput, String hint, String source, ProblemType type) {
    this.action = action;
    this.problemId = problemId;
    this.description = description;
    this.title = title;
    this.isSpj = isSpj;
    this.timeLimit = timeLimit;
    this.memoryLimit = memoryLimit;
    this.outputLimit = outputLimit;
    this.isVisible = isVisible;
    this.input = input;
    this.output = output;
    this.sampleInput = sampleInput;
    this.sampleOutput = sampleOutput;
    this.hint = hint;
    this.source = source;
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ProblemEditDto)) {
      return false;
    }

    ProblemEditDto that = (ProblemEditDto) o;
    return Objects.equals(this.action, that.action)
        && Objects.equals(this.description, that.description)
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
        && Objects.equals(this.source, that.source)
        && Objects.equals(this.timeLimit, that.timeLimit)
        && Objects.equals(this.title, that.title)
        && Objects.equals(this.type, that.type);
  }

  @Override
  public int hashCode() {
    int result = action != null ? action.hashCode() : 0;
    result = 31 * result + (problemId != null ? problemId.hashCode() : 0);
    result = 31 * result + (description != null ? description.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (isSpj != null ? isSpj.hashCode() : 0);
    result = 31 * result + (timeLimit != null ? timeLimit.hashCode() : 0);
    result = 31 * result + (memoryLimit != null ? memoryLimit.hashCode() : 0);
    result = 31 * result + (outputLimit != null ? outputLimit.hashCode() : 0);
    result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
    result = 31 * result + (input != null ? input.hashCode() : 0);
    result = 31 * result + (output != null ? output.hashCode() : 0);
    result = 31 * result + (sampleInput != null ? sampleInput.hashCode() : 0);
    result = 31 * result + (sampleOutput != null ? sampleOutput.hashCode() : 0);
    result = 31 * result + (hint != null ? hint.hashCode() : 0);
    result = 31 * result + (source != null ? source.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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
}
