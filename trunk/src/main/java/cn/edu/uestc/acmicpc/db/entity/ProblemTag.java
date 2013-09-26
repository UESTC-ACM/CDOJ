package cn.edu.uestc.acmicpc.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

/**
 * Mappings between problems and tags.
 */
@Table(name = "problemTag")
@Entity
@KeyField("problemTagId")
public class ProblemTag implements Serializable {

  private static final long serialVersionUID = 8758938774072713107L;
  private Integer problemTagId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "problemTagId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getProblemTagId() {
    return problemTagId;
  }

  public void setProblemTagId(Integer problemTagId) {
    this.problemTagId = problemTagId;
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

  private Integer tagId;

  @Column(name = "tagId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getTagId() {
    return tagId;
  }

  public void setTagId(Integer tagId) {
    this.tagId = tagId;
  }
}
