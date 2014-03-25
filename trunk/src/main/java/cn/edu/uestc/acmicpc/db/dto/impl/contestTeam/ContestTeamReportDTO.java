package cn.edu.uestc.acmicpc.db.dto.impl.contestTeam;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.teamUser.TeamUserReportDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.List;
import java.util.Map;

@Fields({ "contestTeamId", "teamId", "teamByTeamId.teamName",
    "teamByTeamId.leaderId",  "status" })
public class ContestTeamReportDTO implements BaseDTO<ContestTeam> {

  public ContestTeamReportDTO() {
  }

  private ContestTeamReportDTO(Integer contestTeamId, Integer teamId,
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
  private List<TeamUserReportDTO> teamUsers;
  private List<TeamUserReportDTO> invitedUsers;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof ContestTeamReportDTO)) return false;

    ContestTeamReportDTO that = (ContestTeamReportDTO) o;

    if (contestTeamId != null ? !contestTeamId.equals(that.contestTeamId) : that.contestTeamId != null) {
      return false;
    }
    if (invitedUsers != null ? !invitedUsers.equals(that.invitedUsers) : that.invitedUsers != null) {
      return false;
    }
    if (leaderId != null ? !leaderId.equals(that.leaderId) : that.leaderId != null) {
      return false;
    }
    if (status != null ? !status.equals(that.status) : that.status != null) {
      return false;
    }
    if (teamId != null ? !teamId.equals(that.teamId) : that.teamId != null) {
      return false;
    }
    if (teamName != null ? !teamName.equals(that.teamName) : that.teamName != null) {
      return false;
    }
    if (teamUsers != null ? !teamUsers.equals(that.teamUsers) : that.teamUsers != null) {
      return false;
    }

    return true;
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

  public List<TeamUserReportDTO> getTeamUsers() {
    return teamUsers;
  }

  public void setTeamUsers(List<TeamUserReportDTO> teamUsers) {
    this.teamUsers = teamUsers;
  }

  public List<TeamUserReportDTO> getInvitedUsers() {
    return invitedUsers;
  }

  public void setInvitedUsers(List<TeamUserReportDTO> invitedUsers) {
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

  public static class Builder implements BaseBuilder<ContestTeamReportDTO> {

    private Builder() {
    }

    @Override
    public ContestTeamReportDTO build() {
      return new ContestTeamReportDTO(contestTeamId, teamId, teamName, status,
          leaderId);
    }

    public Builder setLeaderId(Integer leaderId) {
      this.leaderId = leaderId;
      return this;
    }

    @Override
    public ContestTeamReportDTO build(Map<String, Object> properties) {
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
