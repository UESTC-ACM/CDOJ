package cn.edu.uestc.acmicpc.db.entity;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

/**
 * Languages for compiler.
 */
@Table(name = "language")
@Entity
@KeyField("languageId")
public class Language implements Serializable {

  private static final long serialVersionUID = 6622284482431851438L;
  private Integer languageId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "languageId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  private String name;

  @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String extension;

  @Column(name = "extension", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public String getExtension() {
    return extension;
  }

  public void setExtension(String extension) {
    this.extension = extension;
  }

  private String param;

  @Column(name = "param", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getParam() {
    return param;
  }

  public void setParam(String param) {
    this.param = param;
  }
}
