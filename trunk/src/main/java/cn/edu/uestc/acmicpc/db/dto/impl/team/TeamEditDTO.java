package cn.edu.uestc.acmicpc.db.dto.impl.team;

/**
 * DTO from team create form
 */
public class TeamEditDTO {
  private Integer teamId;

  public TeamEditDTO(Integer teamId, String teamName, String memberList) {
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

  public TeamEditDTO() {
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    TeamEditDTO that = (TeamEditDTO) o;

    if (memberList != null ? !memberList.equals(that.memberList) : that.memberList != null) {
      return false;
    }
    if (teamId != null ? !teamId.equals(that.teamId) : that.teamId != null) {
      return false;
    }
    if (teamName != null ? !teamName.equals(that.teamName) : that.teamName != null) {
      return false;
    }

    return true;
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
