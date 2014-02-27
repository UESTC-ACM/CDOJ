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
 * Description
 */
@Table(name = "trainingUser")
@Entity
@KeyField("trainingUserId")
public class TrainingUser implements Serializable {

  private static final long serialVersionUID = 3763485382044474929L;
  private Integer trainingUserId;

  @Column(name = "trainingUserId", nullable = false, insertable = true, updatable = true,
      length = 10, precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getTrainingUserId() {
    return trainingUserId;
  }

  public void setTrainingUserId(Integer trainingUserId) {
    this.trainingUserId = trainingUserId;
  }

  private String name;

  @Column(name = "name", nullable = false, insertable = true, updatable = true, length = 24,
      precision = 0, unique = true)
  @Basic
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  private Integer type;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
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

  private Double ratingVary;

  @Column(name = "ratingVary", nullable = true, insertable = true, updatable = true, precision = 0)
  @Basic
  public Double getRatingVary() {
    return ratingVary;
  }

  public void setRatingVary(Double ratingVary) {
    this.ratingVary = ratingVary;
  }

  private Double volatilityVary;

  @Column(name = "volatilityVary", nullable = true, insertable = true, updatable = true,
      precision = 0)
  @Basic
  public Double getVolatilityVary() {
    return volatilityVary;
  }

  public void setVolatilityVary(Double volatilityVary) {
    this.volatilityVary = volatilityVary;
  }

  private Integer competitions;

  @Column(name = "competitions", nullable = false, insertable = true, updatable = true,
      precision = 0)
  public Integer getCompetitions() {
    return competitions;
  }

  public void setCompetitions(Integer competitions) {
    this.competitions = competitions;
  }

  private String member;

  @Column(name = "member", nullable = false, insertable = true, updatable = true, length = 128,
      precision = 0)
  @Basic
  public String getMember() {
    return member;
  }

  public void setMember(String member) {
    this.member = member;
  }

  private Integer version;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
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

  private Collection<TrainingStatus> trainingStatusesByTrainingUserId;

  @OneToMany(mappedBy = "trainingUserByTrainingUserId", cascade = CascadeType.ALL)
  public Collection<TrainingStatus> getTrainingStatusesByTrainingUserId() {
    return trainingStatusesByTrainingUserId;
  }

  public void setTrainingStatusesByTrainingUserId(
      Collection<TrainingStatus> trainingStatusesByTrainingUserId) {
    this.trainingStatusesByTrainingUserId = trainingStatusesByTrainingUserId;
  }
}
