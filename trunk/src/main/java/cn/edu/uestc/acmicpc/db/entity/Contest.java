package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import cn.edu.uestc.acmicpc.util.enums.ContestType;
import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.Version;

/**
 * Contest information.
 */
@Table(name = "contest")
@Entity
@KeyField("contestId")
public class Contest implements Serializable {

  private static final long serialVersionUID = -3631561809657861853L;

  /**
   * Default contest length: 1800 seconds / 300 minutes / 5 hours
   */
  private static final Integer defaultContestLength = 1800;

  private Integer contestId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "contestId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getContestId() {
    return contestId;
  }

  @Override
  public String toString() {
    return "Contest{" + "isVisible=" + isVisible + ", length=" + length + ", time=" + time
        + ", type=" + type + ", description='" + description + '\'' + ", title='" + title + '\''
        + ", contestId=" + contestId + '}';
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  private String title;

  @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String description = "";

  @Column(name = "description", nullable = false, insertable = true, updatable = true,
      length = 65535, precision = 0)
  @Basic
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private Byte type = 0;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 3,
      precision = 0)
  @Basic
  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  private Timestamp time;

  @Column(name = "time", nullable = false, insertable = true, updatable = true, length = 19,
      precision = 0)
  @Basic
  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  private Integer length;

  @Column(name = "length", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  private Integer frozenTime;

  @Column(name = "frozenTime", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getFrozenTime() {
    return frozenTime;
  }

  public void setFrozenTime(Integer frozenTime) {
    this.frozenTime = frozenTime;
  }

  private Boolean isVisible;

  @Column(name = "isVisible", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    isVisible = visible;
  }

  private String password;

  @Column(name = "password", nullable = true, insertable = true, updatable = true, length = 40,
      precision = 0)
  @Basic
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  private Integer parentId;

  @Column(name = "parentId", nullable = true, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  private Collection<ContestProblem> contestProblemsByContestId;

  @OneToMany(mappedBy = "contestByContestId", cascade = CascadeType.ALL)
  public Collection<ContestProblem> getContestProblemsByContestId() {
    return contestProblemsByContestId;
  }

  public void setContestProblemsByContestId(Collection<ContestProblem> contestProblemsByContestId) {
    this.contestProblemsByContestId = contestProblemsByContestId;
  }

  private Collection<ContestUser> contestUsersByContestId;

  @OneToMany(mappedBy = "contestByContestId", cascade = CascadeType.ALL)
  public Collection<ContestUser> getContestUsersByContestId() {
    return contestUsersByContestId;
  }

  public void setContestUsersByContestId(Collection<ContestUser> contestUsersByContestId) {
    this.contestUsersByContestId = contestUsersByContestId;
  }

  private Collection<Status> statusesByContestId;

  @OneToMany(mappedBy = "contestByContestId", cascade = CascadeType.ALL)
  public Collection<Status> getStatusesByContestId() {
    return statusesByContestId;
  }

  public void setStatusesByContestId(Collection<Status> statusesByContestId) {
    this.statusesByContestId = statusesByContestId;
  }

  private Collection<Article> articlesByContestId;

  @OneToMany(mappedBy = "contestByContestId", cascade = CascadeType.ALL)
  public Collection<Article> getArticlesByContestId() {
    return articlesByContestId;
  }

  public void setArticlesByContestId(Collection<Article> articlesByContestId) {
    this.articlesByContestId = articlesByContestId;
  }

  private Collection<ContestTeam> contestTeamsByContestId;

  @OneToMany(mappedBy = "contestByContestId", cascade = CascadeType.ALL)
  public Collection<ContestTeam> getContestTeamsByContestId() {
    return contestTeamsByContestId;
  }

  public void setContestTeamsByContestId(Collection<ContestTeam> contestTeamsByContestId) {
    this.contestTeamsByContestId = contestTeamsByContestId;
  }

  private Contest contestByParentId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parentId", referencedColumnName = "contestId", nullable = true,
      insertable = false, updatable = false)
  public Contest getContestByParentId() {
    return contestByParentId;
  }

  public void setContestByParentId(Contest contestByParentId) {
    this.contestByParentId = contestByParentId;
  }

  public Contest() {
    contestId = null;
    description = "";
    isVisible = false;
    length = defaultContestLength;
    time = new Timestamp(System.currentTimeMillis());
    title = "";
    frozenTime = 0;
    type = (byte) ContestType.PUBLIC.ordinal();
  }
}
