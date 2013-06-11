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

package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.ProblemTag;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.commons.lang3.StringEscapeUtils;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * Use for return problem summary information with json type in contest.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class ContestProblemSummaryView extends View<Problem> {
    /**
     * State: not submitted
     */
    public static final int NONE = 0;
    /**
     * State: submitted but failed
     */
    public static final int FAILED = 1;
    /**
     * State: submitted and passed
     */
    public static final int PASSED = 2;

    private Integer state;

    public Integer getState() {
        return state;
    }

    @Ignore
    public void setState(Integer state) {
        this.state = state;
    }

    private Integer problemId;
    private Integer solved;
    private Integer tried;
    private char order;

    public char getOrder() {
        return order;
    }

    @Ignore
    public void setOrder(char order) {
        this.order = order;
    }

    /**
     * Get ProblemView entity by problem entity.
     *
     * @param problem specific problem entity
     * @throws AppException
     */
    public ContestProblemSummaryView(Problem problem,  User currentUser, Global.AuthorStatusType type)
            throws AppException {
        super(problem);

        if (currentUser == null) {
            setState(NONE);
        } else {
            setState(type == null ? NONE : type.ordinal());
        }
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }


    public Integer getSolved() {
        return solved;
    }

    @Ignore
    public void setSolved(Integer solved) {
        this.solved = solved;
    }

    public Integer getTried() {
        return tried;
    }

    @Ignore
    public void setTried(Integer tried) {
        this.tried = tried;
    }

}
