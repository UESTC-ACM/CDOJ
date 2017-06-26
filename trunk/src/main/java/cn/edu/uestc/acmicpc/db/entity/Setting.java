package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Setting information
 */
@Table(name = "setting")
@Entity
@KeyField("settingId")
public class Setting implements Serializable {

  private static final long serialVersionUID = -4166330560308995164L;
  private Integer settingsId;

  public static long getSerialVersionUID() {
    return serialVersionUID;
  }

  @Column(name = "settingId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getSettingId() {
    return settingsId;
  }

  public void setSettingId(Integer settingsId) {
    this.settingsId = settingsId;
  }

  private String name;

  @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 255,
      precision = 0)
  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String description;

  @Column(name = "description", nullable = false, insertable = true, updatable = true, length = 255,
      precision = 0)
  @Basic
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private String value;

  @Column(name = "value", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
