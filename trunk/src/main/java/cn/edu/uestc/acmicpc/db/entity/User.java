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
 * User information.
 */
@Table(name = "user")
@Entity
@KeyField("userId")
public class User implements Serializable {

  private static final long serialVersionUID = -1942419166710527006L;
  private Integer userId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private String userName;

  @Column(name = "userName", nullable = false, insertable = true, updatable = true, length = 24,
      precision = 0, unique = true)
  @Basic
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  private String studentId;

  @Column(name = "studentId", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  private String password;

  @Column(name = "password", nullable = false, insertable = true, updatable = true, length = 40,
      precision = 0)
  @Basic
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private String school = "";

  @Column(name = "school", nullable = false, insertable = true, updatable = true, length = 100,
      precision = 0)
  @Basic
  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  private String nickName;

  @Column(name = "nickName", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  private String email;

  @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 100,
      precision = 0, unique = true)
  @Basic
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private Integer solved = 0;

  @Column(name = "solved", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  private Integer tried = 0;

  @Column(name = "tried", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  private Integer type = 0;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Timestamp lastLogin;

  @Column(name = "lastLogin", nullable = false, insertable = true, updatable = true, length = 19,
      precision = 0)
  @Basic
  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
  }

  private Integer departmentId;

  @Column(name = "departmentId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0)
  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }
}
