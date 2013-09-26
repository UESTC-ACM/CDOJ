package cn.edu.uestc.acmicpc.db.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

/**
 * Contest information.
 */
@Table(name = "contest")
@Entity
@KeyField("contestId")
public class Contest implements Serializable {

  private static final long serialVersionUID = -3631561809657861853L;
  private Integer contestId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "contestId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getContestId() {
    return contestId;
  }

  @Override
  public String toString() {
    return "Contest{" + "isVisible=" + isVisible + ", length=" + length + ", time=" + time
        + ", type=" + type + ", description='" + description + '\'' + ", title='" + title + '\''
        + ", contestId=" + contestId + '}';
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  private String title;

  @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String description = "";

  @Column(name = "description", nullable = false, insertable = true, updatable = true,
      length = 200, precision = 0)
  @Basic
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private Byte type = 0;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 3,
      precision = 0)
  @Basic
  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
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

  private Boolean isVisible;

  @Column(name = "isVisible", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    isVisible = visible;
  }
}
