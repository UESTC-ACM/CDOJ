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
 * Mappings between contests and users.
 */
@Table(name = "contestUser")
@Entity
@KeyField("contestUserId")
public class ContestUser implements Serializable {

  private static final long serialVersionUID = -8408381521779421508L;
  private Integer contestUserId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "contestUserId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getContestUserId() {
    return contestUserId;
  }

  public void setContestUserId(Integer contestUserId) {
    this.contestUserId = contestUserId;
  }

  private Byte status;

  @Column(name = "status", nullable = false, insertable = true, updatable = true, length = 3,
      precision = 0)
  @Basic
  public Byte getStatus() {
    return status;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  private String comment;

  @Column(name = "comment", nullable = false, insertable = true, updatable = true, length = 255,
      precision = 0)
  @Basic
  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
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

  private Integer userId;

  @Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
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
