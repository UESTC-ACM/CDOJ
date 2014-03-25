package cn.edu.uestc.acmicpc.db.dto.impl.user;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * DTO post from user editor.
 */
public class UserAdminEditDTO {
  private Integer userId;

  private String userName;

  @NotNull(message = "Please enter the nick name.")
  @Length(min = 2, max = 20, message = "Please enter 2-20 characters.")
  private String nickName;

  private String email;

  @NotNull(message = "Please enter the school name.")
  @Length(min = 1, max = 100, message = "Please enter 1-100 characters.")
  private String school;

  @NotNull(message = "Please select the department.")
  private Integer departmentId;

  @NotNull(message = "Please enter the student ID.")
  @Length(min = 1, max = 20, message = "Please enter 1-20 characters.")
  private String studentId;

  private String motto;

  @Length(min = 40, max = 40, message = "Please enter new password.")
  private String newPassword;

  @Length(min = 40, max = 40, message = "Please repeat new password.")
  private String newPasswordRepeat;

  @NotNull(message = "Please select the user type.")
  private Integer type;

  @NotNull(message = "Please enter your name.")
  @Length(min = 1, max = 50, message = "Please enter 1-50 characters.")
  private String name;

  @NotNull(message = "Please select your gender.")
  private Integer sex;

  @NotNull(message = "Please select your grade.")
  private Integer grade;

  @NotNull(message = "Please enter your phone number.")
  @Length(min = 1, max = 45, message = "Please enter 1-45 characters.")
  private String phone;

  @NotNull(message = "Please select your T-Shirts size.")
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

  public String getMotto() {
    return motto;
  }

  public void setMotto(String motto) {
    this.motto = motto;
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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public UserAdminEditDTO() {
  }

  private UserAdminEditDTO(Integer userId, String userName, String nickName, String email, String school,
                           String motto, Integer departmentId, String studentId, String newPassword,
                           String newPasswordRepeat, Integer type, String name, Integer sex,
                           Integer grade, String phone, Integer size) {
    this.userId = userId;
    this.userName = userName;
    this.nickName = nickName;
    this.email = email;
    this.school = school;
    this.motto = motto;
    this.departmentId = departmentId;
    this.studentId = studentId;
    this.newPassword = newPassword;
    this.newPasswordRepeat = newPasswordRepeat;
    this.type = type;
    this.name = name;
    this.sex = sex;
    this.grade = grade;
    this.phone = phone;
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserAdminEditDTO that = (UserAdminEditDTO) o;

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
    if (newPassword != null ? !newPassword.equals(that.newPassword) : that.newPassword != null) {
      return false;
    }
    if (newPasswordRepeat != null ? !newPasswordRepeat.equals(that.newPasswordRepeat) : that.newPasswordRepeat != null) {
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
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) {
      return false;
    }
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (school != null ? school.hashCode() : 0);
    result = 31 * result + (departmentId != null ? departmentId.hashCode() : 0);
    result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
    result = 31 * result + (motto != null ? motto.hashCode() : 0);
    result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
    result = 31 * result + (newPasswordRepeat != null ? newPasswordRepeat.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
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

  public static class Builder {

    private Builder() {
    }

    public UserAdminEditDTO build() {
      return new UserAdminEditDTO(userId, userName, nickName, email, school, motto, departmentId,
          studentId, newPassword, newPasswordRepeat, type, name, sex, grade, phone, size);
    }

    private Integer userId;
    private String userName;
    private String nickName;
    private String email;
    private String school;
    private String motto;
    private Integer departmentId;
    private String studentId;
    private String newPassword;
    private String newPasswordRepeat;
    private Integer type;
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

    public String getStudentId() {
      return studentId;
    }

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public String getNewPassword() {
      return newPassword;
    }

    public Builder setNewPassword(String newPassword) {
      this.newPassword = newPassword;
      return this;
    }

    public String getNewPasswordRepeat() {
      return newPasswordRepeat;
    }

    public Builder setNewPasswordRepeat(String newPasswordRepeat) {
      this.newPasswordRepeat = newPasswordRepeat;
      return this;
    }

    public Integer getType() {
      return type;
    }

    public Builder setType(Integer type) {
      this.type = type;
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
