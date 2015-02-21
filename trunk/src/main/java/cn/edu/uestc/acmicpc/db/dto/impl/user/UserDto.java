package cn.edu.uestc.acmicpc.db.dto.impl.user;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;

/**
 * Dto for user entity.
 */
@Fields({ "userId", "userName", "studentId", "password", "school", "nickName",
    "email", "solved",
    "tried", "type", "motto", "lastLogin", "departmentId",
    "departmentByDepartmentId.name", "name", "sex", "grade", "phone", "size" })
public class UserDto implements BaseDto<User> {

  public UserDto() {
  }

  public UserDto(Integer userId, String userName, String studentId, String password, String school,
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

  @Override
  public String toString() {
    return this.userName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserDto userDto = (UserDto) o;

    if (departmentId != null ? !departmentId.equals(userDto.departmentId)
        : userDto.departmentId != null) {
      return false;
    }
    if (departmentName != null ? !departmentName.equals(userDto.departmentName)
        : userDto.departmentName != null) {
      return false;
    }
    if (email != null ? !email.equals(userDto.email) : userDto.email != null) {
      return false;
    }
    if (grade != null ? !grade.equals(userDto.grade) : userDto.grade != null) {
      return false;
    }
    if (motto != null ? !motto.equals(userDto.motto) : userDto.motto != null) {
      return false;
    }
    if (name != null ? !name.equals(userDto.name) : userDto.name != null) {
      return false;
    }
    if (nickName != null ? !nickName.equals(userDto.nickName) : userDto.nickName != null) {
      return false;
    }
    if (password != null ? !password.equals(userDto.password) : userDto.password != null) {
      return false;
    }
    if (phone != null ? !phone.equals(userDto.phone) : userDto.phone != null) {
      return false;
    }
    if (school != null ? !school.equals(userDto.school) : userDto.school != null) {
      return false;
    }
    if (sex != null ? !sex.equals(userDto.sex) : userDto.sex != null) {
      return false;
    }
    if (size != null ? !size.equals(userDto.size) : userDto.size != null) {
      return false;
    }
    if (solved != null ? !solved.equals(userDto.solved) : userDto.solved != null) {
      return false;
    }
    if (studentId != null ? !studentId.equals(userDto.studentId) : userDto.studentId != null) {
      return false;
    }
    if (tried != null ? !tried.equals(userDto.tried) : userDto.tried != null) {
      return false;
    }
    if (type != null ? !type.equals(userDto.type) : userDto.type != null) {
      return false;
    }
    if (userId != null ? !userId.equals(userDto.userId) : userDto.userId != null) {
      return false;
    }
    if (userName != null ? !userName.equals(userDto.userName) : userDto.userName != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
    result = 31 * result + (password != null ? password.hashCode() : 0);
    result = 31 * result + (school != null ? school.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (solved != null ? solved.hashCode() : 0);
    result = 31 * result + (tried != null ? tried.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (motto != null ? motto.hashCode() : 0);
    result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
    result = 31 * result + (departmentName != null ? departmentName.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (sex != null ? sex.hashCode() : 0);
    result = 31 * result + (grade != null ? grade.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    result = 31 * result + (size != null ? size.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<UserDto> {

    private Builder() {
    }

    @Override
    public UserDto build() {
      return new UserDto(userId, userName, studentId, password, school,
          nickName, email, solved,
          tried, type, motto, lastLogin, departmentId, departmentName, name, sex, grade, phone,
          size);
    }

    @Override
    public UserDto build(Map<String, Object> properties) {
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
