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

package cn.edu.uestc.acmicpc.action.problem;

import cn.edu.uestc.acmicpc.action.BaseAction;

/**
 * action of /problemset/problem/{problemId}/*
 * <br/>
 * there are 4 part for show problem, submit, show status, show discuss.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
public class ProblemAction extends BaseAction {

    private static final long serialVersionUID = 671517525416469862L;

    /**
     * problem to show
     */
    private Integer problemId;

    /**
     * Default action.
     * TODO if problemId is INVALID, return ERROR
     *
     * @return success or error
     */
    public String toShow() {
        if (problemId == null)
            problemId = 1;

        return SUCCESS;
    }

    public String toSubmit() {
        if (problemId == null)
            problemId = 1;

        return SUCCESS;
    }

    public String toStatus() {
        if (problemId == null)
            problemId = 1;

        return SUCCESS;
    }

    public String toDiscuss() {
        if (problemId == null)
            problemId = 1;

        return SUCCESS;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

}
