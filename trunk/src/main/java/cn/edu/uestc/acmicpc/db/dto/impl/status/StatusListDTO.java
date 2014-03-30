package cn.edu.uestc.acmicpc.db.dto.impl.status;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO used in status list.
 * <br/>
 * <code>@Fields({ "statusId", "userByUserId.userName", "problemId", "contestId",
 * "result", "length",
 * "languageByLanguageId.name", "timeCost", "memoryCost", "time", "caseNumber" })</code>
 */
@Fields({"statusId", "userByUserId.userName", "userByUserId.nickName", "problemId", "contestId",
    "result", "length", "languageByLanguageId.name", "timeCost", "memoryCost", "time", "caseNumber",
    "userByUserId.email"})
public class StatusListDTO implements BaseDTO<Status> {

  private String nickName;

  public StatusListDTO() {
  }

  private StatusListDTO(Integer statusId, String userName, String nickName, Integer problemId,
                        Integer contestId, String returnType, Integer returnTypeId, Integer length,
                        String language, Integer timeCost, Integer memoryCost, Timestamp time,
                        Integer caseNumber, String email) {
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StatusListDTO that = (StatusListDTO) o;

    if (caseNumber != null ? !caseNumber.equals(that.caseNumber) : that.caseNumber != null) {
      return false;
    }
    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }
    if (language != null ? !language.equals(that.language) : that.language != null) {
      return false;
    }
    if (length != null ? !length.equals(that.length) : that.length != null) {
      return false;
    }
    if (memoryCost != null ? !memoryCost.equals(that.memoryCost) : that.memoryCost != null) {
      return false;
    }
    if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) {
      return false;
    }
    if (problemId != null ? !problemId.equals(that.problemId) : that.problemId != null) {
      return false;
    }
    if (returnType != null ? !returnType.equals(that.returnType) : that.returnType != null) {
      return false;
    }
    if (returnTypeId != null ? !returnTypeId.equals(that.returnTypeId) : that.returnTypeId != null) {
      return false;
    }
    if (statusId != null ? !statusId.equals(that.statusId) : that.statusId != null) {
      return false;
    }
    if (time != null ? !time.equals(that.time) : that.time != null) {
      return false;
    }
    if (timeCost != null ? !timeCost.equals(that.timeCost) : that.timeCost != null) {
      return false;
    }
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }

    return true;
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

  public static class Builder implements BaseBuilder<StatusListDTO> {

    private String nickName;

    private Builder() {
    }

    @Override
    public StatusListDTO build() {
      return new StatusListDTO(statusId, userName, nickName, problemId, contestId, returnType,
          returnTypeId, length, language, timeCost, memoryCost, time, caseNumber, email);
    }

    @Override
    public StatusListDTO build(Map<String, Object> properties) {
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
      return build();

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
