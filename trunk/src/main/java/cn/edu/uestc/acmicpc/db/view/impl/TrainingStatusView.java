package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingStatusView extends View<TrainingStatus> {

    private Integer contestId;
    private String contestName;
    private Double rating;
    private Double volatility;
    private Double ratingVary;
    private Double volatilityVary;

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

    @Ignore
    public void setRatingVary(Double ratingVary) {
        this.ratingVary = ratingVary;
    }

    public Double getVolatilityVary() {
        return volatilityVary;
    }

    @Ignore
    public void setVolatilityVary(Double volatilityVary) {
        this.volatilityVary = volatilityVary;
    }

    public Integer getContestId() {
        return contestId;
    }

    @Ignore
    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    public String getContestName() {
        return contestName;
    }

    @Ignore
    public void setContestName(String contestName) {
        this.contestName = contestName;
    }

    public TrainingStatusView(TrainingStatus trainingStatus) {
        super(trainingStatus);
        setContestId(trainingStatus.getTrainingContestByTrainingContestId().getTrainingContestId());
        setContestName(trainingStatus.getTrainingContestByTrainingContestId().getTitle());
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
