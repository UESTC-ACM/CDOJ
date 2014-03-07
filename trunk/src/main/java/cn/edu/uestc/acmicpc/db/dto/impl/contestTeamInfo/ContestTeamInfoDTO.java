package cn.edu.uestc.acmicpc.db.dto.impl.contestTeamInfo;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeamInfo;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO used in contest team information.
 */
@Fields({"teamId", "userId", "name", "coderName", "sex", "department",
    "grade", "studentId", "phone", "size", "email", "school", "state"})
public class ContestTeamInfoDTO implements BaseDTO<ContestTeamInfo> {

  private Integer teamId;
  private Integer userId;
  private String name;
  private String coderName;
  private String sex;
  private String department;
  private String grade;
  private String studentId;
  private String phone;
  private String size;
  private String email;
  private String school;
  private String state;

  public ContestTeamInfoDTO() {}

  public ContestTeamInfoDTO(Integer teamId, Integer userId, String name,
                            String coderName, String sex, String department,
                            String grade, String studentId, String phone,
                            String size, String email, String school, String state) {
    this.teamId = teamId;
    this.userId = userId;
    this.name = name;
    this.coderName = coderName;
    this.sex = sex;
    this.department = department;
    this.grade = grade;
    this.studentId = studentId;
    this.phone = phone;
    this.size = size;
    this.email = email;
    this.school = school;
    this.state = state;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getCoderName() {
    return coderName;
  }

  public void setCoderName(String coderName) {
    this.coderName = coderName;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
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

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
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

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ContestTeamInfoDTO> {

    private Integer teamId;
    private Integer userId;
    private String name;
    private String coderName;
    private String sex;
    private String department;
    private String grade;
    private String studentId;
    private String phone;
    private String size;
    private String email;
    private String school;
    private String state;

    @Override
    public ContestTeamInfoDTO build() {
      return new ContestTeamInfoDTO(teamId, userId, name, coderName, sex, department,
          grade, studentId, phone, size, email, school, state);
    }

    @Override
    public ContestTeamInfoDTO build(Map<String, Object> properties) {
      teamId = (Integer) properties.get("teamId");
      userId = (Integer) properties.get("userId");
      name = (String) properties.get("name");
      coderName = (String) properties.get("coderName");
      sex = (String) properties.get("sex");
      department = (String) properties.get("department");
      grade = (String) properties.get("grade");
      studentId = (String) properties.get("studentId");
      phone = (String) properties.get("phone");
      size = (String) properties.get("size");
      email = (String) properties.get("email");
      school = (String) properties.get("school");
      state = (String) properties.get("state");
      return build();
    }

    public void setTeamId(Integer teamId) {
      this.teamId = teamId;
    }

    public void setUserId(Integer userId) {
      this.userId = userId;
    }

    public void setName(String name) {
      this.name = name;
    }

    public void setCoderName(String coderName) {
      this.coderName = coderName;
    }

    public void setSex(String sex) {
      this.sex = sex;
    }

    public void setDepartment(String department) {
      this.department = department;
    }

    public void setGrade(String grade) {
      this.grade = grade;
    }

    public void setStudentId(String studentId) {
      this.studentId = studentId;
    }

    public void setPhone(String phone) {
      this.phone = phone;
    }

    public void setSize(String size) {
      this.size = size;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public void setSchool(String school) {
      this.school = school;
    }

    public void setState(String state) {
      this.state = state;
    }
  }
}
