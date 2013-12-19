package cn.edu.uestc.acmicpc.db.dto.impl.problem;

import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

/**
 * DTO used in problem editor.
 * <br/>
 * <code>@Fields({ "problemId", "title", "description", "input", "output", "sampleInput",
 * "sampleOutput", "hint", "source" }) </code>
 */
@Fields({ "problemId", "title", "description", "input", "output",
    "sampleInput", "sampleOutput", "hint", "source" })
public class ProblemEditorShowDTO implements BaseDTO<Problem> {

  public ProblemEditorShowDTO() {
  }

  private ProblemEditorShowDTO(Integer problemId, String title,
      String description, String input, String output, String sampleInput,
      String sampleOutput, String hint, String source) {
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

  private Integer problemId;
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

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
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

  public static class Builder implements BaseBuilder<ProblemEditorShowDTO> {

    private Builder() {
    }

    @Override
    public ProblemEditorShowDTO build() {
      return new ProblemEditorShowDTO(problemId, title, description, input,
          output, sampleInput, sampleOutput, hint, source);
    }

    @Override
    public ProblemEditorShowDTO build(Map<String, Object> properties) {
      problemId = (Integer) properties.get("problemId");
      title = (String) properties.get("title");
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
