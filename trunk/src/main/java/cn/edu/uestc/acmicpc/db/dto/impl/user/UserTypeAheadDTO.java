package cn.edu.uestc.acmicpc.db.dto.impl.user;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO used in user type ahead search list.
 * <br/>
 * <code>@Fields({"userId", "email", "userName", "nickName"})</code>
 */
@Fields({"userId", "email", "userName", "nickName"})
public class UserTypeAheadDTO implements BaseDTO<User> {

  private Integer userId;
  private String email;
  private String userName;
  private String nickName;

  public UserTypeAheadDTO() {
  }

  public UserTypeAheadDTO(Integer userId, String email, String userName, String nickName) {
    this.userId = userId;
    this.email = email;
    this.userName = userName;
    this.nickName = nickName;
  }

  public static Builder builder() {
    return new Builder();
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserTypeAheadDTO that = (UserTypeAheadDTO) o;

    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }
    if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) {
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
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    return result;
  }

  public static class Builder implements BaseBuilder<UserTypeAheadDTO> {

    private Integer userId;
    private String email;
    private String userName;
    private String nickName;

    private Builder() {
    }

    @Override
    public UserTypeAheadDTO build() {
      return new UserTypeAheadDTO(userId, email, userName, nickName);
    }

    @Override
    public UserTypeAheadDTO build(Map<String, Object> properties) {
      userId = (Integer) properties.get("userId");
      email = (String) properties.get("email");
      userName = (String) properties.get("userName");
      nickName = (String) properties.get("nickName");
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
  }
}
