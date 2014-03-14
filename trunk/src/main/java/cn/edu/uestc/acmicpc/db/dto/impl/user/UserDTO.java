package cn.edu.uestc.acmicpc.db.dto.impl.user;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * DTO for user entity. <br/>
 * <code>@Fields({ "userId", "userName", "studentId", "password", "school", "nickName", "email", "solved",
 * "tried", "type", "motto", "lastLogin", "departmentId", "departmentByDepartmentId.name" })</code>
 */
@Fields({"userId", "userName", "studentId", "password", "school", "nickName",
    "email", "solved",
    "tried", "type", "motto", "lastLogin", "departmentId",
    "departmentByDepartmentId.name", "name", "sex", "grade", "phone", "size"})
public class UserDTO implements BaseDTO<User> {

  public UserDTO() {
  }

  public UserDTO(Integer userId, String userName, String studentId, String password, String school,
                 String nickName, String email, Integer solved, Integer tried, Integer type,
                 String motto, Timestamp lastLogin, Integer departmentId, String departmentName,
                 String name, Integer sex, Integer grade, String phone, Integer size) {
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
    this.name = name;
    this.sex = sex;
    this.grade = grade;
    this.phone = phone;
    this.size = size;
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
  private String name;
  private Integer sex;
  private Integer grade;
  private String phone;
  private Integer size;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Integer getSex() {
    return sex;
  }

  public void setSex(Integer sex) {
    this.sex = sex;
  }

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
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
          tried, type, motto, lastLogin, departmentId, departmentName, name, sex, grade, phone, size);
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
      name = (String) properties.get("name");
      sex = (Integer) properties.get("sex");
      grade = (Integer) properties.get("grade");
      phone = (String) properties.get("phone");
      size = (Integer) properties.get("size");
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
    private String name = "a";
    private Integer sex = 0;
    private Integer grade = 0;
    private String phone = "123";
    private Integer size = 0;

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

    public String getName() {
      return name;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Integer getSex() {
      return sex;
    }

    public Builder setSex(Integer sex) {
      this.sex = sex;
      return this;
    }

    public Integer getGrade() {
      return grade;
    }

    public Builder setGrade(Integer grade) {
      this.grade = grade;
      return this;
    }

    public String getPhone() {
      return phone;
    }

    public Builder setPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public Integer getSize() {
      return size;
    }

    public Builder setSize(Integer size) {
      this.size = size;
      return this;
    }
  }
}
