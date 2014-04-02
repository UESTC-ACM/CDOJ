package cn.edu.uestc.acmicpc.db.dto.impl.teamUser;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.enums.GenderType;
import cn.edu.uestc.acmicpc.util.enums.GradeType;
import cn.edu.uestc.acmicpc.util.enums.TShirtsSizeType;

import java.util.Map;

@Fields({"teamUserId", "teamId", "userId", "allow", "userByUserId.userName",
    "userByUserId.nickName", "userByUserId.name", "userByUserId.sex",
    "userByUserId.size", "userByUserId.email", "userByUserId.phone",
    "userByUserId.school", "userByUserId.departmentByDepartmentId.name",
    "userByUserId.grade", "userByUserId.studentId", "userByUserId.type"})
public class TeamUserReportDTO implements BaseDTO<TeamUser> {

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
    if (this == o) return true;
    if (!(o instanceof TeamUserReportDTO)) return false;

    TeamUserReportDTO that = (TeamUserReportDTO) o;

    if (allow != null ? !allow.equals(that.allow) : that.allow != null) {
      return false;
    }
    if (department != null ? !department.equals(that.department) : that.department != null) {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }
    if (grade != null ? !grade.equals(that.grade) : that.grade != null) {
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
    if (teamId != null ? !teamId.equals(that.teamId) : that.teamId != null) {
      return false;
    }
    if (teamUserId != null ? !teamUserId.equals(that.teamUserId) : that.teamUserId != null) {
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

  public TeamUserReportDTO(Integer teamUserId, Integer teamId, Integer userId,
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

  public static class Builder implements BaseBuilder<TeamUserReportDTO> {

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
    public TeamUserReportDTO build() {
      return new TeamUserReportDTO(teamUserId, teamId, userId, allow, userName,
          nickName, name, sex, size, email, phone, school, department, grade,
          studentId, type);
    }

    @Override
    public TeamUserReportDTO build(Map<String, Object> properties) {
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
