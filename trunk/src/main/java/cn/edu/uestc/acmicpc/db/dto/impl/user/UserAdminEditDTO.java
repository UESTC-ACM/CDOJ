package cn.edu.uestc.acmicpc.db.dto.impl.user;

import java.util.Map;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

@Fields({"userId", "userName", "nickName", "email", "school", "departmentId", 
  "studentId", "type"})
public class UserAdminEditDTO implements BaseDTO<User>{
  
  private Integer userId;
  
  private String userName;

  @NotNull(message = "Please enter your nick name.")
  @Length(min = 2, max = 20, message = "Please enter 2-20 characters.")
  private String nickName;

  private String email;

  @NotNull(message = "Please enter your school name.")
  @Length(min = 1, max = 100, message = "Please enter 1-100 characters.")
  private String school;

  @NotNull(message = "Please select your department.")
  private Integer departmentId;

  @NotNull(message = "Please enter your student ID.")
  @Length(min = 1, max = 20, message = "Please enter 1-20 characters.")
  private String studentId;
  
  @NotNull(message = "Please enter the user type.")
  private Integer type;

  public UserAdminEditDTO(){
  }
  
  public UserAdminEditDTO(Integer userId, String userName, String nickName, String email, 
      String school, Integer departmentId, String studentId, Integer type){
    this.userId = userId;
    this.userName = userName;
    this.nickName = nickName;
    this.email = email;
    this.school = school;
    this.departmentId = departmentId;
    this.studentId = studentId;
    this.type = type;
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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
  
  public static Builder builder() {
    return new Builder();
  }
  
  public static class Builder implements BaseBuilder<UserAdminEditDTO> {
    
    private Integer userId;   
    private String userName;
    private String nickName;
    private String email;
    private String school;
    private Integer departmentId;
    private String studentId;
    private Integer type;
    
    private Builder() {
    }
    
    @Override
    public UserAdminEditDTO build() {
      return new UserAdminEditDTO(userId, userName, nickName, email, school, 
          departmentId, studentId, type);
    }
    @Override
    public UserAdminEditDTO build(Map<String, Object> properties) {
      userId = (Integer) properties.get("userId");
      userName = (String) properties.get("userName");
      nickName = (String) properties.get("nickName");
      email = (String) properties.get("email");
      school = (String) properties.get("school");
      departmentId = (Integer) properties.get("departmentId");
      studentId = (String) properties.get("studentId");
      type = (Integer) properties.get("type");
      return build();
    }
    
    public Builder getUserId(Integer userId) {
      this.userId = userId;
      return this;
    }
    
    public Builder getUserName(String userName) {
      this.userName = userName;
      return this;
    }
    
    public Builder getNickName(String nickName) {
      this.nickName = nickName;
      return this;
    }
    
    public Builder getEmail(String email) {
      this.email = email;
      return this;
    }
    
    public Builder getSchool(String school) {
      this.school = school;
      return this;
    }
    
    public Builder getDepartmentId(Integer departmentId) {
      this.departmentId = departmentId;
      return this;
    }
    
    public Builder getStudentId(String studentId) {
      this.studentId = studentId;
      return this;
    }
    
    public Builder getType(Integer type) {
      this.type = type;
      return this;
    }
  }
}
