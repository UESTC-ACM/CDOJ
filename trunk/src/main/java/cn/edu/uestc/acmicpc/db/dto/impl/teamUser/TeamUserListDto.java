package cn.edu.uestc.acmicpc.db.dto.impl.teamUser;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import java.util.Map;
import java.util.Objects;

@Fields({ "teamUserId", "teamId", "userId", "userByUserId.userName", "userByUserId.email",
    "userByUserId.nickName"
    , "allow", "userByUserId.name" })
public class TeamUserListDto implements BaseDto<TeamUser> {

  private Integer teamUserId;
  private Integer teamId;
  private Integer userId;
  private String userName;
  private String email;
  private String nickName;
  private Boolean allow;
  private String name;

  public TeamUserListDto(Integer teamUserId, Integer teamId, Integer userId, String userName,
      String email, String nickName, Boolean allow, String name) {
    this.teamUserId = teamUserId;
    this.teamId = teamId;
    this.userId = userId;
    this.userName = userName;
    this.email = email;
    this.nickName = nickName;
    this.allow = allow;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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
    if (!(o instanceof TeamUserListDto)) {
      return false;
    }

    TeamUserListDto that = (TeamUserListDto) o;
    return Objects.equals(this.allow, that.allow)
        && Objects.equals(this.email, that.email)
        && Objects.equals(this.nickName, that.nickName)
        && Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamUserId, that.teamUserId)
        && Objects.equals(this.userId, that.userId)
        && Objects.equals(this.userName, that.userName)
        && Objects.equals(this.name, that.name);
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
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<TeamUserListDto> {

    private Builder() {
    }

    @Override
    public TeamUserListDto build() {
      return new TeamUserListDto(teamUserId, teamId, userId, userName, email,
          nickName, allow, name);
    }

    @Override
    public TeamUserListDto build(Map<String, Object> properties) {
      teamUserId = (Integer) properties.get("teamUserId");
      teamId = (Integer) properties.get("teamId");
      userId = (Integer) properties.get("userId");
      userName = (String) properties.get("userByUserId.userName");
      email = (String) properties.get("userByUserId.email");
      nickName = (String) properties.get("userByUserId.nickName");
      allow = (Boolean) properties.get("allow");
      name = (String) properties.get("userByUserId.name");
      return build();
    }

    private Integer teamUserId;
    private Integer teamId;
    private Integer userId;
    private String userName;
    private String email;
    private String nickName;
    private Boolean allow;
    private String name;

    public Builder setName(String name) {
      this.name = name;
      return this;
    }

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
