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
    "result", "length", "languageByLanguageId.name", "timeCost", "memoryCost", "time", "caseNumber"})
public class StatusListDTO implements BaseDTO<Status> {

  private String nickName;

  public StatusListDTO() {
  }

  private StatusListDTO(Integer statusId, String userName, String nickName, Integer problemId,
                        Integer contestId, String returnType, Integer returnTypeId, Integer length,
                        String language, Integer timeCost, Integer memoryCost, Timestamp time,
                        Integer caseNumber) {
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
          returnTypeId, length, language, timeCost, memoryCost, time, caseNumber);
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

    public Builder setNickName(String nickName) {
      this.nickName = nickName;
      return this;
    }
    public Integer getStatusId() {
      return statusId;
    }

    public Builder setStatusId(Integer statusId) {
      this.statusId = statusId;
      return this;
    }

    public String getUserName() {
      return userName;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Integer getContestId() {
      return contestId;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public String getReturnType() {
      return returnType;
    }

    public Builder setReturnType(String returnType) {
      this.returnType = returnType;
      return this;
    }

    public Integer getReturnTypeId() {
      return returnTypeId;
    }

    public Builder setReturnTypeId(Integer returnTypeId) {
      this.returnTypeId = returnTypeId;
      return this;
    }

    public Integer getLength() {
      return length;
    }

    public Builder setLength(Integer length) {
      this.length = length;
      return this;
    }

    public String getLanguage() {
      return language;
    }

    public Builder setLanguage(String language) {
      this.language = language;
      return this;
    }

    public Integer getTimeCost() {
      return timeCost;
    }

    public Builder setTimeCost(Integer timeCost) {
      this.timeCost = timeCost;
      return this;
    }

    public Integer getMemoryCost() {
      return memoryCost;
    }

    public Builder setMemoryCost(Integer memoryCost) {
      this.memoryCost = memoryCost;
      return this;
    }

    public Timestamp getTime() {
      return time;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Integer getCaseNumber() {
      return caseNumber;
    }

    public Builder setCaseNumber(Integer caseNumber) {
      this.caseNumber = caseNumber;
      return this;
    }
  }
}
