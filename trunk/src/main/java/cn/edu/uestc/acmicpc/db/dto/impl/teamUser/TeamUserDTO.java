package cn.edu.uestc.acmicpc.db.dto.impl.teamUser;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TeamUser;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

@Fields({ "teamUserId", "teamId", "userId", "allow" })
public class TeamUserDTO implements BaseDTO<TeamUser> {

  public TeamUserDTO() {
  }

  private TeamUserDTO(Integer teamUserId, Integer teamId, Integer userId, Boolean allow) {
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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TeamUserDTO that = (TeamUserDTO) o;

    if (allow != null ? !allow.equals(that.allow) : that.allow != null) return false;
    if (teamId != null ? !teamId.equals(that.teamId) : that.teamId != null) return false;
    if (teamUserId != null ? !teamUserId.equals(that.teamUserId) : that.teamUserId != null)
      return false;
    if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;

    return true;
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

  public static class Builder implements BaseBuilder<TeamUserDTO> {

    private Builder() {
    }

    @Override
    public TeamUserDTO build() {
      return new TeamUserDTO(teamUserId, teamId, userId, allow);
    }

    @Override
    public TeamUserDTO build(Map<String, Object> properties) {
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
