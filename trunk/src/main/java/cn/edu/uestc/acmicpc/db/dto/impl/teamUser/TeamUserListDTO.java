package cn.edu.uestc.acmicpc.db.dto.impl.teamUser;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

@Fields({"teamUserId", "teamId", "userId", "userByUserId.userName", "userByUserId.email", "userByUserId.nickName"
    , "allow"})
public class TeamUserListDTO implements BaseDTO<TeamUser> {

  private Integer teamUserId;
  private Integer teamId;
  private Integer userId;
  private String userName;
  private String email;
  private String nickName;
  private Boolean allow;

  public TeamUserListDTO(Integer teamUserId, Integer teamId, Integer userId, String userName,
                         String email, String nickName, Boolean allow) {
    this.teamUserId = teamUserId;
    this.teamId = teamId;
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.nickName = nickName;
    this.allow = allow;
  }

  public Integer getTeamUserId() {
    return teamUserId;
  }

  public void setTeamUserId(Integer teamUserId) {
    this.teamUserId = teamUserId;
  }

  public Boolean getAllow() {
    return allow;
  }

  public void setAllow(Boolean allow) {
    this.allow = allow;
  }

  public String getNickName() {
    return nickName;
  }

  public void setNickName(String nickName) {
    this.nickName = nickName;
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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TeamUserListDTO that = (TeamUserListDTO) o;

    if (allow != null ? !allow.equals(that.allow) : that.allow != null) {
      return false;
    }
    if (email != null ? !email.equals(that.email) : that.email != null) {
      return false;
    }
    if (nickName != null ? !nickName.equals(that.nickName) : that.nickName != null) {
      return false;
    }
    if (teamId != null ? !teamId.equals(that.teamId) : that.teamId != null) {
      return false;
    }
    if (teamUserId != null ? !teamUserId.equals(that.teamUserId) : that.teamUserId != null) {
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
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
    result = 31 * result + (allow != null ? allow.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<TeamUserListDTO> {

    private Builder() {
    }

    @Override
    public TeamUserListDTO build() {
      return new TeamUserListDTO(teamUserId, teamId, userId, userName, email, nickName, allow);
    }

    @Override
    public TeamUserListDTO build(Map<String, Object> properties) {
      teamUserId = (Integer) properties.get("teamUserId");
      teamId = (Integer) properties.get("teamId");
      userId = (Integer) properties.get("userId");
      userName = (String) properties.get("userByUserId.userName");
      email = (String) properties.get("userByUserId.email");
      nickName = (String) properties.get("userByUserId.nickName");
      allow = (Boolean) properties.get("allow");
      return build();
    }

    private Integer teamUserId;
    private Integer teamId;
    private Integer userId;
    private String userName;
    private String email;
    private String nickName;
    private Boolean allow;

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

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Builder setEmail(String email) {
      this.email = email;
      return this;
    }

    public Builder setNickName(String nickName) {
      this.nickName = nickName;
      return this;
    }

    public Builder setAllow(Boolean allow) {
      this.allow = allow;
      return this;
    }
  }
}
