package cn.edu.uestc.acmicpc.db.dto.impl.problem;


/**
 * Problem edit data transfer object
 */
public class ProblemEditDTO {

  private Integer problemId;

  /**
   * "new" or "edit"
   */
  private String action;

  private String title;
  private String description;
  private String input;
  private String output;
  private String sampleInput;
  private String sampleOutput;
  private String hint;
  private String source;

  public ProblemEditDTO() {
  }

  private ProblemEditDTO(Integer problemId, String action,
      String title, String description, String input,
      String output, String sampleInput, String sampleOutput,
      String hint, String source) {
    this.problemId = problemId;
    this.action = action;
    this.title = title;
    this.description = description;
    this.input = input;
    this.output = output;
    this.sampleInput = sampleInput;
    this.sampleOutput = sampleOutput;
    this.hint = hint;
    this.source = source;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public ProblemEditDTO build() {
      return new ProblemEditDTO(problemId, action, title, description, input, output,
          sampleInput, sampleOutput, hint, source);
    }

    private Integer problemId;
    private String action;
    private String title;
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

    public String getAction() {
      return action;
    }

    public Builder setAction(String action) {
      this.action = action;
      return this;
    }

    public String getTitle() {
      return title;
    }

    public Builder setTitle(String title) {
      this.title = title;
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
