package cn.edu.uestc.acmicpc.db.dto.impl;

import java.sql.Timestamp;
import java.util.Date;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Data transfer object for {@link Status}.
 */
public class StatusDTO implements BaseDTO<Status> {

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

  public StatusDTO() {
  }

  private StatusDTO(Integer statusId, Integer result, Integer memoryCost, Integer timeCost,
      Integer length, Timestamp time, Integer caseNumber, Integer codeId,
      Integer compileInfoId, Integer contestId,
      Integer languageId) {
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
  }

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

  private Integer problemId;
  private Integer userId;

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link StatusDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer statusId;
    private Integer result = Global.OnlineJudgeReturnType.OJ_AC.ordinal();
    private Integer memoryCost = 100;
    private Integer timeCost = 100;
    private Integer length = 100;
    private Timestamp time = new Timestamp(new Date().getTime());
    private Integer caseNumber = 1;
    private Integer codeId;
    // TODO(mzry1992): get rid of code and use code content instead of it.
    //private Code code;
    private Integer compileInfoId;
    //private CompileInfo compileInfo;
    private Integer contestId;
    //private Contest contest;
    private Integer languageId;
    //private Language language;

    public Builder setStatusId(Integer statusId) {
      this.statusId = statusId;
      return this;
    }

    public Builder setResult(Integer result) {
      this.result = result;
      return this;
    }

    public Builder setMemoryCost(Integer memoryCost) {
      this.memoryCost = memoryCost;
      return this;
    }

    public Builder setTimeCost(Integer timeCost) {
      this.timeCost = timeCost;
      return this;
    }

    public Builder setLength(Integer length) {
      this.length = length;
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

    public Builder setCodeId(Integer codeId) {
      this.codeId = codeId;
      return this;
    }

    public Builder setCompileInfoId(Integer compileInfoId) {
      this.compileInfoId = compileInfoId;
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

    public StatusDTO build() {
      return new StatusDTO(statusId, result, memoryCost, timeCost, length, time, caseNumber,
          codeId, compileInfoId, contestId, languageId);
    }
  }
}
