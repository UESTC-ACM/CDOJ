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

package cn.edu.uestc.acmicpc.db.dto;

import cn.edu.uestc.acmicpc.db.entity.Problem;

/**
 * Problem entity data transform object.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
@SuppressWarnings("UnusedDeclaration")
public class ProblemDTO {
    /**
     * Input: problem id
     */
    private Integer problemId;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    /**
     * Build new problem entity.
     *
     * @return
     */
    public Problem getProblem() {
        // TODO to build problem entity.
        Problem problem = new Problem();
        problem.setProblemId(getProblemId());
        return problem;
    }

    /**
     * Update problem entity.
     *
     * @param problem
     */
    public void updateProblem(Problem problem) {
        //TODO update entity
    }
}
