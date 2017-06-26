package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Team information
 */
@Table(name = "team")
@Entity
@KeyField("teamId")
public class Team implements Serializable {

  private static final long serialVersionUID = 4239722834470628597L;
  private Integer teamId;

  @Column(name = "teamId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTeamId() {
    return teamId;
  }

  public void setTeamId(Integer teamId) {
    this.teamId = teamId;
  }

  private String teamName;

  @Column(name = "teamName", nullable = false, insertable = true, updatable = true, length = 24,
      precision = 0, unique = true)
  @Basic
  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  private Integer leaderId;

  @Column(name = "leaderId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0)
  public Integer getLeaderId() {
    return leaderId;
  }

  public void setLeaderId(Integer leaderId) {
    this.leaderId = leaderId;
  }

  private User leaderByLeaderId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "leaderId", referencedColumnName = "userId", nullable = false,
      insertable = false, updatable = false)
  public User getLeaderByLeaderId() {
    return leaderByLeaderId;
  }

  public void setLeaderByLeaderId(User leaderByLeaderId) {
    this.leaderByLeaderId = leaderByLeaderId;
  }

  private Collection<TeamUser> teamUsersByTeamId;

  @OneToMany(mappedBy = "teamByTeamId", cascade = CascadeType.ALL)
  public Collection<TeamUser> getTeamUsersByTeamId() {
    return teamUsersByTeamId;
  }

  public void setTeamUsersByTeamId(Collection<TeamUser> teamUsersByTeamId) {
    this.teamUsersByTeamId = teamUsersByTeamId;
  }

  private Collection<ContestTeam> contestTeamsByTeamId;

  @OneToMany(mappedBy = "teamByTeamId", cascade = CascadeType.ALL)
  public Collection<ContestTeam> getContestTeamsByTeamId() {
    return contestTeamsByTeamId;
  }

  public void setContestTeamsByTeamId(Collection<ContestTeam> contestTeamsByTeamId) {
    this.contestTeamsByTeamId = contestTeamsByTeamId;
  }
}
