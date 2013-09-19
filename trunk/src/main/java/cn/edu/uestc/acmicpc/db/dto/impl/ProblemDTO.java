package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link Problem}.
 */
public class ProblemDTO extends BaseDTO<Problem> {

  private Integer problemId;
  private String title;
  private String description;
  private String input;
  private String output;
  private String sampleInput;
  private String sampleOutput;
  private String hint;
  private String source;

  public ProblemDTO() {
  }

  private ProblemDTO(Integer problemId, String title, String description, String input,
      String output, String sampleInput, String sampleOutput, String hint, String source) {
    this.problemId = problemId;
    this.title = title;
    this.description = description;
    this.input = input;
    this.output = output;
    this.sampleInput = sampleInput;
    this.sampleOutput = sampleOutput;
    this.hint = hint;
    this.source = source;
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

  @Override
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
    problem.setDifficulty(1);
    return problem;
  }

  @Override
  public void updateEntity(Problem problem) throws AppException {
    super.updateEntity(problem);
  }

  @Override
  protected Class<Problem> getReferenceClass() {
    return Problem.class;
  }

  public static Builder bulder() {
    return new Builder();
  }

  /** Builder for {@link ProblemDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer problemId;
    private String title = "";
    private String description = "";
    private String input = "";
    private String output = "";
    private String sampleInput = "";
    private String sampleOutput = "";
    private String hint = "";
    private String source = "";

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

    public ProblemDTO build() {
      return new ProblemDTO(problemId, title, description, input, output, sampleInput,
          sampleOutput, hint, source);
    }
  }
}
