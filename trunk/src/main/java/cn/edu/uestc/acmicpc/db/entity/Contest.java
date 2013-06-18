/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Contest information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
@Table(name = "contest", schema = "", catalog = "uestcoj")
@Entity
@KeyField("contestId")
public class Contest implements Serializable {
    private static final long serialVersionUID = -3631561809657861853L;
    private Integer contestId;

    private Integer version;

    @Version
    @Column(name = "OPTLOCK")
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Column(name = "contestId", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public Integer getContestId() {
        return contestId;
    }

    @Override
    public String toString() {
        return "Contest{" +
                "isVisible=" + isVisible +
                ", length=" + length +
                ", time=" + time +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", contestId=" + contestId +
                '}';
    }

    public void setContestId(Integer contestId) {
        this.contestId = contestId;
    }

    private String title;

    @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 50,
            precision = 0)
    @Basic
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String description;

    @Column(name = "description", nullable = false, insertable = true, updatable = true,
            length = 200, precision = 0)
    @Basic
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private Byte type;

    @Column(name = "type", nullable = false, insertable = true, updatable = true,
            length = 3, precision = 0)
    @Basic
    public Byte getType() {
        return type;
    }

    public void setType(Byte type) {
        this.type = type;
    }

    private Timestamp time;

    @Column(name = "time", nullable = false, insertable = true, updatable = true,
            length = 19, precision = 0)
    @Basic
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    private Integer length;

    @Column(name = "length", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    private Boolean isVisible;

    @Column(name = "isVisible", nullable = false, insertable = true, updatable = true,
            length = 0, precision = 0)
    @Basic
    public Boolean getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(Boolean visible) {
        isVisible = visible;
    }

    @SuppressWarnings("RedundantIfStatement")
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Contest contest = (Contest) o;

        if (!contestId.equals(contest.contestId)) return false;
        if (isVisible != contest.isVisible) return false;
        if (!length.equals(contest.length)) return false;
        if (!type.equals(contest.type)) return false;
        if (description != null ? !description.equals(contest.description) : contest.description != null) return false;
        if (time != null ? !time.equals(contest.time) : contest.time != null) return false;
        if (title != null ? !title.equals(contest.title) : contest.title != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = contestId;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (int) type;
        result = 31 * result + (time != null ? time.hashCode() : 0);
        result = 31 * result + length;
        result = 31 * result + (isVisible ? 1 : 0);
        return result;
    }

    private Collection<ContestProblem> contestProblemsByContestId;

    @OneToMany(mappedBy = "contestByContestId")
    public Collection<ContestProblem> getContestProblemsByContestId() {
        return contestProblemsByContestId;
    }

    public void setContestProblemsByContestId(Collection<ContestProblem> contestProblemsByContestId) {
        this.contestProblemsByContestId = contestProblemsByContestId;
    }

    private Collection<ContestUser> contestUsersByContestId;

    @OneToMany(mappedBy = "contestByContestId")
    public Collection<ContestUser> getContestUsersByContestId() {
        return contestUsersByContestId;
    }

    public void setContestUsersByContestId(Collection<ContestUser> contestUsersByContestId) {
        this.contestUsersByContestId = contestUsersByContestId;
    }

    private Collection<Status> statusesByContestId;

    @OneToMany(mappedBy = "contestByContestId")
    public Collection<Status> getStatusesByContestId() {
        return statusesByContestId;
    }

    public void setStatusesByContestId(Collection<Status> statusesByContestId) {
        this.statusesByContestId = statusesByContestId;
    }

    private Collection<Article> articlesByContestId;

    @OneToMany(mappedBy = "contestByContestId")
    public Collection<Article> getArticlesByContestId() {
        return articlesByContestId;
    }

    public void setArticlesByContestId(Collection<Article> articlesByContestId) {
        this.articlesByContestId = articlesByContestId;
    }
}
