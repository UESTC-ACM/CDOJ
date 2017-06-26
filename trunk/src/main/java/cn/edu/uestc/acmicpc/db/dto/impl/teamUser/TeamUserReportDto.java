package cn.edu.uestc.acmicpc.db.dto.impl.teamUser;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.GenderType;
import cn.edu.uestc.acmicpc.util.enums.GradeType;
import cn.edu.uestc.acmicpc.util.enums.TShirtsSizeType;
import java.util.Map;
import java.util.Objects;

@Fields({ "teamUserId", "teamId", "userId", "allow", "userByUserId.userName",
    "userByUserId.nickName", "userByUserId.name", "userByUserId.sex",
    "userByUserId.size", "userByUserId.email", "userByUserId.phone",
    "userByUserId.school", "userByUserId.departmentByDepartmentId.name",
    "userByUserId.grade", "userByUserId.studentId", "userByUserId.type" })
public class TeamUserReportDto implements BaseDto<TeamUser> {

  private Integer teamUserId;
  private Integer teamId;
  private Integer userId;
  private Boolean allow;
  private String userName;
  private String nickName;
  private String name;
  private String sex;
  private String size;
  private String email;
  private String phone;
  private String school;
  private String department;
  private String grade;
  private String studentId;
  private String type;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeamUserReportDto)) {
      return false;
    }

    TeamUserReportDto that = (TeamUserReportDto) o;
    return Objects.equals(this.allow, that.allow)
        && Objects.equals(this.department, that.department)
        && Objects.equals(this.email, that.email)
        && Objects.equals(this.grade, that.grade)
        && Objects.equals(this.name, that.name)
        && Objects.equals(this.nickName, that.nickName)
        && Objects.equals(this.phone, that.phone)
        && Objects.equals(this.school, that.school)
        && Objects.equals(this.sex, that.sex)
        && Objects.equals(this.size, that.size)
        && Objects.equals(this.studentId, that.studentId)
        && Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamUserId, that.teamUserId)
        && Objects.equals(this.type, that.type)
        && Objects.equals(this.userId, that.userId)
        && Objects.equals(this.userName, that.userName);
  }

  @Override
  public int hashCode() {
    int result = teamUserId != null ? teamUserId.hashCode() : 0;
    result = 31 * result + (teamId != null ? teamId.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (allow != null ? allow.hashCode() : 0);
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    result = 31 * result + (name != null ? name.hashCode() : 0);
    result = 31 * result + (sex != null ? sex.hashCode() : 0);
    result = 31 * result + (size != null ? size.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (phone != null ? phone.hashCode() : 0);
    result = 31 * result + (school != null ? school.hashCode() : 0);
    result = 31 * result + (department != null ? department.hashCode() : 0);
    result = 31 * result + (grade != null ? grade.hashCode() : 0);
    result = 31 * result + (studentId != null ? studentId.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    return result;
  }

  public TeamUserReportDto(Integer teamUserId, Integer teamId, Integer userId,
      Boolean allow, String userName, String nickName,
      String name, String sex, String size, String email,
      String phone, String school, String department,
      String grade, String studentId, String type) {
    this.teamUserId = teamUserId;
    this.teamId = teamId;
    this.userId = userId;
    this.allow = allow;
    this.userName = userName;
    this.nickName = nickName;
    this.name = name;
    this.sex = sex;
    this.size = size;
    this.email = email;
    this.phone = phone;
    this.school = school;
    this.department = department;
    this.grade = grade;
    this.studentId = studentId;
    this.type = type;
  }

  public Integer getTeamUserId() {

    return teamUserId;
  }

  public void setTeamUserId(Integer teamUserId) {
    this.teamUserId = teamUserId;
  }

  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Boolean getAllow() {
    return allow;
  }

  public void setAllow(Boolean allow) {
    this.allow = allow;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public String getSize() {
    return size;
  }

  public void setSize(String size) {
    this.size = size;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public String getGrade() {
    return grade;
  }

  public void setGrade(String grade) {
    this.grade = grade;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<TeamUserReportDto> {

    private Integer teamUserId;
    private Integer teamId;
    private Integer userId;
    private Boolean allow;
    private String userName;
    private String nickName;
    private String name;
    private String sex;
    private String size;
    private String email;
    private String phone;
    private String school;
    private String department;
    private String grade;
    private String studentId;
    private String type;

    public Builder setTeamUserId(Integer teamUserId) {
      this.teamUserId = teamUserId;
      return this;
    }

    public Builder setTeamId(Integer teamId) {
      this.teamId = teamId;
      return this;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setAllow(Boolean allow) {
      this.allow = allow;
      return this;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder setNickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

    public Builder setSex(String sex) {
      this.sex = sex;
      return this;
    }

    public Builder setSize(String size) {
      this.size = size;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setPhone(String phone) {
      this.phone = phone;
      return this;
    }

    public Builder setSchool(String school) {
      this.school = school;
      return this;
    }

    public Builder setDepartment(String department) {
      this.department = department;
      return this;
    }

    public Builder setGrade(String grade) {
      this.grade = grade;
      return this;
    }

    public Builder setStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }

    public Builder setType(String type) {
      this.type = type;
      return this;
    }

    @Override
    public TeamUserReportDto build() {
      return new TeamUserReportDto(teamUserId, teamId, userId, allow, userName,
          nickName, name, sex, size, email, phone, school, department, grade,
          studentId, type);
    }

    @Override
    public TeamUserReportDto build(Map<String, Object> properties) {
      teamUserId = (Integer) properties.get("teamUserId");
      teamId = (Integer) properties.get("teamId");
      userId = (Integer) properties.get("userId");
      allow = (Boolean) properties.get("allow");
      userName = (String) properties.get("userByUserId.userName");
      nickName = (String) properties.get("userByUserId.nickName");
      name = (String) properties.get("userByUserId.name");
      email = (String) properties.get("userByUserId.email");
      phone = (String) properties.get("userByUserId.phone");
      school = (String) properties.get("userByUserId.school");
      studentId = (String) properties.get("userByUserId.studentId");
      department = (String) properties.get("userByUserId.departmentByDepartmentId.name");

      Integer tempValue;

      tempValue = (Integer) properties.get("userByUserId.sex");
      sex = GenderType.values()[tempValue].getDescription();
      tempValue = (Integer) properties.get("userByUserId.size");
      size = TShirtsSizeType.values()[tempValue].getDescription();
      tempValue = (Integer) properties.get("userByUserId.grade");
      grade = GradeType.values()[tempValue].getDescription();
      tempValue = (Integer) properties.get("userByUserId.type");
      type = AuthenticationType.values()[tempValue].getDescription();

      return build();
    }
  }
}
