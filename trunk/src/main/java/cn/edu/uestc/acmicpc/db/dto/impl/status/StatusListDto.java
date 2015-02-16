package cn.edu.uestc.acmicpc.db.dto.impl.status;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * Dto used in status list.
 */
@Fields({ "statusId", "userByUserId.userName", "userByUserId.nickName", "problemId", "contestId",
    "result", "length", "languageByLanguageId.name", "timeCost", "memoryCost", "time",
    "caseNumber",
    "userByUserId.email", "userByUserId.name" })
public class StatusListDto implements BaseDto<Status> {

  private String nickName;

  public StatusListDto() {
  }

  private StatusListDto(Integer statusId, String userName, String nickName, Integer problemId,
      Integer contestId, String returnType, Integer returnTypeId, Integer length,
      String language, Integer timeCost, Integer memoryCost, Timestamp time,
      Integer caseNumber, String email, String name) {
    this.statusId = statusId;
    this.userName = userName;
    this.nickName = nickName;
    this.problemId = problemId;
    this.contestId = contestId;
    this.returnType = returnType;
    this.returnTypeId = returnTypeId;
    this.length = length;
    this.language = language;
    this.timeCost = timeCost;
    this.memoryCost = memoryCost;
    this.time = time;
    this.caseNumber = caseNumber;
    this.email = email;
    this.name = name;
  }

  private String name;
  private Integer statusId;
  private String userName;
  private Integer problemId;
  private Integer contestId;
  private String returnType;
  private Integer returnTypeId;
  private Integer length;
  private String language;
  private Integer timeCost;
  private Integer memoryCost;
  private Timestamp time;
  private Integer caseNumber;
  private String email;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
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

  public String getReturnType() {
    return returnType;
  }

  public void setReturnType(String returnType) {
    this.returnType = returnType;
  }

  public Integer getReturnTypeId() {
    return returnTypeId;
  }

  public void setReturnTypeId(Integer returnTypeId) {
    this.returnTypeId = returnTypeId;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public Integer getTimeCost() {
    return timeCost;
  }

  public void setTimeCost(Integer timeCost) {
    this.timeCost = timeCost;
  }

  public Integer getMemoryCost() {
    return memoryCost;
  }

  public void setMemoryCost(Integer memoryCost) {
    this.memoryCost = memoryCost;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Integer getCaseNumber() {
    return caseNumber;
  }

  public void setCaseNumber(Integer caseNumber) {
    this.caseNumber = caseNumber;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StatusListDto)) {
      return false;
    }

    StatusListDto that = (StatusListDto) o;
    return Objects.equals(this.caseNumber, that.caseNumber)
        && Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.language, that.language)
        && Objects.equals(this.length, that.length)
        && Objects.equals(this.memoryCost, that.memoryCost)
        && Objects.equals(this.nickName, that.nickName)
        && Objects.equals(this.problemId, that.problemId)
        && Objects.equals(this.returnType, that.returnType)
        && Objects.equals(this.returnTypeId, that.returnTypeId)
        && Objects.equals(this.statusId, that.statusId)
        && Objects.equals(this.time, that.time)
        && Objects.equals(this.timeCost, that.timeCost)
        && Objects.equals(this.userName, that.userName)
        && Objects.equals(this.email, that.email);
  }

  @Override
  public int hashCode() {
    int result = nickName != null ? nickName.hashCode() : 0;
    result = 31 * result + (statusId != null ? statusId.hashCode() : 0);
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (problemId != null ? problemId.hashCode() : 0);
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    result = 31 * result + (returnType != null ? returnType.hashCode() : 0);
    result = 31 * result + (returnTypeId != null ? returnTypeId.hashCode() : 0);
    result = 31 * result + (length != null ? length.hashCode() : 0);
    result = 31 * result + (language != null ? language.hashCode() : 0);
    result = 31 * result + (timeCost != null ? timeCost.hashCode() : 0);
    result = 31 * result + (memoryCost != null ? memoryCost.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (caseNumber != null ? caseNumber.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<StatusListDto> {

    private String nickName;

    private Builder() {
    }

    @Override
    public StatusListDto build() {
      return new StatusListDto(statusId, userName, nickName, problemId, contestId, returnType,
          returnTypeId, length, language, timeCost, memoryCost, time, caseNumber, email, name);
    }

    @Override
    public StatusListDto build(Map<String, Object> properties) {
      statusId = (Integer) properties.get("statusId");
      userName = (String) properties.get("userByUserId.userName");
      nickName = (String) properties.get("userByUserId.nickName");
      problemId = (Integer) properties.get("problemId");
      contestId = (Integer) properties.get("contestId");
      returnTypeId = (Integer) properties.get("result");
      length = (Integer) properties.get("length");
      language = (String) properties.get("languageByLanguageId.name");
      timeCost = (Integer) properties.get("timeCost");
      memoryCost = (Integer) properties.get("memoryCost");
      time = (Timestamp) properties.get("time");
      caseNumber = (Integer) properties.get("caseNumber");
      email = (String) properties.get("userByUserId.email");
      name = (String) properties.get("userByUserId.name");
      return build();

    }

    private String name;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    private Integer statusId;
    private String userName;
    private Integer problemId;
    private Integer contestId;
    private String returnType;
    private Integer returnTypeId;
    private Integer length;
    private String language;
    private Integer timeCost;
    private Integer memoryCost;
    private Timestamp time;
    private Integer caseNumber;
    private String email;

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setNickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    public Builder setStatusId(Integer statusId) {
      this.statusId = statusId;
      return this;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
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

    public Builder setReturnType(String returnType) {
      this.returnType = returnType;
      return this;
    }

    public Builder setReturnTypeId(Integer returnTypeId) {
      this.returnTypeId = returnTypeId;
      return this;
    }

    public Builder setLength(Integer length) {
      this.length = length;
      return this;
    }

    public Builder setLanguage(String language) {
      this.language = language;
      return this;
    }

    public Builder setTimeCost(Integer timeCost) {
      this.timeCost = timeCost;
      return this;
    }

    public Builder setMemoryCost(Integer memoryCost) {
      this.memoryCost = memoryCost;
      return this;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Builder setCaseNumber(Integer caseNumber) {
      this.caseNumber = caseNumber;
      return this;
    }
  }
}
