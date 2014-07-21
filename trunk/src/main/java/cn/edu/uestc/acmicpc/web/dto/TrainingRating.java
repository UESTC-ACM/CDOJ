package cn.edu.uestc.acmicpc.web.dto;

/**
 * Training rating record
 */
public class TrainingRating {
  public Double rating;
  public Double volatility;
  public Integer rank;
  public Double ratingVary;
  public Double volatilityVary;
  public Integer trainingContestId;

  public TrainingRating() {
  }

  public TrainingRating(Double rating, Double volatility, Integer rank, Double ratingVary, Double volatilityVary, Integer trainingContestId) {
    this.rating = rating;
    this.volatility = volatility;
    this.rank = rank;
    this.ratingVary = ratingVary;
    this.volatilityVary = volatilityVary;
    this.trainingContestId = trainingContestId;
  }
}
