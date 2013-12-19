package cn.edu.uestc.acmicpc.db.dto.impl.user;

import java.sql.Timestamp;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

/**
 * DTO used in user admin page.
 * <br/>
 * <code>@Fields({"userId","userName","nickName","email","type","lastLogin", "school", "departmentId",
 *   "studentId"})</code>
 */
@Fields({"userId","userName","nickName","email","type","lastLogin", "school", "departmentId",
    "studentId"})
public class UserAdminSummaryDTO implements BaseDTO<User>{

  private Integer userId;
  private String userName;
  private String nickName;
  private String email;
  private Integer type;
  private String typeName;
  private Timestamp lastLogin;
  private String school;
  private Integer departmentId;
  private String studentId;

  public UserAdminSummaryDTO() {
  }

  public UserAdminSummaryDTO(Integer userId, String userName, String nickName,
      String email, Integer type, String typeName, Timestamp lastLogin, String school,
      Integer departmentId, String studentId) {
    this.userId = userId;
    this.userName = userName;
    this.nickName = nickName;
    this.email = email;
    this.type = type;
    this.typeName = typeName;
    this.lastLogin = lastLogin;
    this.school = school;
    this.departmentId = departmentId;
    this.studentId = studentId;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Timestamp getLastLogin() {
    return lastLogin;
  }

  public void setLastLogin(Timestamp lastLogin) {
    this.lastLogin = lastLogin;
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

  public static class Builder implements BaseBuilder<UserAdminSummaryDTO> {

    private Builder(){
    }
    private Integer userId;
    private String userName="";
    private String nickName="";
    private String email="";
    private Integer type;
    private String typeName = "";
    private Timestamp lastLogin;
    private String school;
    private Integer departmentId;
    private String studentId;

    @Override
    public UserAdminSummaryDTO build() {
      return new UserAdminSummaryDTO(userId, userName, nickName, email, type, typeName, lastLogin,
          school, departmentId, studentId);
    }

    @Override
    public UserAdminSummaryDTO build(Map<String, Object> properties) {
      userId = (Integer) properties.get("userId");
      userName = (String) properties.get("userName");
      nickName = (String) properties.get("nickName");
      email = (String) properties.get("email");
      type = (Integer) properties.get("type");
      lastLogin = (Timestamp) properties.get("lastLogin");
      school = (String) properties.get("school");
      departmentId = (Integer) properties.get("departmentId");
      studentId = (String) properties.get("studentId");
      return build();
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
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

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public Builder setLastLogin(Timestamp lastLogin) {
      this.lastLogin = lastLogin;
      return this;
    }

    public Builder setTypeName(String typeName) {
      this.typeName = typeName;
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
  }
}
