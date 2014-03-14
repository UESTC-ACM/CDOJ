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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<UserTypeAheadDTO> {

    private Builder() {
    }

    private Integer userId;
    private String email;
    private String userName;
    private String nickName;

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
