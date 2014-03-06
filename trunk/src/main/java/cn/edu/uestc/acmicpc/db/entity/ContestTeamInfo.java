package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Contest team information, for school programming contest.
 */
@Table(name = "contestTeamInfo")
@Entity
@KeyField("teamId")
public class ContestTeamInfo implements Serializable {

  private static final long serialVersionUID = -5816811480409208296L;
  private Integer teamId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "teamId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  private Integer userId;

  @Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private String name = "";

  @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  private String coderName = "";

  @Column(name = "coderName", nullable = false, insertable = true, updatable = true, length = 150,
      precision = 0)
  @Basic
  public String getCoderName() {
    return coderName;
  }

  public void setCoderName(String coderName) {
    this.coderName = coderName;
  }

  private String sex = "";

  @Column(name = "sex", nullable = false, insertable = true, updatable = true, length = 3,
      precision = 0)
  @Basic
  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  private String department = "";

  @Column(name = "department", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
  }

  private String grade = "";

  @Column(name = "grade", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  private String studentId = "";

  @Column(name = "studentId", nullable = false, insertable = true, updatable = true, length = 100,
      precision = 0)
  @Basic
  public String getStudentId() { return studentId; }

  public void setStudentId(String studentId) { this.studentId = studentId; }

  private String phone = "";

  @Column(name = "phone", nullable = false, insertable = true, updatable = true, length = 100,
      precision = 0)
  @Basic
  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  private String size = "";

  @Column(name = "size", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  private String email = "";

  @Column(name = "email", nullable = false, insertable = true, updatable = true, length = 300,
      precision = 0)
  @Basic
  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  private String school = "";

  @Column(name = "school", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  private Byte state = 0;

  @Column(name = "state", nullable = false, insertable = true, updatable = true, length = 3,
      precision = 0)
  @Basic
  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }
}
