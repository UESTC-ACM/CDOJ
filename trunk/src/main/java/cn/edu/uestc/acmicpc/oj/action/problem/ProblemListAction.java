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

package cn.edu.uestc.acmicpc.oj.action.problem;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemListView;
import cn.edu.uestc.acmicpc.ioc.condition.ProblemConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * action for list and search problem.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class ProblemListAction extends BaseAction
        implements ProblemConditionAware, ProblemDAOAware {

    /**
     * ProblemDAO for problem search.
     */
    @Autowired
    private IProblemDAO problemDAO;

    @Autowired
    private ProblemCondition problemCondition;

    @Override
    public void setProblemCondition(ProblemCondition problemCondition) {
        this.problemCondition = problemCondition;
    }

    public ProblemCondition getProblemCondition() {
        return problemCondition;
    }

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
     * @return <strong>SUCCESS</strong> signal
     */
    @SuppressWarnings("SameReturnValue")
    @SkipValidation
    public String toProblemList() {
        return SUCCESS;
    }

    /**
     * Search action.
     * <p/>
     * Find all records by conditions and return them as a list in JSON, and the condition
     * set will set in JSON named "condition".
     * <p/>
     * <strong>JSON output</strong>:
     * <ul>
     * <li>
     * For success: {"result":"ok", "pageInfo":<strong>PageInfo object</strong>,
     * "condition", <strong>ProblemCondition entity</strong>,
     * "problemList":<strong>query result</strong>}
     * </li>
     * <li>
     * For error: {"result":"error", "error_msg":<strong>error message</strong>}
     * </li>
     * </ul>
     *
     * @return <strong>JSON</strong> signal
     */
    @SuppressWarnings("unchecked")
    @SkipValidation
    public String toSearch() {
        try {
            if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
                problemCondition.setIsVisible(true);
            problemCondition.setIsTitleEmpty(false);
            Condition condition = problemCondition.getCondition();
            Long count = problemDAO.count(problemCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.currentPage = pageInfo.getCurrentPage();
            condition.countPerPage = RECORD_PER_PAGE;
            List<Problem> problemList = (List<Problem>) problemDAO.findAll(condition);
            List<ProblemListView> problemListViewList = new ArrayList<>();
            for (Problem problem : problemList) {
                problemListViewList.add(new ProblemListView(problem,
                        getCurrentUser(), problemStatus.get(problem.getProblemId())));
            }
            json.put("pageInfo", pageInfo.getHtmlString());
            json.put("result", "ok");
            json.put("condition", problemCondition);
            json.put("problemList", problemListViewList);
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
    }
}
