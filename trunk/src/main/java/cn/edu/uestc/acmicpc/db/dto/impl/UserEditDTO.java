package cn.edu.uestc.acmicpc.db.dto.impl;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Description
 * TODO(mzry1992)
 */
public class UserEditDTO {
  private Integer userId;

  private String userName;

  @NotNull(message = "Please enter your nick name.")
  @Length(min = 2, max = 20, message = "Please enter 2-20 characters.")
  private String nickName;

  private String email;

  @NotNull(message = "Please enter your school name.")
  @Length(min = 1, max = 100, message = "Please enter 1-100 characters.")
  private String school;

  @NotNull(message = "Please select your department.")
  private Integer departmentId;

  @NotNull(message = "Please enter your student ID.")
  @Length(min = 1, max = 20, message = "Please enter 1-20 characters.")
  private String studentId;

  @Length(min = 6, max = 20, message = "Please enter 6-20 characters.")
  private String newPassword;

  @Length(min = 6, max = 20, message = "Please enter 6-20 characters.")
  private String newPasswordRepeat;

  @NotNull(message = "Please enter your current password.")
  private String oldPassword;

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

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getNewPasswordRepeat() {
    return newPasswordRepeat;
  }

  public void setNewPasswordRepeat(String newPasswordRepeat) {
    this.newPasswordRepeat = newPasswordRepeat;
  }

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

}
