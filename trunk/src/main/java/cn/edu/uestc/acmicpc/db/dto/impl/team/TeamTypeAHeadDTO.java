package cn.edu.uestc.acmicpc.db.dto.impl.team;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Team;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

@Fields({ "team.teamId", "team.teamName" })
public class TeamTypeAHeadDTO implements BaseDTO<Team> {

  public TeamTypeAHeadDTO() {
  }

  private TeamTypeAHeadDTO(Integer teamId, String teamName) {
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<TeamTypeAHeadDTO> {

    private Builder() {
    }

    @Override
    public TeamTypeAHeadDTO build() {
      return new TeamTypeAHeadDTO(teamId, teamName);
    }

    @Override
    public TeamTypeAHeadDTO build(Map<String, Object> properties) {
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
