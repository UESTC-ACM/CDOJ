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

package cn.edu.uestc.acmicpc.oj.entity;

import cn.edu.uestc.acmicpc.db.view.impl.ContestProblemSummaryView;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ProblemSummaryInfo {
    private Integer tried;
    private char index;
    private Boolean isPending;
    private Boolean isSolved;
    private Integer penalty;
    private Integer solutionTime;
    private Integer solutionRunId;
    private Integer problemId;

    public ProblemSummaryInfo(ContestProblemSummaryView contestProblemSummaryView) {
        this.tried = 0;
        this.isPending = this.isSolved = false;
        this.penalty = 0;
        this.solutionTime = this.solutionRunId = -1;
        this.problemId = contestProblemSummaryView.getProblemId();
        this.index = contestProblemSummaryView.getOrder();
    }

    public Integer getTried() {
        return tried;
    }

    public void setTried(Integer tried) {
        this.tried = tried;
    }

    public char getIndex() {
        return index;
    }

    public void setIndex(char index) {
        this.index = index;
    }

    public Boolean getPending() {
        return isPending;
    }

    public void setPending(Boolean pending) {
        isPending = pending;
    }

    public Boolean getSolved() {
        return isSolved;
    }

    public void setSolved(Boolean solved) {
        isSolved = solved;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Integer getSolutionTime() {
        return solutionTime;
    }

    public void setSolutionTime(Integer solutionTime) {
        this.solutionTime = solutionTime;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getSolutionRunId() {
        return solutionRunId;
    }

    public void setSolutionRunId(Integer solutionRunId) {
        this.solutionRunId = solutionRunId;
    }
}
