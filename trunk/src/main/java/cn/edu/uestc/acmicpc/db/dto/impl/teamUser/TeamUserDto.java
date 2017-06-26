package cn.edu.uestc.acmicpc.db.dto.impl.teamUser;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import java.util.Map;
import java.util.Objects;

@Fields({ "teamUserId", "teamId", "userId", "allow" })
public class TeamUserDto implements BaseDto<TeamUser> {

  public TeamUserDto() {
  }

  private TeamUserDto(Integer teamUserId, Integer teamId, Integer userId, Boolean allow) {
    this.teamUserId = teamUserId;
    this.teamId = teamId;
    this.userId = userId;
    this.allow = allow;
  }

  private Integer teamUserId;
  private Integer teamId;
  private Integer userId;
  private Boolean allow;

  public Boolean getAllow() {
    return allow;
  }

  public void setAllow(Boolean allow) {
    this.allow = allow;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeamUserDto)) {
      return false;
    }

    TeamUserDto that = (TeamUserDto) o;
    return Objects.equals(this.allow, that.allow)
        && Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamUserId, that.teamUserId)
        && Objects.equals(this.userId, that.userId);
  }

  @Override
  public int hashCode() {
    int result = teamUserId != null ? teamUserId.hashCode() : 0;
    result = 31 * result + (teamId != null ? teamId.hashCode() : 0);
    result = 31 * result + (userId != null ? userId.hashCode() : 0);
    result = 31 * result + (allow != null ? allow.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<TeamUserDto> {

    private Builder() {
    }

    @Override
    public TeamUserDto build() {
      return new TeamUserDto(teamUserId, teamId, userId, allow);
    }

    @Override
    public TeamUserDto build(Map<String, Object> properties) {
      teamUserId = (Integer) properties.get("teamUserId");
      teamId = (Integer) properties.get("teamId");
      userId = (Integer) properties.get("userId");
      allow = (Boolean) properties.get("allow");
      return build();
    }

    private Integer teamUserId;
    private Integer teamId;
    private Integer userId;
    private Boolean allow;

    public Integer getTeamUserId() {
      return teamUserId;
    }

    public Builder setTeamUserId(Integer teamUserId) {
      this.teamUserId = teamUserId;
      return this;
    }

    public Integer getTeamId() {
      return teamId;
    }

    public Builder setTeamId(Integer teamId) {
      this.teamId = teamId;
      return this;
    }

    public Integer getUserId() {
      return userId;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }

    public Builder setAllow(Boolean allow) {
      this.allow = allow;
      return this;
    }
  }
}
