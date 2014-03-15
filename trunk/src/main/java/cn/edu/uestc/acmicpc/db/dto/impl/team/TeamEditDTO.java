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
