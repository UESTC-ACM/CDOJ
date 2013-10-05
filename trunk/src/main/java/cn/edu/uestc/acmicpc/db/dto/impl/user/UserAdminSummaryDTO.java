package cn.edu.uestc.acmicpc.db.dto.impl.user;

import java.sql.Timestamp;

import java.util.Map;



import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

@Fields({"userId","userName","nickName","email","type","lastLogin"})
public class UserAdminSummaryDTO implements BaseDTO<User>{
  
  private Integer userId;
  private String userName;
  private String nickName;
  private String email;
  private Integer type;
  private Timestamp lastLogin;
  
  public UserAdminSummaryDTO() {
  }
  
  public UserAdminSummaryDTO(Integer userId, String userName, String nickName, 
      String email, Integer type, Timestamp lastLogin) {
    this.userId = userId;
    this.userName = userName;
    this.nickName = nickName;
    this.email = email;
    this.type = type;
    this.lastLogin = lastLogin;
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
    private Timestamp lastLogin;
    
    @Override
    public UserAdminSummaryDTO build() {
      return new UserAdminSummaryDTO(userId, userName, nickName, email,type, lastLogin);
    }

    @Override
    public UserAdminSummaryDTO build(Map<String, Object> properties) {
      userId = (Integer) properties.get("userId");
      userName = (String) properties.get("userName");
      nickName = (String) properties.get("nickName");
      email = (String) properties.get("email");
      type = (Integer) properties.get("type");
      lastLogin = (Timestamp) properties.get("lastLogin");
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
    
  }
}
