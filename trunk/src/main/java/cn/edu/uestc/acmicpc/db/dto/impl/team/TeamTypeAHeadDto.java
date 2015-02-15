package cn.edu.uestc.acmicpc.db.dto.impl.team;

import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;
import java.util.Objects;

@Fields({ "team.teamId", "team.teamName" })
public class TeamTypeAHeadDto implements BaseDto<Team> {

  public TeamTypeAHeadDto() {
  }

  private TeamTypeAHeadDto(Integer teamId, String teamName) {
    this.teamId = teamId;
    this.teamName = teamName;
  }

  private Integer teamId;
  private String teamName;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeamTypeAHeadDto)) {
      return false;
    }

    TeamTypeAHeadDto that = (TeamTypeAHeadDto) o;
    return Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamName, that.teamName);
  }

  @Override
  public int hashCode() {
    int result = teamId != null ? teamId.hashCode() : 0;
    result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseDtoBuilder<TeamTypeAHeadDto> {

    private Builder() {
    }

    @Override
    public TeamTypeAHeadDto build() {
      return new TeamTypeAHeadDto(teamId, teamName);
    }

    @Override
    public TeamTypeAHeadDto build(Map<String, Object> properties) {
      teamId = (Integer) properties.get("team.teamId");
      teamName = (String) properties.get("team.teamName");
      return build();

    }

    private Integer teamId;
    private String teamName;

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
  }
}
