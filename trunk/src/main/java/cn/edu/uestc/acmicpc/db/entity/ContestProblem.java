package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
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
 * Mappings between contest and problems.
 */
@Table(name = "contestProblem")
@Entity
@KeyField("contestProblemId")
public class ContestProblem implements Serializable {

  private static final long serialVersionUID = -9079259357297937419L;
  private Integer contestProblemId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "contestProblemId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getContestProblemId() {
    return contestProblemId;
  }

  public void setContestProblemId(Integer contestProblemId) {
    this.contestProblemId = contestProblemId;
  }

  private Integer order;

  @Column(name = "`order`", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  private Integer contestId;

  @Column(name = "contestId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
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

  private Contest contestByContestId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contestId", referencedColumnName = "contestId", nullable = false,
      insertable = false, updatable = false)
  public Contest getContestByContestId() {
    return contestByContestId;
  }

  public void setContestByContestId(Contest contestByContestId) {
    this.contestByContestId = contestByContestId;
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
}
