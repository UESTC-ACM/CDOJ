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
 * TrainingUser Information
 */
@Table(name = "trainingUser")
@Entity
@KeyField("trainingUserId")
public class TrainingUser implements Serializable {

  private static final long serialVersionUID = -1805092310910212411L;

  private Integer trainingUserId;

  @Column(name = "trainingUserId", nullable = false, length = 10, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingUserId() {
    return trainingUserId;
  }

  public void setTrainingUserId(Integer trainingUserId) {
    this.trainingUserId = trainingUserId;
  }

  private Integer trainingId;

  @Column(name = "trainingId", nullable = false, length = 10)
  public Integer getTrainingId() {
    return trainingId;
  }

  public void setTrainingId(Integer trainingId) {
    this.trainingId = trainingId;
  }

  private Integer userId;

  @Column(name = "userId", nullable = false, length = 10)
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private String trainingUserName;

  @Column(name = "trainingUserName", nullable = false, length = 45)
  @Basic
  public String getTrainingUserName() {
    return trainingUserName;
  }

  public void setTrainingUserName(String trainingUserName) {
    this.trainingUserName = trainingUserName;
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

  private Double currentRating;

  @Column(name = "currentRating", nullable = false)
  @Basic
  public Double getCurrentRating() {
    return currentRating;
  }

  public void setCurrentRating(Double currentRating) {
    this.currentRating = currentRating;
  }

  private Double currentVolatility;

  @Column(name = "currentVolatility", nullable = false)
  @Basic
  public Double getCurrentVolatility() {
    return currentVolatility;
  }

  public void setCurrentVolatility(Double currentVolatility) {
    this.currentVolatility = currentVolatility;
  }

  private Integer competitions;

  @Column(name = "competitions", nullable = false, length = 10)
  @Basic
  public Integer getCompetitions() {
    return competitions;
  }

  public void setCompetitions(Integer competitions) {
    this.competitions = competitions;
  }

  private Integer rank;

  @Column(name = "rank", nullable = false, length = 10)
  @Basic
  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  private Double maximumRating;

  @Column(name = "maximumRating", nullable = false)
  @Basic
  public Double getMaximumRating() {
    return maximumRating;
  }

  public void setMaximumRating(Double maximumRating) {
    this.maximumRating = maximumRating;
  }

  private Double minimumRating;

  @Column(name = "minimumRating", nullable = false)
  @Basic
  public Double getMinimumRating() {
    return minimumRating;
  }

  public void setMinimumRating(Double minimumRating) {
    this.minimumRating = minimumRating;
  }

  private Integer mostRecentEventId;

  @Column(name = "mostRecentEventId")
  @Basic
  public Integer getMostRecentEventId() {
    return mostRecentEventId;
  }

  public void setMostRecentEventId(Integer mostRecentEventId) {
    this.mostRecentEventId = mostRecentEventId;
  }

  private String mostRecentEventName;

  @Column(name = "mostRecentEventName", length = 255)
  @Basic
  public String getMostRecentEventName() {
    return mostRecentEventName;
  }

  public void setMostRecentEventName(String mostRecentEventName) {
    this.mostRecentEventName = mostRecentEventName;
  }

  private String ratingHistory;

  @Column(name = "ratingHistory", nullable = false, length = 16777215)
  @Basic
  public String getRatingHistory() {
    return ratingHistory;
  }

  public void setRatingHistory(String ratingHistory) {
    this.ratingHistory = ratingHistory;
  }

  public Training trainingByTrainingId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "trainingId", referencedColumnName = "trainingId", nullable = false, insertable = false, updatable = false)
  public Training getTrainingByTrainingId() {
    return trainingByTrainingId;
  }

  public void setTrainingByTrainingId(Training trainingByTrainingId) {
    this.trainingByTrainingId = trainingByTrainingId;
  }

  private User userByUserId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false, insertable = false, updatable = false)
  public User getUserByUserId() {
    return userByUserId;
  }

  public void setUserByUserId(User userByUserId) {
    this.userByUserId = userByUserId;
  }

  private Collection<TrainingPlatformInfo> trainingPlatformInfosByTrainingUserId;

  @OneToMany(mappedBy = "trainingUserByTrainingUserId", cascade = CascadeType.ALL)
  public Collection<TrainingPlatformInfo> getTrainingPlatformInfosByTrainingUserId() {
    return trainingPlatformInfosByTrainingUserId;
  }

  public void setTrainingPlatformInfosByTrainingUserId(Collection<TrainingPlatformInfo> trainingPlatformInfosByTrainingUserId) {
    this.trainingPlatformInfosByTrainingUserId = trainingPlatformInfosByTrainingUserId;
  }
}

