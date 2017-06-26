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
 * TrainingPlatformInfo information
 */
@Table(name = "trainingPlatformInfo")
@Entity
@KeyField("trainingPlatformInfoId")
public class TrainingPlatformInfo implements Serializable {

  private static final long serialVersionUID = -3704445205398803959L;

  private Integer trainingPlatformInfoId;

  @Column(name = "trainingPlatformInfoId", nullable = false, length = 10, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingPlatformInfoId() {
    return trainingPlatformInfoId;
  }

  public void setTrainingPlatformInfoId(Integer trainingPlatformInfoId) {
    this.trainingPlatformInfoId = trainingPlatformInfoId;
  }

  private Integer trainingUserId;

  @Column(name = "trainingUserId", nullable = false, length = 10)
  public Integer getTrainingUserId() {
    return trainingUserId;
  }

  public void setTrainingUserId(Integer trainingUserId) {
    this.trainingUserId = trainingUserId;
  }

  private String userName;

  @Column(name = "userName")
  @Basic
  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  private String userId;

  @Column(name = "userId")
  @Basic
  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  private Integer type;

  @Column(name = "type", nullable = false, length = 10)
  @Basic
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private TrainingUser trainingUserByTrainingUserId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "trainingUserId", referencedColumnName = "trainingUserId", nullable = false, insertable = false, updatable = false)
  public TrainingUser getTrainingUserByTrainingUserId() {
    return trainingUserByTrainingUserId;
  }

  public void setTrainingUserByTrainingUserId(TrainingUser trainingUserByTrainingUserId) {
    this.trainingUserByTrainingUserId = trainingUserByTrainingUserId;
  }
}
