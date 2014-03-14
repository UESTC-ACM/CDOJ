package cn.edu.uestc.acmicpc.db.dto.impl.contestTeam;

/**
 * Description
 */
public class ContestTeamReviewDTO {
  private Integer status;
  private Integer contestTeamId;
  private String comment;

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Integer getContestTeamId() {
    return contestTeamId;
  }

  public void setContestTeamId(Integer contestTeamId) {
    this.contestTeamId = contestTeamId;
  }

  public String getComment() {
    return comment;
  }

  public void setComment(String comment) {
    this.comment = comment;
  }
}
