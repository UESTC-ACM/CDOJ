package cn.edu.uestc.acmicpc.db.dto.impl.team;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

@Fields({ "teamId", "teamName", "leaderId" })
public class TeamDTO implements BaseDTO<Team> {

  public TeamDTO() {
  }

  private TeamDTO(Integer teamId, String teamName, Integer leaderId) {
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
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    TeamDTO teamDTO = (TeamDTO) o;

    if (leaderId != null ? !leaderId.equals(teamDTO.leaderId) : teamDTO.leaderId != null)
      return false;
    if (teamId != null ? !teamId.equals(teamDTO.teamId) : teamDTO.teamId != null) return false;
    if (teamName != null ? !teamName.equals(teamDTO.teamName) : teamDTO.teamName != null)
      return false;

    return true;
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

  public static class Builder implements BaseBuilder<TeamDTO> {

    private Builder() {
    }

    @Override
    public TeamDTO build() {
      return new TeamDTO(teamId, teamName, leaderId);
    }

    @Override
    public TeamDTO build(Map<String, Object> properties) {
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
