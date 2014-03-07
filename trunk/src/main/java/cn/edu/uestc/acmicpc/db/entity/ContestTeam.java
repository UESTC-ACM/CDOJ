package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mappings between contests and teams.
 */
@Table(name = "contestTeam")
@Entity
@KeyField("contestTeamId")
public class ContestTeam implements Serializable {

  private static final long serialVersionUID = -5157120879962267233L;

  private Integer contestTeamId;

  @Column(name = "contestTeamId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getContestTeamId() {
    return contestTeamId;
  }

  public void setContestTeamId(Integer contestTeamId) {
    this.contestTeamId = contestTeamId;
  }

  private Integer contestId;

  @Column(name = "contestId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  private Integer teamId;

  @Column(name = "teamId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  private Contest contestByContestId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contestId", referencedColumnName = "contestId", nullable = false,
      insertable = false, updatable = false)
  public Contest getContestByContestId() {
    return contestByContestId;
  }

  public void setContestByContestId(Contest contestByContestId) {
    this.contestByContestId = contestByContestId;
  }

  private Team teamByTeamId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "teamId", referencedColumnName = "teamId", nullable = false,
      insertable = false, updatable = false)
  public Team getTeamByTeamId() {
    return teamByTeamId;
  }

  public void setTeamByTeamId(Team teamByTeamId) {
    this.teamByTeamId = teamByTeamId;
  }
}
