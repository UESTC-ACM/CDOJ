package cn.edu.uestc.acmicpc.db.dto.impl.contestteam;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDto;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Fields({ "contestTeamId", "teamId", "teamByTeamId.teamName",
    "teamByTeamId.leaderId", "status" })
public class ContestTeamReportDto implements BaseDto<ContestTeam> {

  public ContestTeamReportDto() {
  }

  private ContestTeamReportDto(Integer contestTeamId, Integer teamId,
      String teamName, Integer status,
      Integer leaderId) {
    this.contestTeamId = contestTeamId;
    this.teamId = teamId;
    this.teamName = teamName;
    this.status = status;
    this.leaderId = leaderId;
  }

  private Integer contestTeamId;
  private Integer teamId;
  private String teamName;
  private Integer status;
  private Integer leaderId;
  private List<TeamUserReportDto> teamUsers;
  private List<TeamUserReportDto> invitedUsers;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof ContestTeamReportDto)) {
      return false;
    }

    ContestTeamReportDto that = (ContestTeamReportDto) o;
    return Objects.equals(this.contestTeamId, that.contestTeamId)
        && Objects.equals(this.invitedUsers, that.invitedUsers)
        && Objects.equals(this.leaderId, that.leaderId)
        && Objects.equals(this.status, that.status)
        && Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamName, that.teamName)
        && Objects.equals(this.teamUsers, that.teamUsers);
  }

  @Override
  public int hashCode() {
    int result = contestTeamId != null ? contestTeamId.hashCode() : 0;
    result = 31 * result + (teamId != null ? teamId.hashCode() : 0);
    result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
    result = 31 * result + (status != null ? status.hashCode() : 0);
    result = 31 * result + (leaderId != null ? leaderId.hashCode() : 0);
    result = 31 * result + (teamUsers != null ? teamUsers.hashCode() : 0);
    result = 31 * result + (invitedUsers != null ? invitedUsers.hashCode() : 0);
    return result;
  }

  public List<TeamUserReportDto> getTeamUsers() {
    return teamUsers;
  }

  public void setTeamUsers(List<TeamUserReportDto> teamUsers) {
    this.teamUsers = teamUsers;
  }

  public List<TeamUserReportDto> getInvitedUsers() {
    return invitedUsers;
  }

  public void setInvitedUsers(List<TeamUserReportDto> invitedUsers) {
    this.invitedUsers = invitedUsers;
  }

  public Integer getLeaderId() {
    return leaderId;
  }

  public void setLeaderId(Integer leaderId) {
    this.leaderId = leaderId;
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<ContestTeamReportDto> {

    private Builder() {
    }

    @Override
    public ContestTeamReportDto build() {
      return new ContestTeamReportDto(contestTeamId, teamId, teamName, status,
          leaderId);
    }

    public Builder setLeaderId(Integer leaderId) {
      this.leaderId = leaderId;
      return this;
    }

    @Override
    public ContestTeamReportDto build(Map<String, Object> properties) {
      contestTeamId = (Integer) properties.get("contestTeamId");
      teamId = (Integer) properties.get("teamId");
      teamName = (String) properties.get("teamByTeamId.teamName");
      status = (Integer) properties.get("status");
      leaderId = (Integer) properties.get("teamByTeamId.leaderId");
      return build();

    }

    private Integer contestTeamId;
    private Integer teamId;
    private String teamName;
    private Integer status;
    private Integer leaderId;

    public Integer getContestTeamId() {
      return contestTeamId;
    }

    public Builder setContestTeamId(Integer contestTeamId) {
      this.contestTeamId = contestTeamId;
      return this;
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

    public Integer getStatus() {
      return status;
    }

    public Builder setStatus(Integer status) {
      this.status = status;
      return this;
    }
  }
}
