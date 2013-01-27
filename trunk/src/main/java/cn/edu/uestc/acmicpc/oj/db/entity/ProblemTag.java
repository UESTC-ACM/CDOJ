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

package cn.edu.uestc.acmicpc.oj.db.entity;

import cn.edu.uestc.acmicpc.oj.annotation.IdSetter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Mappings between problems and tags.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@Table(name = "problemTag", schema = "", catalog = "uestcoj")
@Entity
public class ProblemTag implements Serializable {
    private static final long serialVersionUID = 8758938774072713107L;
    private int problemTagId;

    @Column(name = "problemTagId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public int getProblemTagId() {
        return problemTagId;
    }

    @IdSetter
    public void setProblemTagId(int problemTagId) {
        this.problemTagId = problemTagId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProblemTag that = (ProblemTag) o;

        if (problemTagId != that.problemTagId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return problemTagId;
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

    private Tag tagByTagId;

    @ManyToOne
    @JoinColumn(name = "tagId", referencedColumnName = "tagId", nullable = false)
    public Tag getTagByTagId() {
        return tagByTagId;
    }

    public void setTagByTagId(Tag tagByTagId) {
        this.tagByTagId = tagByTagId;
    }
}
