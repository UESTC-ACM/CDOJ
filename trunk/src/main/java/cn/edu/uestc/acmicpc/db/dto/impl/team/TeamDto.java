package cn.edu.uestc.acmicpc.db.dto.impl.team;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

@Fields({ "teamId", "teamName", "leaderId" })
public class TeamDto implements BaseDto<Team> {

  public TeamDto() {
  }

  private TeamDto(Integer teamId, String teamName, Integer leaderId) {
    this.teamId = teamId;
    this.teamName = teamName;
    this.leaderId = leaderId;
  }

  private Integer teamId;
  private String teamName;
  private Integer leaderId;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeamDto)) {
      return false;
    }

    TeamDto that = (TeamDto) o;
    return Objects.equals(this.leaderId, that.leaderId)
        && Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamName, that.teamName);
  }

  @Override
  public int hashCode() {
    int result = teamId != null ? teamId.hashCode() : 0;
    result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
    result = 31 * result + (leaderId != null ? leaderId.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<TeamDto> {

    private Builder() {
    }

    @Override
    public TeamDto build() {
      return new TeamDto(teamId, teamName, leaderId);
    }

    @Override
    public TeamDto build(Map<String, Object> properties) {
      teamId = (Integer) properties.get("teamId");
      teamName = (String) properties.get("teamName");
      leaderId = (Integer) properties.get("leaderId");
      return build();

    }

    private Integer teamId;
    private String teamName;
    private Integer leaderId;

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
