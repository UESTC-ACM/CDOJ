package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Status information.
 */
@Table(name = "status")
@Entity
@KeyField("statusId")
public class Status implements Serializable {

  private static final long serialVersionUID = 4819326443036942394L;
  private Integer statusId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "statusId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  private Integer result;

  @Column(name = "result", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getResult() {
    return result;
  }

  public void setResult(Integer result) {
    this.result = result;
  }

  private Integer memoryCost;

  @Column(name = "memoryCost", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getMemoryCost() {
    return memoryCost;
  }

  public void setMemoryCost(Integer memoryCost) {
    this.memoryCost = memoryCost;
  }

  private Integer timeCost;

  @Column(name = "timeCost", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getTimeCost() {
    return timeCost;
  }

  public void setTimeCost(Integer timeCost) {
    this.timeCost = timeCost;
  }

  private Integer length;

  @Column(name = "length", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  private Timestamp time;

  @Column(name = "time", nullable = false, insertable = true, updatable = true, length = 19,
      precision = 0)
  @Basic
  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  private Integer caseNumber = 0;

  @Column(name = "caseNumber", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getCaseNumber() {
    return caseNumber;
  }

  public void setCaseNumber(Integer caseNumber) {
    this.caseNumber = caseNumber;
  }

  private Integer codeId;

  @Column(name = "codeId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getCodeId() {
    return codeId;
  }

  public void setCodeId(Integer codeId) {
    this.codeId = codeId;
  }

  private Integer compileInfoId;

  @Column(name = "compileInfoId", nullable = true, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
  }

  private Integer contestId;

  @Column(name = "contestId", nullable = true, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  private Integer languageId;

  @Column(name = "languageId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  private Integer problemId;

  @Column(name = "problemId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  private Integer userId;

  @Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private Code codeByCodeId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "codeId", referencedColumnName = "codeId", nullable = false,
      insertable = false, updatable = false)
  public Code getCodeByCodeId() {
    return codeByCodeId;
  }

  public void setCodeByCodeId(Code codeByCodeId) {
    this.codeByCodeId = codeByCodeId;
  }

  private CompileInfo compileInfoByCompileInfoId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "compileInfoId", referencedColumnName = "compileInfoId",
      insertable = false, updatable = false)
  public CompileInfo getCompileInfoByCompileInfoId() {
    return compileInfoByCompileInfoId;
  }

  public void setCompileInfoByCompileInfoId(CompileInfo compileInfoByCompileInfoId) {
    this.compileInfoByCompileInfoId = compileInfoByCompileInfoId;
  }

  private Contest contestByContestId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contestId", referencedColumnName = "contestId",
      insertable = false, updatable = false)
  public Contest getContestByContestId() {
    return contestByContestId;
  }

  public void setContestByContestId(Contest contestByContestId) {
    this.contestByContestId = contestByContestId;
  }

  private Language languageByLanguageId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "languageId", referencedColumnName = "languageId", nullable = false,
      insertable = false, updatable = false)
  public Language getLanguageByLanguageId() {
    return languageByLanguageId;
  }

  public void setLanguageByLanguageId(Language languageByLanguageId) {
    this.languageByLanguageId = languageByLanguageId;
  }

  private Problem problemByProblemId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "problemId", referencedColumnName = "problemId", nullable = false,
      insertable = false, updatable = false)
  public Problem getProblemByProblemId() {
    return problemByProblemId;
  }

  public void setProblemByProblemId(Problem problemByProblemId) {
    this.problemByProblemId = problemByProblemId;
  }

  private User userByUserId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false,
      insertable = false, updatable = false)
  public User getUserByUserId() {
    return userByUserId;
  }

  public void setUserByUserId(User userByUserId) {
    this.userByUserId = userByUserId;
  }
}
