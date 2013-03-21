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

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Discuss for problems.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Table(name = "discuss", schema = "", catalog = "uestcoj")
@Entity
public class Discuss implements Serializable {
    private static final long serialVersionUID = -9155275753251130485L;
    private Integer discussId;

    @Column(name = "discussId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public Integer getDiscussId() {
        return discussId;
    }

    public void setDiscussId(Integer discussId) {
        this.discussId = discussId;
    }

    private String content;

    @Column(name = "content", nullable = false, insertable = true, updatable = true,
            length = 65535, precision = 0)
    @Basic
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Discuss discuss = (Discuss) o;

        if (!discussId.equals(discuss.discussId)) return false;
        if (content != null ? !content.equals(discuss.content) : discuss.content != null) return false;
        if (time != null ? !time.equals(discuss.time) : discuss.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = discussId;
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (time != null ? time.hashCode() : 0);
        return result;
    }

    private Problem problemByProblemId;

    @ManyToOne
    @JoinColumn(name = "problemId", referencedColumnName = "problemId", nullable = false)
    public Problem getProblemByProblemId() {
        return problemByProblemId;
    }

    public void setProblemByProblemId(Problem problemByProblemId) {
        this.problemByProblemId = problemByProblemId;
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

    private Discuss discussByParentId;

    @ManyToOne
    @JoinColumn(name = "parentId", referencedColumnName = "discussId")
    public Discuss getDiscussByParentId() {
        return discussByParentId;
    }

    public void setDiscussByParentId(Discuss discussByParentId) {
        this.discussByParentId = discussByParentId;
    }

    private Collection<Discuss> discussesByDiscussId;

    @OneToMany(mappedBy = "discussByParentId")
    public Collection<Discuss> getDiscussesByDiscussId() {
        return discussesByDiscussId;
    }

    public void setDiscussesByDiscussId(Collection<Discuss> discussesByDiscussId) {
        this.discussesByDiscussId = discussesByDiscussId;
    }
}
