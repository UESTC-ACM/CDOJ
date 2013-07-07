/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * 	mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "trainingStatus", schema = "", catalog = "uestcoj")
@Entity
@KeyField("trainingStatusId")
public class TrainingStatus implements Serializable {
    private Integer trainingStatusId;

    @Column(name = "trainingStatusId", nullable = false, insertable = true, updatable = true, length = 10,
            precision = 0, unique = true)
    @Id
    @GeneratedValue
    public Integer getTrainingStatusId() {
        return trainingStatusId;
    }

    public void setTrainingStatusId(Integer trainingStatusId) {
        this.trainingStatusId = trainingStatusId;
    }

    private Double rating;

    @Column(name = "rating", nullable = false, insertable = true, updatable = true,
            precision = 0)
    @Basic
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    private Double volatility;

    @Column(name = "volatility", nullable = false, insertable = true, updatable = true,
            precision = 0)
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

    @Column(name = "ratingVary", nullable = false, insertable = true, updatable = true,
            precision = 0)
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

    private Integer version;

    @Version
    @Column(name = "OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingStatus)) return false;

        TrainingStatus that = (TrainingStatus) o;

        if (penalty != null ? !penalty.equals(that.penalty) : that.penalty != null) return false;
        if (rank != null ? !rank.equals(that.rank) : that.rank != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (ratingVary != null ? !ratingVary.equals(that.ratingVary) : that.ratingVary != null) return false;
        if (solve != null ? !solve.equals(that.solve) : that.solve != null) return false;
        if (trainingContestByTrainingContestId != null ? !trainingContestByTrainingContestId.equals(that.trainingContestByTrainingContestId) : that.trainingContestByTrainingContestId != null)
            return false;
        if (trainingStatusId != null ? !trainingStatusId.equals(that.trainingStatusId) : that.trainingStatusId != null)
            return false;
        if (trainingUserByTrainingUserId != null ? !trainingUserByTrainingUserId.equals(that.trainingUserByTrainingUserId) : that.trainingUserByTrainingUserId != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (volatility != null ? !volatility.equals(that.volatility) : that.volatility != null) return false;
        if (volatilityVary != null ? !volatilityVary.equals(that.volatilityVary) : that.volatilityVary != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trainingStatusId != null ? trainingStatusId.hashCode() : 0;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (volatility != null ? volatility.hashCode() : 0);
        result = 31 * result + (rank != null ? rank.hashCode() : 0);
        result = 31 * result + (penalty != null ? penalty.hashCode() : 0);
        result = 31 * result + (solve != null ? solve.hashCode() : 0);
        result = 31 * result + (ratingVary != null ? ratingVary.hashCode() : 0);
        result = 31 * result + (volatilityVary != null ? volatilityVary.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (trainingUserByTrainingUserId != null ? trainingUserByTrainingUserId.hashCode() : 0);
        result = 31 * result + (trainingContestByTrainingContestId != null ? trainingContestByTrainingContestId.hashCode() : 0);
        return result;
    }

    private TrainingUser trainingUserByTrainingUserId;

    @ManyToOne
    @JoinColumn(name = "trainingUserId", referencedColumnName = "trainingUserId", nullable = false)
    public TrainingUser getTrainingUserByTrainingUserId() {
        return trainingUserByTrainingUserId;
    }

    public void setTrainingUserByTrainingUserId(TrainingUser trainingUserByTrainingUserId) {
        this.trainingUserByTrainingUserId = trainingUserByTrainingUserId;
    }

    @ManyToOne
    @JoinColumn(name = "trainingContestId", referencedColumnName = "trainingContestId", nullable = false)
    public TrainingContest getTrainingContestByTrainingContestId() {
        return trainingContestByTrainingContestId;
    }

    public void setTrainingContestByTrainingContestId(TrainingContest trainingContestByTrainingContestId) {
        this.trainingContestByTrainingContestId = trainingContestByTrainingContestId;
    }

    private TrainingContest trainingContestByTrainingContestId;
}
