package cn.edu.uestc.acmicpc.db.dto.impl.user;

import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

/**
 * DTO for user entity. <br/>
 * <code>@Fields({ "userName", "studentId", "school", "nickName", "email",
 * "type", "motto", "departmentId"})</code>
 */
@Fields({ "userName", "studentId", "school", "nickName",
    "email", "type", "motto", "departmentId"})
public class UserAdminEditorDTO implements BaseDTO<User> {

  public UserAdminEditorDTO() {
  }

  private UserAdminEditorDTO(String userName, String studentId,
      String school, String nickName, String email,
      Integer type, String motto, Integer departmentId) {
    this.userName = userName;
    this.studentId = studentId;
    this.school = school;
    this.nickName = nickName;
    this.email = email;
    this.type = type;
    this.motto = motto;
    this.departmentId = departmentId;
  }

  private String userName;
  private String studentId;
  private String school;
  private String nickName;
  private String email;
  private Integer type;
  private String motto;
  private Integer departmentId;

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

  public Integer getDepartmentId() {
    return departmentId;
  }

  public void setDepartmentId(Integer departmentId) {
    this.departmentId = departmentId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<UserAdminEditorDTO> {

    private Builder() {
    }

    @Override
    public UserAdminEditorDTO build() {
      return new UserAdminEditorDTO(userName, studentId, school,
          nickName, email, type, motto, departmentId);
    }

    @Override
    public UserAdminEditorDTO build(Map<String, Object> properties) {
      userName = (String) properties.get("userName");
      studentId = (String) properties.get("studentId");
      school = (String) properties.get("school");
      nickName = (String) properties.get("nickName");
      email = (String) properties.get("email");
      type = (Integer) properties.get("type");
      motto = (String) properties.get("motto");
      departmentId = (Integer) properties.get("departmentId");
      return build();

    }

    private String userName = "admin";
    private String studentId = "20131010";
    private String school = "school";
    private String nickName = "nickName";
    private String email = "email";
    private Integer type = 0;
    private String motto = "";
    private Integer departmentId = 1;

    public String getUserName() {
      return userName;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public String getStudentId() {
      return studentId;
    }

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public String getSchool() {
      return school;
    }

    public Builder setSchool(String school) {
      this.school = school;
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

    public Integer getDepartmentId() {
      return departmentId;
    }

    public Builder setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }

  }
}