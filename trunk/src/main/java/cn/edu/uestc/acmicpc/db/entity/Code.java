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
 * Code information.
 */
@Table(name = "code")
@Entity
@KeyField("codeId")
public class Code implements Serializable {

  private static final long serialVersionUID = 6092881044668152921L;
  private Integer codeId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "codeId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getCodeId() {
    return codeId;
  }

  public void setCodeId(Integer codeId) {
    this.codeId = codeId;
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

  private Boolean share;

  @Column(name = "share", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getShare() {
    return share;
  }

  public void setShare(Boolean share) {
    this.share = share;
  }

  private Collection<Status> statusesByCodeId;

  @OneToMany(mappedBy = "codeByCodeId", cascade = CascadeType.ALL)
  public Collection<Status> getStatusesByCodeId() {
    return statusesByCodeId;
  }

  public void setStatusesByCodeId(Collection<Status> statusesByCodeId) {
    this.statusesByCodeId = statusesByCodeId;
  }
}
