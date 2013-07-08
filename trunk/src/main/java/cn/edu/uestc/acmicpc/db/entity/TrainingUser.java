/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
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
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "trainingUser", schema = "", catalog = "uestcoj")
@Entity
@KeyField("trainingUserId")
public class TrainingUser implements Serializable {
    private Integer trainingUserId;

    @Column(name = "trainingUserId", nullable = false, insertable = true, updatable = true, length = 10,
            precision = 0, unique = true)
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

    @Column(name = "ratingVary", nullable = true, insertable = true, updatable = true,
            precision = 0)
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

    private Integer version;

    @Version
    @Column(name = "OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    private User userByUserId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainingUser)) return false;

        TrainingUser that = (TrainingUser) o;

        if (allow != null ? !allow.equals(that.allow) : that.allow != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (rating != null ? !rating.equals(that.rating) : that.rating != null) return false;
        if (ratingVary != null ? !ratingVary.equals(that.ratingVary) : that.ratingVary != null) return false;
        if (trainingUserId != null ? !trainingUserId.equals(that.trainingUserId) : that.trainingUserId != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (userByUserId != null ? !userByUserId.equals(that.userByUserId) : that.userByUserId != null) return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;
        if (volatility != null ? !volatility.equals(that.volatility) : that.volatility != null) return false;
        if (volatilityVary != null ? !volatilityVary.equals(that.volatilityVary) : that.volatilityVary != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trainingUserId != null ? trainingUserId.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (rating != null ? rating.hashCode() : 0);
        result = 31 * result + (volatility != null ? volatility.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (allow != null ? allow.hashCode() : 0);
        result = 31 * result + (ratingVary != null ? ratingVary.hashCode() : 0);
        result = 31 * result + (volatilityVary != null ? volatilityVary.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        result = 31 * result + (userByUserId != null ? userByUserId.hashCode() : 0);
        return result;
    }
}
