package cn.edu.uestc.acmicpc.db.dto.impl.user;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

/**
 * DTO for user entity. <br/>
 * <code>@Fields({ "userId", "userName", "studentId", "password", "school", "nickName", "email", "solved",
 * "tried", "type", "motto", "lastLogin", "departmentId", "departmentByDepartmentId.name" })</code>
 */
@Fields({"userId", "userName", "studentId", "password", "school", "nickName",
    "email", "solved",
    "tried", "type", "motto", "lastLogin", "departmentId",
    "departmentByDepartmentId.name"})
public class UserDTO implements BaseDTO<User> {

  public UserDTO() {
  }

  private UserDTO(Integer userId, String userName, String studentId,
                  String password,
                  String school, String nickName, String email, Integer solved,
                  Integer tried,
                  Integer type, String motto, Timestamp lastLogin, Integer departmentId,
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
    this.motto = motto;
    this.lastLogin = lastLogin;
    this.departmentId = departmentId;
    this.departmentName = departmentName;
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
  private String motto;
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

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<UserDTO> {

    private Builder() {
    }

    @Override
    public UserDTO build() {
      return new UserDTO(userId, userName, studentId, password, school,
          nickName, email, solved,
          tried, type, motto, lastLogin, departmentId, departmentName);
    }

    @Override
    public UserDTO build(Map<String, Object> properties) {
      userId = (Integer) properties.get("userId");
      userName = (String) properties.get("userName");
      studentId = (String) properties.get("studentId");
      password = (String) properties.get("password");
      school = (String) properties.get("school");
      nickName = (String) properties.get("nickName");
      email = (String) properties.get("email");
      solved = (Integer) properties.get("solved");
      tried = (Integer) properties.get("tried");
      type = (Integer) properties.get("type");
      motto = (String) properties.get("motto");
      lastLogin = (Timestamp) properties.get("lastLogin");
      departmentId = (Integer) properties.get("departmentId");
      departmentName = (String) properties.get("departmentByDepartmentId.name");
      return build();

    }

    private Integer userId = 2;
    private String userName = "admin";
    private String studentId = "20131010";
    private String password = "password";
    private String school = "school";
    private String nickName = "nickName";
    private String email = "email";
    private Integer solved = 0;
    private Integer tried = 0;
    private Integer type = 0;
    private String motto = "";
    private Timestamp lastLogin = new Timestamp(new Date().getTime());
    private Integer departmentId = 1;
    private String departmentName = "department";

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

    public String getStudentId() {
      return studentId;
    }

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public String getPassword() {
      return password;
    }

    public Builder setPassword(String password) {
      this.password = password;
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

    public Integer getSolved() {
      return solved;
    }

    public Builder setSolved(Integer solved) {
      this.solved = solved;
      return this;
    }

    public Integer getTried() {
      return tried;
    }

    public Builder setTried(Integer tried) {
      this.tried = tried;
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

    public Timestamp getLastLogin() {
      return lastLogin;
    }

    public Builder setLastLogin(Timestamp lastLogin) {
      this.lastLogin = lastLogin;
      return this;
    }

    public Integer getDepartmentId() {
      return departmentId;
    }

    public Builder setDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }

    public String getDepartmentName() {
      return departmentName;
    }

    public Builder setDepartmentName(String departmentName) {
      this.departmentName = departmentName;
      return this;
    }
  }
}
