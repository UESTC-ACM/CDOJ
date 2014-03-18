package cn.edu.uestc.acmicpc.db.dto.impl.user;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO used in user summary list.
 * <br/>
 * <code>@Fields({ "userId", "email", "userName", "nickName", "type", "school", "motto", "lastLogin", "solved",
 * "tried" })</code>
 */
@Fields({"userId", "email", "userName", "nickName", "type", "school", "motto", "lastLogin", "solved",
    "tried"})
public class UserListDTO implements BaseDTO<User> {

  private Integer userId;
  private String email;
  private String userName;
  private String nickName;
  private Integer type;
  private String school;
  private String motto;
  private Timestamp lastLogin;
  private Integer solved;
  private Integer tried;

  public UserListDTO() {
  }

  private UserListDTO(Integer userId, String email, String userName, String nickName,
                      Integer type, String school, String motto, Timestamp lastLogin, Integer solved, Integer tried) {
    this.userId = userId;
    this.email = email;
    this.userName = userName;
    this.nickName = nickName;
    this.type = type;
    this.school = school;
    this.motto = motto;
    this.lastLogin = lastLogin;
    this.solved = solved;
    this.tried = tried;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
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

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getSchool() {
    return school;
  }

  public void setSchool(String school) {
    this.school = school;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    UserListDTO that = (UserListDTO) o;

    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    if (motto != null ? !motto.equals(that.motto) : that.motto != null) return false;
    if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) return false;
    if (school != null ? !school.equals(that.school) : that.school != null) return false;
    if (solved != null ? !solved.equals(that.solved) : that.solved != null) return false;
    if (tried != null ? !tried.equals(that.tried) : that.tried != null) return false;
    if (type != null ? !type.equals(that.type) : that.type != null) return false;
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = userId != null ? userId.hashCode() : 0;
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (school != null ? school.hashCode() : 0);
    result = 31 * result + (motto != null ? motto.hashCode() : 0);
    result = 31 * result + (solved != null ? solved.hashCode() : 0);
    result = 31 * result + (tried != null ? tried.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<UserListDTO> {

    private Builder() {
    }

    private Integer userId;
    private String email;
    private String userName;
    private String nickName;
    private Integer type;
    private String school;
    private String motto;
    private Timestamp lastLogin;
    private Integer solved;
    private Integer tried;

    @Override
    public UserListDTO build() {
      return new UserListDTO(userId, email, userName, nickName, type, school,
          motto, lastLogin, solved, tried);
    }

    @Override
    public UserListDTO build(Map<String, Object> properties) {
      userId = (Integer) properties.get("userId");
      email = (String) properties.get("email");
      userName = (String) properties.get("userName");
      nickName = (String) properties.get("nickName");
      type = (Integer) properties.get("type");
      school = (String) properties.get("school");
      motto = (String) properties.get("motto");
      lastLogin = (Timestamp) properties.get("lastLogin");
      solved = (Integer) properties.get("solved");
      tried = (Integer) properties.get("tried");
      return build();
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
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

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public Builder setSchool(String school) {
      this.school = school;
      return this;
    }

    public Builder setMotto(String motto) {
      this.motto = motto;
      return this;
    }

    public Builder setLastLogin(Timestamp lastLogin) {
      this.lastLogin = lastLogin;
      return this;
    }

    public Builder setSolved(Integer solved) {
      this.solved = solved;
      return this;
    }

    public Builder setTried(Integer tried) {
      this.tried = tried;
      return this;
    }
  }
}
