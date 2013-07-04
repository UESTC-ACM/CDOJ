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
@Table(name = "trainingContest", schema = "", catalog = "uestcoj")
@Entity
@KeyField("trainingContestId")
public class TrainingContest {
    private Integer trainingContestId;

    @Column(name = "trainingContestId")
    @Id
    public Integer getTrainingContestId() {
        return trainingContestId;
    }

    public void setTrainingContestId(Integer trainingContestId) {
        this.trainingContestId = trainingContestId;
    }

    private Boolean isPersonal;

    @Column(name = "isPersonal")
    @Basic
    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean isPersonal) {
        this.isPersonal = isPersonal;
    }

    private String title;

    @Column(name = "title")
    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

        TrainingContest that = (TrainingContest) o;

        if (isPersonal != null ? !isPersonal.equals(that.isPersonal) : that.isPersonal != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (trainingContestId != null ? !trainingContestId.equals(that.trainingContestId) : that.trainingContestId != null)
            return false;
        if (version != null ? !version.equals(that.version) : that.version != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = trainingContestId != null ? trainingContestId.hashCode() : 0;
        result = 31 * result + (isPersonal != null ? isPersonal.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (version != null ? version.hashCode() : 0);
        return result;
    }
}
