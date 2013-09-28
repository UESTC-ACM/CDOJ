package cn.edu.uestc.acmicpc.db.dto.impl;

import java.sql.Timestamp;

/**
 * Description
 * TODO(mzry1992)
 */
public class UserDTO {
  private Integer userId;

  private String userName;

  private String studentId;

  private String password;

  private String school;

  private String nickName;

  private String email;

  private Integer solved;

  private Integer tried;

  private Integer type;

  private String typeName;

  private Timestamp lastLogin;

  private Integer departmentId;

  private String departmentName;

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

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
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

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
  }

  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public UserDTO() {
  }

  public UserDTO(Integer userId, String userName, String studentId, String password,
                 String school, String nickName, String email, Integer solved, Integer tried,
                 Integer type, String typeName, Timestamp lastLogin, Integer departmentId,
                 String departmentName) {
    this.userId = userId;
    this.userName = userName;
    this.studentId = studentId;
    this.password = password;
    this.school = school;
    this.nickName = nickName;
    this.email = email;
    this.solved = solved;
    this.tried = tried;
    this.type = type;
    this.typeName = typeName;
    this.lastLogin = lastLogin;
    this.departmentId = departmentId;
    this.departmentName = departmentName;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link UserDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer userId;

    private String userName;

    private String studentId;

    private String password;

    private String school;

    private String nickName;

    private String email;

    private Integer solved;

    private Integer tried;

    private Integer type;

    private String typeName;

    private Timestamp lastLogin;

    private Integer departmentId;

    private String departmentName;

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public Builder setPassword(String password) {
      this.password = password;
      return this;
    }

    public Builder setSchool(String school) {
      this.school = school;
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

    public Builder setTypeName(String typeName) {
      this.typeName = typeName;
      return this;
    }

    public Builder setLastLogin(Timestamp lastLogin) {
      this.lastLogin = lastLogin;
      return this;
    }

    public Builder setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }

    public Builder setDepartmentName(String departmentName) {
      this.departmentName = departmentName;
      return this;
    }

    public UserDTO build() {
      return new UserDTO(userId, userName, studentId, password,
          school, nickName, email, solved, tried,
          type, typeName, lastLogin, departmentId,
          departmentName);
    }
  }
}
