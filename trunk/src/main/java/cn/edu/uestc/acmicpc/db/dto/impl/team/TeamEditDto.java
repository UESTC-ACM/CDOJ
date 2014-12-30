package cn.edu.uestc.acmicpc.db.dto.impl.team;

import java.util.Objects;

/**
 * Dto from team create form
 */
public class TeamEditDto {
  private Integer teamId;

  public TeamEditDto(Integer teamId, String teamName, String memberList) {
    this.teamId = teamId;
    this.teamName = teamName;
    this.memberList = memberList;
  }

  public Integer getTeamId() {

    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  private String teamName;
  private String memberList;

  public TeamEditDto() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TeamEditDto)) {
      return false;
    }

    TeamEditDto that = (TeamEditDto) o;
    return Objects.equals(this.memberList, that.memberList)
        && Objects.equals(this.teamId, that.teamId)
        && Objects.equals(this.teamName, that.teamName);
  }

  @Override
  public int hashCode() {
    int result = teamId != null ? teamId.hashCode() : 0;
    result = 31 * result + (teamName != null ? teamName.hashCode() : 0);
    result = 31 * result + (memberList != null ? memberList.hashCode() : 0);
    return result;
  }

  public String getTeamName() {

    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public String getMemberList() {
    return memberList;
  }

  public void setMemberList(String memberList) {
    this.memberList = memberList;
  }
}
