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

package cn.edu.uestc.acmicpc.action.index;

import cn.edu.uestc.acmicpc.action.BaseAction;
import cn.edu.uestc.acmicpc.entity.test.Contest;
import cn.edu.uestc.acmicpc.entity.test.Problem;
import cn.edu.uestc.acmicpc.entity.test.User;
import com.opensymphony.xwork2.ActionSupport;

import java.util.List;

/**
 * action of /index/index.action
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 2
 */

public class IndexPageAction extends BaseAction {

    private static final long serialVersionUID = 1689142403463050894L;

    /**
     * current user
     */
    private User user;

    /**
     * problemList show on indexpage
     */
    private List<Problem> problemList;

    /**
     * contestList show on indexpage
     */
    private List<Contest> contestList;

    /**
     * Default action, retrun success to show indexpage
     *
     * @return success flag
     */
    public String toIndex() throws Exception {
        return SUCCESS;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Problem> getProblemList() {
        return problemList;
    }

    public void setProblemList(List<Problem> problemList) {
        this.problemList = problemList;
    }

    public List<Contest> getContestList() {
        return contestList;
    }

    public void setContestList(List<Contest> contestList) {
        this.contestList = contestList;
    }

}
