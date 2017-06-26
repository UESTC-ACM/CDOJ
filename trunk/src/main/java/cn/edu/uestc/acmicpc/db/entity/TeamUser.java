package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Mappings between teams and users.
 */
@Table(name = "teamUser")
@Entity
@KeyField("teamUserId")
public class TeamUser implements Serializable {

  private static final long serialVersionUID = -385147680841365770L;

  private Integer teamUserId;

  @Column(name = "teamUserId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTeamUserId() {
    return teamUserId;
  }

  public void setTeamUserId(Integer teamUserId) {
    this.teamUserId = teamUserId;
  }

  private Boolean allow;

  @Column(name = "allow", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getAllow() {
    return allow;
  }

  public void setAllow(Boolean allow) {
    this.allow = allow;
  }

  private Integer userId;

  @Column(name = "userId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
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

  private User userByUserId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false,
      insertable = false, updatable = false)
  public User getUserByUserId() {
    return userByUserId;
  }

  public void setUserByUserId(User userByUserId) {
    this.userByUserId = userByUserId;
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
