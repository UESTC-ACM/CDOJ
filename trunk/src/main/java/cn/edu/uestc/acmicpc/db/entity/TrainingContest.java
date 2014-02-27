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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Entity for training contest.
 */
@Table(name = "trainingContest")
@Entity
@KeyField("trainingContestId")
public class TrainingContest implements Serializable {

  private static final long serialVersionUID = -3563641835123515967L;
  private Integer trainingContestId;

  @Column(name = "trainingContestId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  private Boolean isPersonal;

  @Column(name = "isPersonal", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsPersonal() {
    return isPersonal;
  }

  public void setIsPersonal(Boolean isPersonal) {
    this.isPersonal = isPersonal;
  }

  private String title = "";

  @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 150,
      precision = 0)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private Integer type;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 11,
      precision = 0)
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  private Collection<TrainingStatus> trainingStatusesByTrainingContestId;

  @OneToMany(mappedBy = "trainingContestByTrainingContestId", cascade = CascadeType.ALL)
  public Collection<TrainingStatus> getTrainingStatusesByTrainingContestId() {
    return trainingStatusesByTrainingContestId;
  }

  public void setTrainingStatusesByTrainingContestId(
      Collection<TrainingStatus> trainingStatusesByTrainingContestId) {
    this.trainingStatusesByTrainingContestId = trainingStatusesByTrainingContestId;
  }
}
