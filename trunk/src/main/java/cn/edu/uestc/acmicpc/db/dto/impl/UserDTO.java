package cn.edu.uestc.acmicpc.db.dto.impl;

import java.sql.Timestamp;

import cn.edu.uestc.acmicpc.db.entity.Department;
import cn.edu.uestc.acmicpc.db.entity.User;

/**
 * Data transfer object for {@link User}.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class UserDTO {

  /**
   * Input: user id, set null for new user
   */
  private Integer userId;

  /**
   * Input: user name
   */
  private String userName;

  /**
   * Input: old password
   */
  private String oldPassword;

  /**
   * Input: password
   */
  private String password;

  /**
   * Input: repeat password
   */
  private String passwordRepeat;

  /**
   * Input: nick name
   */
  private String nickName;

  /**
   * Input: email
   */
  private String email;

  /**
   * Input: school
   */
  private String school;

  /**
   * Input: departmentId
   */
  private Integer departmentId;

  /**
   * Department entity
   */
  private Department department;

  /**
   * Input: student ID
   */
  private String studentId;

  /**
   * Last login time
   */
  private Timestamp lastLogin;

  /**
   * Input: number of problems the user has solved
   */
  private Integer solved;


  /**
   * Input: number the problems the user has tried
   */
  private Integer tried;

  /**
   * Input: User type
   */
  private Integer type;

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getPasswordRepeat() {
    return passwordRepeat;
  }

  public void setPasswordRepeat(String passwordRepeat) {
    this.passwordRepeat = passwordRepeat;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
  }

  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}
