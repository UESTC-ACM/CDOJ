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
import javax.persistence.Version;

/**
 * Entity for training status.
 */
@Table(name = "trainingStatus")
@Entity
@KeyField("trainingStatusId")
public class TrainingStatus implements Serializable {

  private static final long serialVersionUID = 61887986969087053L;
  private Integer trainingStatusId;

  @Column(name = "trainingStatusId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingStatusId() {
    return trainingStatusId;
  }

  public void setTrainingStatusId(Integer trainingStatusId) {
    this.trainingStatusId = trainingStatusId;
  }

  private Double rating;

  @Column(name = "rating", nullable = false, insertable = true, updatable = true, precision = 0)
  @Basic
  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  private Double volatility;

  @Column(name = "volatility", nullable = false, insertable = true, updatable = true, precision = 0)
  @Basic
  public Double getVolatility() {
    return volatility;
  }

  public void setVolatility(Double volatility) {
    this.volatility = volatility;
  }

  private Integer rank;

  @Column(name = "rank", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  private Integer penalty;

  @Column(name = "penalty", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getPenalty() {
    return penalty;
  }

  public void setPenalty(Integer penalty) {
    this.penalty = penalty;
  }

  private Integer solve;

  @Column(name = "solve", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getSolve() {
    return solve;
  }

  public void setSolve(Integer solve) {
    this.solve = solve;
  }

  private Double ratingVary;

  @Column(name = "ratingVary", nullable = false, insertable = true, updatable = true, precision = 0)
  @Basic
  public Double getRatingVary() {
    return ratingVary;
  }

  public void setRatingVary(Double ratingVary) {
    this.ratingVary = ratingVary;
  }

  private Double volatilityVary;

  @Column(name = "volatilityVary", nullable = false, insertable = true, updatable = true,
      precision = 0)
  @Basic
  public Double getVolatilityVary() {
    return volatilityVary;
  }

  public void setVolatilityVary(Double volatilityVary) {
    this.volatilityVary = volatilityVary;
  }

  private String summary;

  @Column(name = "summary", nullable = false, insertable = true, updatable = true, length = 1024,
      precision = 0)
  @Basic
  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
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

  private Integer trainingUserId;

  @Column(name = "trainingUserId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0)
  public Integer getTrainingUserId() {
    return trainingUserId;
  }

  public void setTrainingUserId(Integer trainingUserId) {
    this.trainingUserId = trainingUserId;
  }

  private Integer trainingContestId;

  @Column(name = "trainingContestId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0)
  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  private TrainingUser trainingUserByTrainingUserId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "trainingUserId", referencedColumnName = "trainingUserId", nullable = false,
      insertable = false, updatable = false)
  public TrainingUser getTrainingUserByTrainingUserId() {
    return trainingUserByTrainingUserId;
  }

  public void setTrainingUserByTrainingUserId(TrainingUser trainingUserByTrainingUserId) {
    this.trainingUserByTrainingUserId = trainingUserByTrainingUserId;
  }

  private TrainingContest trainingContestByTrainingContestId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "trainingContestId", referencedColumnName = "trainingContestId",
      nullable = false, insertable = false, updatable = false)
  public TrainingContest getTrainingContestByTrainingContestId() {
    return trainingContestByTrainingContestId;
  }

  public void setTrainingContestByTrainingContestId(
      TrainingContest trainingContestByTrainingContestId) {
    this.trainingContestByTrainingContestId = trainingContestByTrainingContestId;
  }
}
