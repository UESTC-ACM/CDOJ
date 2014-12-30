package cn.edu.uestc.acmicpc.db.dto.impl.team;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDto;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Dto for team list
 */
@Fields({ "team.teamId", "team.teamName", "team.leaderId" })
public class TeamListDto implements BaseDto<Team> {

  private Integer teamId;
  private String teamName;
  private Integer leaderId;
  private List<TeamUserListDto> teamUsers;
  private List<TeamUserListDto> invitedUsers;
  private Boolean allow;

  public Boolean getAllow() {
    return allow;
  }

  public void setAllow(Boolean allow) {
    this.allow = allow;
  }

  public List<TeamUserListDto> getInvitedUsers() {
    return invitedUsers;
  }

  public void setInvitedUsers(List<TeamUserListDto> invitedUsers) {
    this.invitedUsers = invitedUsers;
  }

  public List<TeamUserListDto> getTeamUsers() {
    return teamUsers;
  }

  public void setTeamUsers(List<TeamUserListDto> teamUsers) {
    this.teamUsers = teamUsers;
  }

  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Integer getLeaderId() {
    return leaderId;
  }

  public void setLeaderId(Integer leaderId) {
    this.leaderId = leaderId;
  }

  public TeamListDto(Integer teamId, String teamName, Integer leaderId) {

    this.teamId = teamId;
    this.teamName = teamName;
    this.leaderId = leaderId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeamListDto)) {
      return false;
    }

    TeamListDto that = (TeamListDto) o;
    return Objects.equals(this.allow, that.allow)
        && Objects.equals(this.invitedUsers, that.invitedUsers)
        && Objects.equals(this.leaderId, that.leaderId)
        && Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamName, that.teamName)
        && Objects.equals(this.teamUsers, that.teamUsers);
  }

  @Override
  public int hashCode() {
    int result = teamId != null ? teamId.hashCode() : 0;
    result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
    result = 31 * result + (leaderId != null ? leaderId.hashCode() : 0);
    result = 31 * result + (teamUsers != null ? teamUsers.hashCode() : 0);
    result = 31 * result + (invitedUsers != null ? invitedUsers.hashCode() : 0);
    result = 31 * result + (allow != null ? allow.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<TeamListDto> {

    private Builder() {
    }

    private Integer teamId;
    private String teamName;
    private Integer leaderId;

    @Override
    public TeamListDto build() {
      return new TeamListDto(teamId, teamName, leaderId);
    }

    @Override
    public TeamListDto build(Map<String, Object> properties) {
      teamId = (Integer) properties.get("team.teamId");
      teamName = (String) properties.get("team.teamName");
      leaderId = (Integer) properties.get("team.leaderId");
      return build();
    }

    public Integer getTeamId() {
      return teamId;
    }

    public Builder setTeamId(Integer teamId) {
      this.teamId = teamId;
      return this;
    }

    public String getTeamName() {
      return teamName;
    }

    public Builder setTeamName(String teamName) {
      this.teamName = teamName;
      return this;
    }

    public Integer getLeaderId() {
      return leaderId;
    }

    public Builder setLeaderId(Integer leaderId) {
      this.leaderId = leaderId;
      return this;
    }
  }
}
