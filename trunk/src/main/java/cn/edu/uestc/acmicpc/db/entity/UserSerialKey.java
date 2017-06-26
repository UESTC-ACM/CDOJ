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
 * User serial keys for password.
 */
@Table(name = "userSerialKey")
@Entity
@KeyField("userSerialKeyId")
public class UserSerialKey implements Serializable {

  private static final long serialVersionUID = -129312932189312L;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Override
  public String toString() {
    return "UserSerialKey{" + "serialKey='" + serialKey + '\'' + ", userByUserId=" + userId
        + ", time=" + time + ", userSerialKeyId=" + userSerialKeyId + '}';
  }

  private Integer userSerialKeyId;

  private Timestamp time;
  private String serialKey;

  @Column(name = "serialKey", nullable = false, insertable = true, updatable = true, length = 128,
      precision = 0, unique = false)
  @Basic
  public String getSerialKey() {
    return serialKey;
  }

  public void setSerialKey(String serialKey) {
    this.serialKey = serialKey;
  }

  @Column(name = "userSerialKeyId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getUserSerialKeyId() {
    return userSerialKeyId;
  }

  public void setUserSerialKeyId(Integer userSerialKeyId) {
    this.userSerialKeyId = userSerialKeyId;
  }

  @Column(name = "time", nullable = false, insertable = true, updatable = true, length = 19,
      precision = 0)
  @Basic
  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
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
