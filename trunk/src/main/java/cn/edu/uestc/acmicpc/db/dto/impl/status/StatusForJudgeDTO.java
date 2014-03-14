package cn.edu.uestc.acmicpc.db.dto.impl.status;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO used in judge service.
 * <br/>
 * <code>@Fields({ "statusId", "result", "caseNumber", "codeByCodeId.content",
 * "problemId", "problemByProblemId.dataCount",
 * "problemByProblemId.javaMemoryLimit", "problemByProblemId.javaTimeLimit",
 * "problemByProblemId.memoryLimit", "problemByProblemId.timeLimit",
 * "problemByProblemId.outputLimit", "problemByProblemId.isSpj", "languageId",
 * "languageByLanguageId.extension", "languageByLanguageId.name",
 * "memoryCost", "timeCost", "compileInfoId", "userId" })</code>
 */
@Fields({"statusId", "result", "caseNumber", "codeByCodeId.content",
    "problemId", "problemByProblemId.dataCount",
    "problemByProblemId.javaMemoryLimit", "problemByProblemId.javaTimeLimit",
    "problemByProblemId.memoryLimit", "problemByProblemId.timeLimit",
    "problemByProblemId.outputLimit", "problemByProblemId.isSpj", "languageId",
    "languageByLanguageId.extension", "languageByLanguageId.name",
    "memoryCost", "timeCost", "compileInfoId", "userId"})
public class StatusForJudgeDTO implements BaseDTO<Status> {

  public StatusForJudgeDTO() {
  }

  private StatusForJudgeDTO(Integer statusId, Integer result,
                            Integer caseNumber, String codeContent,
                            Integer problemId, Integer dataCount, Integer javaMemoryLimit,
                            Integer javaTimeLimit, Integer memoryLimit, Integer timeLimit,
                            Integer outputLimit, Boolean isSpj, Integer languageId,
                            String languageExtension, String languageName, Integer memoryCost,
                            Integer timeCost, Integer compileInfoId, Integer userId) {
    this.statusId = statusId;
    this.result = result;
    this.caseNumber = caseNumber;
    this.codeContent = codeContent;
    this.problemId = problemId;
    this.dataCount = dataCount;
    this.javaMemoryLimit = javaMemoryLimit;
    this.javaTimeLimit = javaTimeLimit;
    this.memoryLimit = memoryLimit;
    this.timeLimit = timeLimit;
    this.outputLimit = outputLimit;
    this.isSpj = isSpj;
    this.languageId = languageId;
    this.languageExtension = languageExtension;
    this.languageName = languageName;
    this.memoryCost = memoryCost;
    this.timeCost = timeCost;
    this.compileInfoId = compileInfoId;
    this.userId = userId;
  }

  private Integer statusId;
  private Integer result;
  private Integer caseNumber;
  private String codeContent;
  private Integer problemId;
  private Integer dataCount;
  private Integer javaMemoryLimit;
  private Integer javaTimeLimit;
  private Integer memoryLimit;
  private Integer timeLimit;
  private Integer outputLimit;
  private Boolean isSpj;
  private Integer languageId;
  private String languageExtension;
  private String languageName;
  private Integer memoryCost;
  private Integer timeCost;
  private Integer compileInfoId;
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

  public Integer getCaseNumber() {
    return caseNumber;
  }

  public void setCaseNumber(Integer caseNumber) {
    this.caseNumber = caseNumber;
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

  public Integer getDataCount() {
    return dataCount;
  }

  public void setDataCount(Integer dataCount) {
    this.dataCount = dataCount;
  }

  public Integer getJavaMemoryLimit() {
    return javaMemoryLimit;
  }

  public void setJavaMemoryLimit(Integer javaMemoryLimit) {
    this.javaMemoryLimit = javaMemoryLimit;
  }

  public Integer getJavaTimeLimit() {
    return javaTimeLimit;
  }

  public void setJavaTimeLimit(Integer javaTimeLimit) {
    this.javaTimeLimit = javaTimeLimit;
  }

  public Integer getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(Integer memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  public Integer getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(Integer timeLimit) {
    this.timeLimit = timeLimit;
  }

  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
  }

  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean isSpj) {
    this.isSpj = isSpj;
  }

  public Integer getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  public String getLanguageExtension() {
    return languageExtension;
  }

  public void setLanguageExtension(String languageExtension) {
    this.languageExtension = languageExtension;
  }

  public String getLanguageName() {
    return languageName;
  }

