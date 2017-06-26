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

/**
 * Training information
 */
@Table(name = "training")
@Entity
@KeyField("trainingId")
public class Training implements Serializable {

  private static final long serialVersionUID = 8186666368069514966L;

  private Integer trainingId;

  @Column(name = "trainingId", nullable = false, length = 10, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingId() {
    return trainingId;
  }

  public void setTrainingId(Integer trainingId) {
    this.trainingId = trainingId;
  }

  private String title;

  @Column(name = "title", nullable = false)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String description;

  @Column(name = "description", nullable = false, length = 65535)
  @Basic
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  private Collection<TrainingContest> trainingContestsByTrainingId;

  @OneToMany(mappedBy = "trainingByTrainingId", cascade = CascadeType.ALL)
  public Collection<TrainingContest> getTrainingContestsByTrainingId() {
    return trainingContestsByTrainingId;
  }

  public void setTrainingContestsByTrainingId(Collection<TrainingContest> trainingContestsByTrainingId) {
    this.trainingContestsByTrainingId = trainingContestsByTrainingId;
  }

  private Collection<TrainingUser> trainingUsersByTrainingId;

  @OneToMany(mappedBy = "trainingByTrainingId", cascade = CascadeType.ALL)
  public Collection<TrainingUser> getTrainingUsersByTrainingId() {
    return trainingUsersByTrainingId;
  }

  public void setTrainingUsersByTrainingId(Collection<TrainingUser> trainingUsersByTrainingId) {
    this.trainingUsersByTrainingId = trainingUsersByTrainingId;
  }
}
