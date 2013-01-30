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

package cn.edu.uestc.acmicpc.oj.action.admin;

import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.oj.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.oj.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.oj.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.oj.db.entity.Problem;
import cn.edu.uestc.acmicpc.oj.db.view.impl.ProblemView;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.ArrayList;
import java.util.List;

/**
 * action for list, search, edit, add problem.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 2
 */
@SuppressWarnings("UnusedDeclaration")
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class ProblemAdminAction extends BaseAction {

    /**
     * ProblemDAO for problem search.
     */
    private IProblemDAO problemDAO = null;

    public ProblemCondition problemCondition = null;

    /**
     * Setter of ProblemDAO for Ioc.
     *
     * @param problemDAO newly problemDAO
     */
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }

    /**
     * return the problem.jsp for base view
     *
     * @return SUCCESS
     */
    public String toProblemList() {
        return SUCCESS;
    }

    /**
     * @return
     */
    public String toSearch() {
        try {
            Condition condition = problemCondition.getCondition();
            Long count = userDAO.count(problemCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.currentPage = pageInfo.getCurrentPage();
            condition.countPerPage = RECORD_PER_PAGE;
            List<Problem> problemList = problemDAO.findAll(condition);
            List<ProblemView> userViewList = new ArrayList<ProblemView>();
            for (Problem problem : problemList)
                userViewList.add(new ProblemView(problem));
            json.put("pageInfo", pageInfo.getHtmlString());
            json.put("result", "ok");
            json.put("condition", problemCondition);
            json.put("userList", userViewList);
        } catch (AppException e) {
            json.put("result", "error");
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }

    /**
     * 看下新建题目和修改题目能不能写在一起？
     *
     * @return
     */
    public String toEdit() {
        return JSON;
    }

    /**
     * @return
     */
    public String toOperatorProblem() {
        return JSON;
    }

}
