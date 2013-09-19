package cn.edu.uestc.acmicpc.db.dto.impl;

import java.sql.Timestamp;
import java.util.Date;

import cn.edu.uestc.acmicpc.db.entity.Department;

/**
 * Data transfer object for {@link User}.
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

  public UserDTO() {
  }

  private UserDTO(Integer userId, String userName, String oldPassword, String password,
      String passwordRepeat, String nickName, String email, String school, Integer departmentId,
      Department department, String studentId, Timestamp lastLogin, Integer solved,
      Integer tried, Integer type) {
    this.userId = userId;
    this.userName = userName;
    this.oldPassword = oldPassword;
    this.password = password;
    this.passwordRepeat = passwordRepeat;
    this.nickName = nickName;
    this.email = email;
    this.school = school;
    this.departmentId = departmentId;
    this.department = department;
    this.studentId = studentId;
    this.lastLogin = lastLogin;
    this.solved = solved;
    this.tried = tried;
    this.type = type;
  }

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

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link UserDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer userId;
    private String userName = "";
    private String oldPassword = "";
    private String password = "";
    private String passwordRepeat = "";
    private String nickName = "";
    private String email = "";
    private String school = "";
    private Integer departmentId;
    // TODO do not use DB entity in dto.
    private Department department;
    private String studentId = "";
    private Timestamp lastLogin = new Timestamp(new Date().getTime());
    private Integer solved = 0;
    private Integer tried = 0;
    private Integer type = 0;

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder setOldPassword(String oldPassword) {
      this.oldPassword = oldPassword;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder setPasswordRepeat(String passwordRepeat) {
      this.passwordRepeat = passwordRepeat;
      return this;
    }

    public Builder setNickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setSchool(String school) {
      this.school = school;
      return this;
    }

    public Builder setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }

    public Builder setDepartment(Department department) {
      this.department = department;
      return this;
    }

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public Builder setLastLogin(Timestamp lastLogin) {
      this.lastLogin = lastLogin;
      return this;
    }

    public Builder setSolved(Integer solved) {
      this.solved = solved;
      return this;
    }

    public Builder setTried(Integer tried) {
      this.tried = tried;
      return this;
    }

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public UserDTO build() {
      return new UserDTO(userId, userName, oldPassword, password, passwordRepeat, nickName, email,
          school, departmentId, department, studentId, lastLogin, solved, tried, type);
    }
  }
}
