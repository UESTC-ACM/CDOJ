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

/**
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "trainingStatus", schema = "", catalog = "uestcoj")
@Entity
@KeyField("trainingStatusId")
public class TrainingStatus {
    private Integer trainingStatusId;

    @Column(name = "trainingStatusId")
    @Id
    public Integer getTrainingStatusId() {
        return trainingStatusId;
    }

    public void setTrainingStatusId(Integer trainingStatusId) {
        this.trainingStatusId = trainingStatusId;
    }

    private Double rating;

    @Column(name = "rating")
    @Basic
    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    private Double volatility;

    @Column(name = "volatility")
    @Basic
    public Double getVolatility() {
        return volatility;
    }

    public void setVolatility(Double volatility) {
        this.volatility = volatility;
    }

    private Integer version;

    @Column(name = "OPTLOCK")
    @Basic
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TrainingStatus that = (TrainingStatus) o;

        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (trainingStatusId != null ? !trainingStatusId.equals(that.trainingStatusId) : that.trainingStatusId != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (volatility != null ? !volatility.equals(that.volatility) : that.volatility != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trainingStatusId != null ? trainingStatusId.hashCode() : 0;
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (volatility != null ? volatility.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    private User userByUserId;

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
