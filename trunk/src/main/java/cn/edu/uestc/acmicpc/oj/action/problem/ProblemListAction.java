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
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemListView;
import cn.edu.uestc.acmicpc.ioc.condition.ProblemConditionAware;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.interceptor.AppInterceptor;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * action for list and search problem.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class ProblemListAction extends BaseAction
        implements ProblemConditionAware, ProblemDAOAware,
        StatusConditionAware, StatusDAOAware {

    /**
     * ProblemDAO for problem search.
     */
    @Autowired
    private IProblemDAO problemDAO;

    @Autowired
    private IStatusDAO statusDAO;

    @Autowired
    private ProblemCondition problemCondition;

    @Autowired
    private StatusCondition statusCondition;

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

    @SuppressWarnings("unchecked")
    @Override
    public void onActionExecuting(AppInterceptor.ActionInfo actionInfo) {
        super.onActionExecuting(actionInfo);

        Map<Integer, Global.AuthorStatusType> problemStatus = new HashMap<>();
        try {
            if (currentUser != null) {
                statusCondition.setUserId(currentUser.getUserId());
                statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
                Condition condition = statusCondition.getCondition();
                condition.addProjection(Projections.groupProperty("problemByProblemId"));
                List<Problem> results = (List<Problem>) statusDAO.findAll(condition);
                for (Problem result : results)
                    problemStatus.put(result.getProblemId(), Global.AuthorStatusType.PASS);

                statusCondition.setResultId(null);
                condition = statusCondition.getCondition();
                condition.addProjection(Projections.groupProperty("problemByProblemId"));
                results = (List<Problem>) statusDAO.findAll(condition);
                for (Problem result : results)
                    if (!problemStatus.containsKey(result.getProblemId()))
                        problemStatus.put(result.getProblemId(), Global.AuthorStatusType.FAIL);
            }
        } catch (AppException ignored) {
        }
        session.put("problemStatus", problemStatus);
    }

    @Override
    public void setStatusCondition(StatusCondition statusCondition) {
        this.statusCondition = statusCondition;
    }

    @Override
    public StatusCondition getStatusCondition() {
        return statusCondition;
    }

    @Override
    public void setStatusDAO(IStatusDAO statusDAO) {
        this.statusDAO = statusDAO;
    }
}
