package cn.edu.uestc.acmicpc.db.dto.impl.contestTeam;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestTeam;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

@Fields({ "contestTeamId", "contestId", "teamId", "status", "comment" })
public class ContestTeamDTO implements BaseDTO<ContestTeam> {

  public ContestTeamDTO() {
  }

  private ContestTeamDTO(Integer contestTeamId, Integer contestId, Integer teamId, Integer status, String comment) {
    this.contestTeamId = contestTeamId;
    this.contestId = contestId;
    this.teamId = teamId;
    this.status = status;
    this.comment = comment;
  }

  private Integer contestTeamId;
  private Integer contestId;
  private Integer teamId;
  private Integer status;
  private String comment;

  public Integer getContestTeamId() {
    return contestTeamId;
  }

  public void setContestTeamId(Integer contestTeamId) {
    this.contestTeamId = contestTeamId;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
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

  public static class Builder implements BaseBuilder<ContestTeamDTO> {

    private Builder() {
    }

    @Override
    public ContestTeamDTO build() {
      return new ContestTeamDTO(contestTeamId, contestId, teamId, status, comment);
    }

    @Override
    public ContestTeamDTO build(Map<String, Object> properties) {
      contestTeamId = (Integer) properties.get("contestTeamId");
      contestId = (Integer) properties.get("contestId");
      teamId = (Integer) properties.get("teamId");
      status = (Integer) properties.get("status");
      comment = (String) properties.get("comment");
      return build();

    }

    private Integer contestTeamId;
    private Integer contestId;
    private Integer teamId;
    private Integer status;
    private String comment;

    public Integer getContestTeamId() {
      return contestTeamId;
    }

    public Builder setContestTeamId(Integer contestTeamId) {
      this.contestTeamId = contestTeamId;
      return this;
    }

    public Integer getContestId() {
      return contestId;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Integer getTeamId() {
      return teamId;
    }

    public Builder setTeamId(Integer teamId) {
      this.teamId = teamId;
      return this;
    }

    public Integer getStatus() {
      return status;
    }

    public Builder setStatus(Integer status) {
      this.status = status;
      return this;
    }

    public String getComment() {
      return comment;
    }

    public Builder setComment(String comment) {
      this.comment = comment;
      return this;
    }
  }
}
