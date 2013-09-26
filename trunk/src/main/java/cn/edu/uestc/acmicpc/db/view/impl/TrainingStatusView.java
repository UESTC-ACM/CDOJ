package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.view.base.View;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingStatusView extends View<TrainingStatus> {

  private Integer contestId;
  private String contestName;
  private String name;
  private String userName;
  private String userEmail;
  private Double rating;
  private Double volatility;
  private Double ratingVary;
  private Double volatilityVary;
  private Integer rank;
  private Integer solve;
  private Integer penalty;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getUserEmail() {
    return userEmail;
  }

  public void setUserEmail(String userEmail) {
    this.userEmail = userEmail;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public Integer getSolve() {
    return solve;
  }

  public void setSolve(Integer solve) {
    this.solve = solve;
  }

  public Integer getPenalty() {
    return penalty;
  }

  public void setPenalty(Integer penalty) {
    this.penalty = penalty;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public Double getVolatility() {
    return volatility;
  }

  public void setVolatility(Double volatility) {
    this.volatility = volatility;
  }

  public Double getRatingVary() {
    return ratingVary;
  }

  public void setRatingVary(Double ratingVary) {
    this.ratingVary = ratingVary;
  }

  public Double getVolatilityVary() {
    return volatilityVary;
  }

  public void setVolatilityVary(Double volatilityVary) {
    this.volatilityVary = volatilityVary;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public String getContestName() {
    return contestName;
  }

  public void setContestName(String contestName) {
    this.contestName = contestName;
  }

  @Deprecated
  public TrainingStatusView(TrainingStatus trainingStatus) {
    super(trainingStatus);
    setContestId(trainingStatus.getTrainingContestByTrainingContestId().getTrainingContestId());
    setContestName(trainingStatus.getTrainingContestByTrainingContestId().getTitle());
    setName(trainingStatus.getTrainingUserByTrainingUserId().getName());
    setUserName(trainingStatus.getTrainingUserByTrainingUserId().getUserByUserId().getUserName());
    setUserEmail(trainingStatus.getTrainingUserByTrainingUserId().getUserByUserId().getEmail());
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public TrainingStatusView() {
    setContestName("Initialize");
    setContestId(0);
    setRating(1200.0);
    setVolatility(550.0);
    setRatingVary(null);
    setVolatilityVary(null);
  }
}
