package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingStatusTeamView extends View<TrainingStatus> {

    private Integer contestId;
    private String contestName;
    private Integer rating;
    private String ratingColor;
    private Integer volatility;
    private Integer ratingVary;
    private Integer volatilityVary;

    public String getRatingColor() {
        return ratingColor;
    }

    @Ignore
    public void setRatingColor(String ratingColor) {
        this.ratingColor = ratingColor;
    }

    public Integer getRatingVary() {
        return ratingVary;
    }

    @Ignore
    public void setRatingVary(Integer ratingVary) {
        this.ratingVary = ratingVary;
    }

    public Integer getVolatilityVary() {
        return volatilityVary;
    }

    @Ignore
    public void setVolatilityVary(Integer volatilityVary) {
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

    public Integer getRating() {
        return rating;
    }

    @Ignore
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getVolatility() {
        return volatility;
    }

    @Ignore
    public void setVolatility(Integer volatility) {
        this.volatility = volatility;
    }

    public TrainingStatusTeamView(TrainingStatus trainingStatus) {
        super(trainingStatus);
        setRating(trainingStatus.getRating().intValue());
        setVolatility(trainingStatus.getVolatility().intValue());
        if (rating < 900)   ratingColor = "gray";
        else if (rating < 1200) ratingColor = "green";
        else if (rating < 1500) ratingColor = "blue";
        else if (rating < 2200) ratingColor = "yellow";
        else    ratingColor = "red";
    }
}
