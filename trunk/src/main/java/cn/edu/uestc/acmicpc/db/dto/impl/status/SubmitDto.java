package cn.edu.uestc.acmicpc.db.dto.impl.status;

import org.hibernate.validator.constraints.Length;

import java.util.Objects;

import javax.validation.constraints.NotNull;

/**
 * Dto post from submit form.
 */
public class SubmitDto {

  @NotNull(message = "Code length should at least 50B and at most 65536B")
  @Length(min = 50, max = 65535, message = "Code length should at least 50B and at most 65536B")
  private String codeContent;

  private Integer problemId;

  private Integer contestId;

  private Integer languageId;

  public SubmitDto() {
  }

  public SubmitDto(String codeContent, Integer problemId, Integer contestId, Integer languageId) {
    this.codeContent = codeContent;
    this.problemId = problemId;
    this.contestId = contestId;
    this.languageId = languageId;
  }

  public String getCodeContent() {
    return codeContent;
  }

  public void setCodeContent(String codeContent) {
    this.codeContent = codeContent;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public Integer getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof SubmitDto)) {
      return false;
    }

    SubmitDto that = (SubmitDto) o;
    return Objects.equals(this.codeContent, that.codeContent)
        && Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.languageId, that.languageId)
        && Objects.equals(this.problemId, that.problemId);
  }

  @Override
  public int hashCode() {
    int result = codeContent != null ? codeContent.hashCode() : 0;
    result = 31 * result + (problemId != null ? problemId.hashCode() : 0);
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    result = 31 * result + (languageId != null ? languageId.hashCode() : 0);
    return result;
  }

  public Builder builder() {
    return new Builder();
  }

  public static class Builder {
    private String codeContent;

    private Integer problemId;

    private Integer contestId;

    private Integer languageId;

    public SubmitDto build() {
      return new SubmitDto(codeContent, problemId, contestId, languageId);
    }

    public Builder setCodeContent(String codeContent) {
      this.codeContent = codeContent;
      return this;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Builder setLanguageId(Integer languageId) {
      this.languageId = languageId;
      return this;
    }
  }
}
