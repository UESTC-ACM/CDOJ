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
 * Compile information which compiler returns.
 */
@Table(name = "compileInfo")
@Entity
@KeyField("compileInfoId")
public class CompileInfo implements Serializable {

  private static final long serialVersionUID = 1404496264299518630L;
  private Integer compileInfoId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "compileInfoId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
  }

  private String content;

  @Column(name = "content", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  private Collection<Status> statusesByCompileInfoId;

  @OneToMany(mappedBy = "compileInfoByCompileInfoId", cascade = CascadeType.ALL)
  public Collection<Status> getStatusesByCompileInfoId() {
    return statusesByCompileInfoId;
  }

  public void setStatusesByCompileInfoId(Collection<Status> statusesByCompileInfoId) {
    this.statusesByCompileInfoId = statusesByCompileInfoId;
  }
}
