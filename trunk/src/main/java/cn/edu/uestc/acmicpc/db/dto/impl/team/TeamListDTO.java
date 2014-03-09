package cn.edu.uestc.acmicpc.db.dto.impl.team;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.List;
import java.util.Map;

/**
 * DTO for team list
 */
@Fields({ "team.teamId", "team.teamName", "team.leaderId"})
public class TeamListDTO implements BaseDTO<Team> {

  private Integer teamId;
  private String teamName;
  private Integer leaderId;
  private List<TeamUserListDTO> teamUsers;
  private List<TeamUserListDTO> invitedUsers;
  private Boolean allow;

  public Boolean getAllow() {
    return allow;
  }

  public void setAllow(Boolean allow) {
    this.allow = allow;
  }

  public List<TeamUserListDTO> getInvitedUsers() {
    return invitedUsers;
  }

  public void setInvitedUsers(List<TeamUserListDTO> invitedUsers) {
    this.invitedUsers = invitedUsers;
  }

  public List<TeamUserListDTO> getTeamUsers() {
    return teamUsers;
  }

  public void setTeamUsers(List<TeamUserListDTO> teamUsers) {
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

  public TeamListDTO(Integer teamId, String teamName, Integer leaderId) {

    this.teamId = teamId;
    this.teamName = teamName;
    this.leaderId = leaderId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<TeamListDTO> {

    private Builder() {
    }

    private Integer teamId;
    private String teamName;
    private Integer leaderId;

    @Override
    public TeamListDTO build() {
      return new TeamListDTO(teamId, teamName, leaderId);
    }

    @Override
    public TeamListDTO build(Map<String, Object> properties) {
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
