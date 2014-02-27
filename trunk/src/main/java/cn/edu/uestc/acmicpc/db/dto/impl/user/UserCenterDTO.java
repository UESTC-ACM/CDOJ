package cn.edu.uestc.acmicpc.db.dto.impl.user;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO used in user center.
 * <br/>
 * <code>@Fields({"userId", "userName", "nickName", "email", "school", "departmentId",
 * "departmentByDepartmentId.name", "studentId", "tried", "solved",
 * "type", "motto", "lastLogin" })</code>
 */
@Fields({"userId", "userName", "nickName", "email", "school", "departmentId",
    "departmentByDepartmentId.name", "studentId", "tried", "solved",
    "type", "motto", "lastLogin"})
public class UserCenterDTO implements BaseDTO<User> {

  private Integer userId;
  private String userName;
  private String nickName;
  private String email;
  private String school;
  private String department;
  private Integer departmentId;
  private String studentId;
  private Integer tried;
  private Integer solved;
  private Integer type;
  private String motto;
  private Timestamp lastLogin;

  public UserCenterDTO() {
  }

  public UserCenterDTO(Integer userId, String userName, String nickName,
                       String email, String school, String department, Integer departmentId,
                       String studentId, Integer tried, Integer solved, Integer type, String motto,
                       Timestamp lastLogin) {
    this.userId = userId;
    this.userName = userName;
    this.nickName = nickName;
    this.email = email;
    this.school = school;
    this.department = department;
    this.departmentId = departmentId;
    this.studentId = studentId;
    this.tried = tried;
    this.solved = solved;
    this.type = type;
    this.motto = motto;
    this.lastLogin = lastLogin;
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

  public String getDepartment() {
    return department;
  }

  public void setDepartment(String department) {
    this.department = department;
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

  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<UserCenterDTO> {

    private Builder() {
    }

    private Integer userId;
    private String userName;
    private String nickName;
    private String email;
    private String school;
    private String department;
    private Integer departmentId;
    private String studentId;
    private Integer tried;
    private Integer solved;
    private Integer type;
    private String motto;
    private Timestamp lastLogin;

    @Override
    public UserCenterDTO build() {
      return new UserCenterDTO(userId, userName, nickName,
          email, school, department, departmentId,
          studentId, tried, solved, type, motto,
          lastLogin);
    }

    @Override
    public UserCenterDTO build(Map<String, Object> properties) {
      userId = (Integer) properties.get("userId");
      email = (String) properties.get("email");
      userName = (String) properties.get("userName");
      nickName = (String) properties.get("nickName");
      type = (Integer) properties.get("type");
      school = (String) properties.get("school");
      departmentId = (Integer) properties.get("departmentId");
      department = (String) properties.get("departmentByDepartmentId.name");
      studentId = (String) properties.get("studentId");
      lastLogin = (Timestamp) properties.get("lastLogin");
      solved = (Integer) properties.get("solved");
      motto = (String) properties.get("motto");
      tried = (Integer) properties.get("tried");
      return build();
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

    public String getDepartment() {
      return department;
    }

    public void setDepartment(String department) {
      this.department = department;
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

    public Integer getTried() {
      return tried;
    }

    public void setTried(Integer tried) {
      this.tried = tried;
    }

    public Integer getSolved() {
      return solved;
    }

    public void setSolved(Integer solved) {
      this.solved = solved;
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

    public Builder setMotto(String motto) {
      this.motto = motto;
      return this;
    }

    public Timestamp getLastLogin() {
      return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
      this.lastLogin = lastLogin;
    }
  }
}
