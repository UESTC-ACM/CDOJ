package cn.edu.uestc.acmicpc.db.dto.impl.status;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * Dto for code and compile information query. <br/>
 * <code>@Fields({ "statusId", "userId", "codeByCodeId.content", "compileInfoId", "contestId", "problemId" })</code>
 */
@Fields({ "statusId", "userId", "codeByCodeId.content", "compileInfoId",
    "contestId", "problemId", "languageByLanguageId.extension",
    "userByUserId.userName", "time" })
public class StatusInformationDto implements BaseDto<Status> {

  public StatusInformationDto() {
  }

  private StatusInformationDto(Integer statusId, Integer userId, String codeContent,
      Integer compileInfoId, Integer contestId, Integer problemId,
      String extension, String userName, Timestamp time) {
    this.statusId = statusId;
    this.userId = userId;
    this.codeContent = codeContent;
    this.compileInfoId = compileInfoId;
    this.contestId = contestId;
    this.problemId = problemId;
    this.extension = extension;
    this.userName = userName;
    this.time = time;
  }

  private Integer statusId;
  private Integer userId;
  private String codeContent;
  private Integer compileInfoId;
  private Integer contestId;
  private Integer problemId;
  private String extension;
  private String userName;
  private Timestamp time;

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
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

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getCodeContent() {
    return codeContent;
  }

  public void setCodeContent(String codeContent) {
    this.codeContent = codeContent;
  }

  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StatusInformationDto)) {
      return false;
    }

    StatusInformationDto that = (StatusInformationDto) o;
    return Objects.equals(this.codeContent, that.codeContent)
        && Objects.equals(this.compileInfoId, that.compileInfoId)
        && Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.extension, that.extension)
        && Objects.equals(this.problemId, that.problemId)
        && Objects.equals(this.statusId, that.statusId)
        && Objects.equals(this.time, that.time)
        && Objects.equals(this.userId, that.userId)
        && Objects.equals(this.userName, that.userName);
  }

  @Override
  public int hashCode() {
    int result = statusId != null ? statusId.hashCode() : 0;
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (codeContent != null ? codeContent.hashCode() : 0);
    result = 31 * result + (compileInfoId != null ? compileInfoId.hashCode() : 0);
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    result = 31 * result + (problemId != null ? problemId.hashCode() : 0);
    result = 31 * result + (extension != null ? extension.hashCode() : 0);
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<StatusInformationDto> {

    private Builder() {
    }

    @Override
    public StatusInformationDto build() {
      return new StatusInformationDto(statusId, userId, codeContent, compileInfoId,
          contestId, problemId, extension, userName, time);
    }

    @Override
    public StatusInformationDto build(Map<String, Object> properties) {
      statusId = (Integer) properties.get("statusId");
      userId = (Integer) properties.get("userId");
      codeContent = (String) properties.get("codeByCodeId.content");
      compileInfoId = (Integer) properties.get("compileInfoId");
      contestId = (Integer) properties.get("contestId");
      problemId = (Integer) properties.get("problemId");
      extension = (String) properties.get("languageByLanguageId.extension");
      userName = (String) properties.get("userByUserId.userName");
      time = (Timestamp) properties.get("time");
      return build();
    }

    private Integer statusId;
    private Integer userId;
    private String codeContent;
    private Integer compileInfoId;
    private Integer contestId;
    private Integer problemId;
    private String extension;
    private String userName;
    private Timestamp time;

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder setExtension(String extension) {
      this.extension = extension;
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

    public Builder setStatusId(Integer statusId) {
      this.statusId = statusId;
      return this;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setCodeContent(String codeContent) {
      this.codeContent = codeContent;
      return this;
    }

    public Builder setCompileInfoId(Integer compileInfoId) {
      this.compileInfoId = compileInfoId;
      return this;
    }
  }
}
