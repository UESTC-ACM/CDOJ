package cn.edu.uestc.acmicpc.db.dto.impl.status;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.enums.OnlineJudgeReturnType;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Objects;

/**
 * Dto for status entity. <br/>
 * <code>@Fields({ "statusId", "result", "memoryCost", "timeCost", "length", "time", "caseNumber", "codeId",
 * "compileInfoId", "contestId", "languageId", "problemId", "userId" })</code>
 */
@Fields({ "statusId", "result", "memoryCost", "timeCost", "length", "time", "caseNumber", "codeId",
    "compileInfoId", "contestId", "languageId", "problemId", "userId" })
public class StatusDto implements BaseDto<Status> {

  public StatusDto() {
  }

  private StatusDto(Integer statusId, Integer result, Integer memoryCost, Integer timeCost,
      Integer length, Timestamp time, Integer caseNumber, Integer codeId,
      Integer compileInfoId, Integer contestId, Integer languageId,
      Integer problemId, Integer userId) {
    this.statusId = statusId;
    this.result = result;
    this.memoryCost = memoryCost;
    this.timeCost = timeCost;
    this.length = length;
    this.time = time;
    this.caseNumber = caseNumber;
    this.codeId = codeId;
    this.compileInfoId = compileInfoId;
    this.contestId = contestId;
    this.languageId = languageId;
    this.problemId = problemId;
    this.userId = userId;
  }

  private Integer statusId;
  private Integer result;
  private Integer memoryCost;
  private Integer timeCost;
  private Integer length;
  private Timestamp time;
  private Integer caseNumber;
  private Integer codeId;
  private Integer compileInfoId;
  private Integer contestId;
  private Integer languageId;
  private Integer problemId;
  private Integer userId;

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public Integer getResult() {
    return result;
  }

  public void setResult(Integer result) {
    this.result = result;
  }

  public Integer getMemoryCost() {
    return memoryCost;
  }

  public void setMemoryCost(Integer memoryCost) {
    this.memoryCost = memoryCost;
  }

  public Integer getTimeCost() {
    return timeCost;
  }

  public void setTimeCost(Integer timeCost) {
    this.timeCost = timeCost;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
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

  public Integer getCodeId() {
    return codeId;
  }

  public void setCodeId(Integer codeId) {
    this.codeId = codeId;
  }

  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
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

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof StatusDto)) {
      return false;
    }

    StatusDto that = (StatusDto) o;
    return Objects.equals(this.caseNumber, that.caseNumber)
        && Objects.equals(this.codeId, that.codeId)
        && Objects.equals(this.compileInfoId, that.compileInfoId)
        && Objects.equals(this.contestId, that.contestId)
        && Objects.equals(this.languageId, that.languageId)
        && Objects.equals(this.length, that.length)
        && Objects.equals(this.memoryCost, that.memoryCost)
        && Objects.equals(this.problemId, that.problemId)
        && Objects.equals(this.result, that.result)
        && Objects.equals(this.statusId, that.statusId)
        && Objects.equals(this.time, that.time)
        && Objects.equals(this.timeCost, that.timeCost)
        && Objects.equals(this.userId, that.userId);
  }

  @Override
  public int hashCode() {
    int result1 = statusId != null ? statusId.hashCode() : 0;
    result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
    result1 = 31 * result1 + (memoryCost != null ? memoryCost.hashCode() : 0);
    result1 = 31 * result1 + (timeCost != null ? timeCost.hashCode() : 0);
    result1 = 31 * result1 + (length != null ? length.hashCode() : 0);
    result1 = 31 * result1 + (time != null ? time.hashCode() : 0);
    result1 = 31 * result1 + (caseNumber != null ? caseNumber.hashCode() : 0);
    result1 = 31 * result1 + (codeId != null ? codeId.hashCode() : 0);
    result1 = 31 * result1 + (compileInfoId != null ? compileInfoId.hashCode() : 0);
    result1 = 31 * result1 + (contestId != null ? contestId.hashCode() : 0);
    result1 = 31 * result1 + (languageId != null ? languageId.hashCode() : 0);
    result1 = 31 * result1 + (problemId != null ? problemId.hashCode() : 0);
    result1 = 31 * result1 + (userId != null ? userId.hashCode() : 0);
    return result1;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<StatusDto> {

    private Builder() {
    }

    @Override
    public StatusDto build() {
      return new StatusDto(statusId, result, memoryCost, timeCost, length, time, caseNumber,
          codeId, compileInfoId, contestId, languageId, problemId, userId);
    }

    @Override
    public StatusDto build(Map<String, Object> properties) {
      statusId = (Integer) properties.get("statusId");
      result = (Integer) properties.get("result");
      memoryCost = (Integer) properties.get("memoryCost");
      timeCost = (Integer) properties.get("timeCost");
      length = (Integer) properties.get("length");
      time = (Timestamp) properties.get("time");
      caseNumber = (Integer) properties.get("caseNumber");
      codeId = (Integer) properties.get("codeId");
      compileInfoId = (Integer) properties.get("compileInfoId");
      contestId = (Integer) properties.get("contestId");
      languageId = (Integer) properties.get("languageId");
      problemId = (Integer) properties.get("problemId");
      userId = (Integer) properties.get("userId");
      return build();

    }

    private Integer statusId;
    private Integer result = OnlineJudgeReturnType.OJ_WAIT.ordinal();
    private Integer memoryCost = 0;
    private Integer timeCost = 0;
    private Integer length;
    private Timestamp time;
    private Integer caseNumber = 0;
    private Integer codeId;
    private Integer compileInfoId;
    private Integer contestId;
    private Integer languageId;
    private Integer problemId;
    private Integer userId;

    public Integer getStatusId() {
      return statusId;
    }

    public Builder setStatusId(Integer statusId) {
      this.statusId = statusId;
      return this;
    }

    public Integer getResult() {
      return result;
    }

    public Builder setResult(Integer result) {
      this.result = result;
      return this;
    }

    public Integer getMemoryCost() {
      return memoryCost;
    }

    public Builder setMemoryCost(Integer memoryCost) {
      this.memoryCost = memoryCost;
      return this;
    }

    public Integer getTimeCost() {
      return timeCost;
    }

    public Builder setTimeCost(Integer timeCost) {
      this.timeCost = timeCost;
      return this;
    }

    public Integer getLength() {
      return length;
    }

    public Builder setLength(Integer length) {
      this.length = length;
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

    public Integer getCodeId() {
      return codeId;
    }

    public Builder setCodeId(Integer codeId) {
      this.codeId = codeId;
      return this;
    }

    public Integer getCompileInfoId() {
      return compileInfoId;
    }

    public Builder setCompileInfoId(Integer compileInfoId) {
      this.compileInfoId = compileInfoId;
      return this;
    }

    public Integer getContestId() {
      return contestId;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Integer getLanguageId() {
      return languageId;
    }

    public Builder setLanguageId(Integer languageId) {
      this.languageId = languageId;
      return this;
    }

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Integer getUserId() {
      return userId;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }
  }
}