  public void setLanguageName(String languageName) {
    this.languageName = languageName;
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

  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<StatusForJudgeDTO> {

    private Builder() {
    }

    @Override
    public StatusForJudgeDTO build() {
      return new StatusForJudgeDTO(statusId, result, caseNumber, codeContent,
          problemId, dataCount,
          javaMemoryLimit, javaTimeLimit, memoryLimit, timeLimit, outputLimit,
          isSpj, languageId, languageExtension, languageName, memoryCost,
          timeCost, compileInfoId, userId);
    }

    @Override
    public StatusForJudgeDTO build(Map<String, Object> properties) {
      statusId = (Integer) properties.get("statusId");
      result = (Integer) properties.get("result");
      caseNumber = (Integer) properties.get("caseNumber");
      codeContent = (String) properties.get("codeByCodeId.content");
      problemId = (Integer) properties.get("problemId");
      dataCount = (Integer) properties.get("problemByProblemId.dataCount");
      javaMemoryLimit = (Integer) properties
          .get("problemByProblemId.javaMemoryLimit");
      javaTimeLimit = (Integer) properties
          .get("problemByProblemId.javaTimeLimit");
      memoryLimit = (Integer) properties.get("problemByProblemId.memoryLimit");
      timeLimit = (Integer) properties.get("problemByProblemId.timeLimit");
      outputLimit = (Integer) properties.get("problemByProblemId.outputLimit");
      isSpj = (Boolean) properties.get("problemByProblemId.isSpj");
      languageId = (Integer) properties.get("languageId");
      languageExtension = (String) properties
          .get("languageByLanguageId.extension");
      languageName = (String) properties
          .get("languageByLanguageId.name");
      memoryCost = (Integer) properties.get("memoryCost");
      timeCost = (Integer) properties.get("timeCost");
      compileInfoId = (Integer) properties.get("compileInfoId");
      userId = (Integer) properties.get("userId");
      return build();
    }

    private Integer statusId;
    private Integer result;
    private Integer caseNumber;
    private String codeContent;
    private Integer problemId;
    private Integer dataCount;
    private Integer javaMemoryLimit;
    private Integer javaTimeLimit;
    private Integer memoryLimit;
    private Integer timeLimit;
    private Integer outputLimit;
    private Boolean isSpj;
    private Integer languageId;
    private String languageExtension;
    private String languageName;
    private Integer memoryCost;
    private Integer timeCost;
    private Integer compileInfoId;
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

    public Integer getCaseNumber() {
      return caseNumber;
    }

    public Builder setCaseNumber(Integer caseNumber) {
      this.caseNumber = caseNumber;
      return this;
    }

    public String getCodeContent() {
      return codeContent;
    }

    public Builder setCodeContent(String codeContent) {
      this.codeContent = codeContent;
      return this;
    }

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Integer getDataCount() {
      return dataCount;
    }

    public Builder setDataCount(Integer dataCount) {
      this.dataCount = dataCount;
      return this;
    }

    public Integer getJavaMemoryLimit() {
      return javaMemoryLimit;
    }

    public Builder setJavaMemoryLimit(Integer javaMemoryLimit) {
      this.javaMemoryLimit = javaMemoryLimit;
      return this;
    }

    public Integer getJavaTimeLimit() {
      return javaTimeLimit;
    }

    public Builder setJavaTimeLimit(Integer javaTimeLimit) {
      this.javaTimeLimit = javaTimeLimit;
      return this;
    }

    public Integer getMemoryLimit() {
      return memoryLimit;
    }

    public Builder setMemoryLimit(Integer memoryLimit) {
      this.memoryLimit = memoryLimit;
      return this;
    }

    public Integer getTimeLimit() {
      return timeLimit;
    }

    public Builder setTimeLimit(Integer timeLimit) {
      this.timeLimit = timeLimit;
      return this;
    }

    public Integer getOutputLimit() {
      return outputLimit;
    }

    public Builder setOutputLimit(Integer outputLimit) {
      this.outputLimit = outputLimit;
      return this;
    }

    public Boolean getIsSpj() {
      return isSpj;
    }

    public Builder setIsSpj(Boolean isSpj) {
      this.isSpj = isSpj;
      return this;
    }

    public Integer getLanguageId() {
      return languageId;
    }

    public Builder setLanguageId(Integer languageId) {
      this.languageId = languageId;
      return this;
    }

    public String getLanguageExtension() {
      return languageExtension;
    }

    public Builder setLanguageExtension(String languageExtension) {
      this.languageExtension = languageExtension;
      return this;
    }

    public String getLanguageName() {
      return languageName;
    }

    public Builder setLanguageName(String languageName) {
      this.languageName = languageName;
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

    public Integer getCompileInfoId() {
      return compileInfoId;
    }

    public Builder setCompileInfoId(Integer compileInfoId) {
      this.compileInfoId = compileInfoId;
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
