package cn.edu.uestc.acmicpc.db.dto.impl.contestTeam;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserListDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.settings.Global;

import java.util.List;
import java.util.Map;

/**
 * Description
 */
@Fields({"contestTeamId", "teamId", "teamByTeamId.teamName", "teamByTeamId.leaderId", "status", "comment"})
public class ContestTeamListDTO implements BaseDTO<ContestTeam> {

  private Integer contestTeamId;
  private Integer teamId;
  private String teamName;
  private Integer leaderId;
  private Integer status;
  private String statusName;
  private String comment;
  private List<TeamUserListDTO> teamUsers;
  private List<TeamUserListDTO> invitedUsers;

  public List<TeamUserListDTO> getTeamUsers() {
    return teamUsers;
  }

  public void setTeamUsers(List<TeamUserListDTO> teamUsers) {
    this.teamUsers = teamUsers;
  }

  public String getStatusName() {
    return statusName;
  }

  public List<TeamUserListDTO> getInvitedUsers() {
    return invitedUsers;
  }

  public void setInvitedUsers(List<TeamUserListDTO> invitedUsers) {
    this.invitedUsers = invitedUsers;
  }

  public void setStatusName(String statusName) {
    this.statusName = statusName;
  }

  public ContestTeamListDTO(Integer contestTeamId, Integer teamId, String teamName, Integer leaderId,
                            Integer status, String comment) {
    this.contestTeamId = contestTeamId;
    this.teamId = teamId;
    this.teamName = teamName;
    this.leaderId = leaderId;
    this.status = status;
    this.comment = comment;

    this.statusName = Global.ContestRegistryStatus.values()[status].getDescription();
  }

  public Integer getContestTeamId() {

    return contestTeamId;
  }

  public void setContestTeamId(Integer contestTeamId) {
    this.contestTeamId = contestTeamId;
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

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }


  public static Builder builder() {
    return new Builder();
  }

  public Integer getLeaderId() {
    return leaderId;
  }

  public void setLeaderId(Integer leaderId) {
    this.leaderId = leaderId;
  }

  public static class Builder implements BaseBuilder<ContestTeamListDTO> {

    private Builder() {
    }

    public Builder setLeaderId(Integer leaderId) {
      this.leaderId = leaderId;
      return this;
    }

    @Override
    public ContestTeamListDTO build() {
      return new ContestTeamListDTO(contestTeamId, teamId, teamName, leaderId, status, comment);
    }

    @Override
    public ContestTeamListDTO build(Map<String, Object> properties) {
      contestTeamId = (Integer) properties.get("contestTeamId");
      teamId = (Integer) properties.get("teamId");
      teamName = (String) properties.get("teamByTeamId.teamName");
      leaderId = (Integer) properties.get("teamByTeamId.leaderId");
      status = (Integer) properties.get("status");
      comment = (String) properties.get("comment");
      return build();
    }

    private Integer contestTeamId;
    private Integer teamId;
    private String teamName;
    private Integer leaderId;
    private Integer status;
    private String comment;

    public Builder setContestTeamId(Integer contestTeamId) {
      this.contestTeamId = contestTeamId;
      return this;
    }

    public Builder setTeamId(Integer teamId) {
      this.teamId = teamId;
      return this;
    }

    public Builder setTeamName(String teamName) {
      this.teamName = teamName;
      return this;
    }

    public Builder setStatus(Integer status) {
      this.status = status;
      return this;
    }

    public Builder setComment(String comment) {
      this.comment = comment;
      return this;
    }
  }
}
