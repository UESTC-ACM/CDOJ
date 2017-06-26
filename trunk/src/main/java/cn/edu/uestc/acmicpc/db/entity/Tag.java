package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Problem tag information.
 */
@Table(name = "tag")
@Entity
@KeyField("tagId")
public class Tag implements Serializable {

  private static final long serialVersionUID = 8221283073294354906L;
  private Integer tagId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "tagId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTagId() {
    return tagId;
  }

  public void setTagId(Integer tagId) {
    this.tagId = tagId;
  }

  private String name;

  @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0, unique = true)
  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private Collection<ProblemTag> problemTagsByTagId;

  @OneToMany(mappedBy = "tagByTagId", cascade = CascadeType.ALL)
  public Collection<ProblemTag> getProblemTagsByTagId() {
    return problemTagsByTagId;
  }

  public void setProblemTagsByTagId(Collection<ProblemTag> problemTagsByTagId) {
    this.problemTagsByTagId = problemTagsByTagId;
  }
}
