package cn.edu.uestc.acmicpc.db.dto.impl.team;

/**
 * DTO from team create form
 */
public class TeamEditDTO {
  private String teamName;
  private String memberList;

  public TeamEditDTO() {
  }

  public TeamEditDTO(String teamName, String memberList) {

    this.teamName = teamName;
    this.memberList = memberList;
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
