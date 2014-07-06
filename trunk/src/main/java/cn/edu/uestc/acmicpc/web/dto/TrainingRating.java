package cn.edu.uestc.acmicpc.web.dto;

/**
 * Training rating record
 */
public class TrainingRating {
  private Double rating;
  private Double volatility;

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
}
