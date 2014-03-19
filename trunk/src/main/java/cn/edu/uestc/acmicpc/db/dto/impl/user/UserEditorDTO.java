package cn.edu.uestc.acmicpc.db.dto.impl.user;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO for user entity. <br/>
 * <code>@Fields({ "userName", "studentId", "school", "nickName", "email",
 * "type", "motto", "departmentId"})</code>
 */
@Fields({"userName", "studentId", "school", "nickName",
    "email", "type", "motto", "departmentId", "name", "sex", "grade", "phone", "size"})
public class UserEditorDTO implements BaseDTO<User> {

  public UserEditorDTO() {
  }

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

  public UserEditorDTO(String userName, String studentId, String school, String nickName,
                       String email, Integer type, String motto, Integer departmentId, String name,
                       Integer sex, Integer grade, String phone, Integer size) {

    this.userName = userName;
    this.studentId = studentId;
    this.school = school;
    this.nickName = nickName;
    this.email = email;
    this.type = type;
    this.motto = motto;
    this.departmentId = departmentId;
    this.name = name;
    this.sex = sex;
    this.grade = grade;
    this.phone = phone;
    this.size = size;
  }

  private String userName;
  private String studentId;
  private String school;
  private String nickName;
  private String email;
  private Integer type;
  private String motto;
  private Integer departmentId;
  private String name;
  private Integer sex;
  private Integer grade;
  private String phone;
  private Integer size;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserEditorDTO that = (UserEditorDTO) o;

    if (departmentId != null ? !departmentId.equals(that.departmentId) : that.departmentId != null) {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }
    if (grade != null ? !grade.equals(that.grade) : that.grade != null) {
      return false;
    }
    if (motto != null ? !motto.equals(that.motto) : that.motto != null) {
      return false;
    }
    if (name != null ? !name.equals(that.name) : that.name != null) {
      return false;
    }
    if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) {
      return false;
    }
    if (phone != null ? !phone.equals(that.phone) : that.phone != null) {
      return false;
    }
    if (school != null ? !school.equals(that.school) : that.school != null) {
      return false;
    }
    if (sex != null ? !sex.equals(that.sex) : that.sex != null) {
      return false;
    }
    if (size != null ? !size.equals(that.size) : that.size != null) {
      return false;
    }
    if (studentId != null ? !studentId.equals(that.studentId) : that.studentId != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = userName != null ? userName.hashCode() : 0;
    result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
    result = 31 * result + (school != null ? school.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (motto != null ? motto.hashCode() : 0);
    result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
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

  public static class Builder implements BaseBuilder<UserEditorDTO> {

    private Builder() {
    }

    @Override
    public UserEditorDTO build() {
      return new UserEditorDTO(userName, studentId, school,
          nickName, email, type, motto, departmentId, name, sex, grade, phone, size);
    }

    @Override
    public UserEditorDTO build(Map<String, Object> properties) {
      userName = (String) properties.get("userName");
      studentId = (String) properties.get("studentId");
      school = (String) properties.get("school");
      nickName = (String) properties.get("nickName");
      email = (String) properties.get("email");
      type = (Integer) properties.get("type");
      motto = (String) properties.get("motto");
      departmentId = (Integer) properties.get("departmentId");
      name = (String) properties.get("name");
      sex = (Integer) properties.get("sex");
      grade = (Integer) properties.get("grade");
      phone = (String) properties.get("phone");
      size = (Integer) properties.get("size");
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
    private String name = "a";
    private Integer sex = 0;
    private Integer grade = 0;
    private String phone = "123";
    private Integer size = 0;

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