package cn.edu.uestc.acmicpc.db.dto.impl.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.ObjectUtil;

/**
 * Data transfer object for {@link User}.
 */
public class UserRegisterDTO implements BaseDTO<User> {

  /**
   * Input: user id, set null for new user
   */
  private Integer userId;

  /**
   * Input: user name
   */
  @NotNull(message = "Please enter your user name.")
  @Pattern(regexp = "\\b^[a-zA-Z0-9_]{4,24}$\\b",
      message = "Please enter 4-24 characters consist of A-Z, a-z, 0-9 and '_'.")
  private String userName;

  /**
   * Input: password
   */
  @Length(min = 6, max = 20, message = "Please enter 6-20 characters.")
  private String password;

  /**
   * Input: repeat password
   */
  @Length(min = 6, max = 20, message = "Please enter 6-20 characters.")
  private String passwordRepeat;

  /**
   * Input: nick name
   */
  @NotNull(message = "Please enter your nick name.")
  @Length(min = 2, max = 20, message = "Please enter 2-20 characters.")
  private String nickName;

  /**
   * Input: email
   */
  @NotEmpty(message = "Please enter a valid email address.")
  @Email(message = "Please enter a valid email address.")
  private String email;

  /**
   * Input: school
   */
  @NotNull(message = "Please enter your school name.")
  @Length(min = 1, max = 100, message = "Please enter 1-100 characters.")
  private String school;

  /**
   * Input: departmentId
   */
  @NotNull(message = "Please select your department.")
  private Integer departmentId;

  /**
   * Input: student ID
   */
  @NotNull(message = "Please enter your student ID.")
  @Length(min = 1, max = 20, message = "Please enter 1-20 characters.")
  private String studentId;

  public UserRegisterDTO() {
  }

  @Override
  public boolean equals(Object obj) {
    if (obj instanceof UserRegisterDTO) {
      boolean result = true;
      UserRegisterDTO dto = (UserRegisterDTO) obj;
      result &= ObjectUtil.equals(userName, dto.userName);
      result &= ObjectUtil.equals(nickName, dto.nickName);
      result &= ObjectUtil.equals(email, dto.email);
      result &= ObjectUtil.equals(school, dto.school);
      result &= ObjectUtil.equals(departmentId, dto.departmentId);
      result &= ObjectUtil.equals(studentId, dto.studentId);
      return result;
    }
    return false;
  }

  private UserRegisterDTO(Integer userId, String userName, String password,
                          String passwordRepeat, String nickName, String email, String school, Integer departmentId,
                          String studentId) {
    this.userId = userId;
    this.userName = userName;
    this.password = password;
    this.passwordRepeat = passwordRepeat;
    this.nickName = nickName;
    this.email = email;
    this.school = school;
    this.departmentId = departmentId;
    this.studentId = studentId;
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

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link UserRegisterDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer userId = 2;
    private String userName = "admin";
    private String password = "password";
    private String passwordRepeat = "password";
    private String nickName = "admin";
    private String email = "acm_admin@uestc.edu.cn";
    private String school = "UESTC";
    private Integer departmentId = 1;
    private String studentId = "2010013100008";

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
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

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public UserRegisterDTO build() {
      return new UserRegisterDTO(userId, userName, password, passwordRepeat, nickName, email,
          school, departmentId, studentId);
    }
  }
}
