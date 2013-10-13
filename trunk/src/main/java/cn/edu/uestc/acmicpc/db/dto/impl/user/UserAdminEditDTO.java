package cn.edu.uestc.acmicpc.db.dto.impl.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

public class UserAdminEditDTO {
  
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
  
  @NotNull(message = "Please enter the user type.")
  private Integer type;

  private String motto;

  public UserAdminEditDTO(){
  }
  
  public UserAdminEditDTO(Integer userId, String userName, String nickName, String email, 
      String school, Integer departmentId, String studentId, Integer type, String motto){
    this.userId = userId;
    this.userName = userName;
    this.nickName = nickName;
    this.email = email;
    this.school = school;
    this.departmentId = departmentId;
    this.studentId = studentId;
    this.type = type;
    this.motto = motto;
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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
  }

  public static Builder builder() {
    return new Builder();
  }
  
  public static class Builder {
    
    private Integer userId;   
    private String userName;
    private String nickName;
    private String email;
    private String school;
    private Integer departmentId;
    private String studentId;
    private Integer type;
    private String motto;
    
    private Builder() {
    }
    
    public UserAdminEditDTO build() {
      return new UserAdminEditDTO(userId, userName, nickName, email, school, 
          departmentId, studentId, type, motto);
    }

    public Integer getUserId() {
      return userId;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public String getUserName() {
      return userName;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public String getNickName() {
      return nickName;
    }

    public Builder setNickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    public String getEmail() {
      return email;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public String getSchool() {
      return school;
    }

    public Builder setSchool(String school) {
      this.school = school;
      return this;
    }

    public Integer getDepartmentId() {
      return departmentId;
    }

    public Builder setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }

    public String getStudentId() {
      return studentId;
    }

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public Integer getType() {
      return type;
    }

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public String getMotto() {
      return motto;
    }

    public Builder setMotto(String motto) {
      this.motto = motto;
      return this;
    }
  }
}
