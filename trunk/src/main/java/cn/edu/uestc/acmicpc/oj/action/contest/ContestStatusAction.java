/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.oj.action.contest;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.ICodeDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.view.impl.CodeView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestView;
import cn.edu.uestc.acmicpc.db.view.impl.StatusView;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.CodeDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
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
 * Action for list and search all submit status in contest
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class ContestStatusAction extends BaseAction
        implements StatusConditionAware, StatusDAOAware, ContestDAOAware {

    private Integer targetContestId;

    public Integer getTargetContestId() {
        return targetContestId;
    }

    public void setTargetContestId(Integer targetContestId) {
        this.targetContestId = targetContestId;
    }

    /**
     * StatusDAO for status queries.
     */
    @Autowired
    private IStatusDAO statusDAO;
    @Autowired
    private StatusCondition statusCondition;

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
            if (targetContestId == null)
                throw new AppException("Contest Id is empty!");

            Contest contest = contestDAO.get(targetContestId);
            if (contest == null)
                throw new AppException("Wrong contest ID!");

            if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
                if (!contest.getIsVisible())
                    throw new AppException("Contest doesn't exist");

            ContestView contestView = new ContestView(contest);

            statusCondition.setContestId(contest.getContestId());
            //Contest is still running
            if (contestView.getStatus().equals("Running")) {
                if (currentUser == null)
                    throw new AppException("Please login first!");
                if (currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
                    statusCondition.setUserId(currentUser.getUserId());
            }

            Condition condition = statusCondition.getCondition();
            Long count = statusDAO.count(statusCondition.getCondition());
            PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
            condition.setCurrentPage(pageInfo.getCurrentPage());
            condition.setCountPerPage(RECORD_PER_PAGE);
            condition.addOrder("statusId", false);
            List<Status> statusList = (List<Status>) statusDAO.findAll(condition);
            List<StatusView> statusViewList = new ArrayList<>();
            for (Status status : statusList) {
                StatusView statusView = new StatusView(status);
                statusView.setProblemId(contestView.getProblemList().indexOf(statusView.getProblemId()));
                statusViewList.add(statusView);
            }
            json.put("pageInfo", pageInfo.getHtmlString());
            json.put("result", "ok");
            json.put("statusList", statusViewList);
        } catch (AppException e) {
            json.put("result", "error");
            json.put("error_msg", e.getMessage());
        } catch (Exception e) {
            json.put("result", "error");
            json.put("error_msg", "Unknown exception occurred.");
        }
        return JSON;
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

    @Autowired
    private IContestDAO contestDAO;

    @Override
    public void setContestDAO(IContestDAO contestDAO) {
        this.contestDAO = contestDAO;
    }
}
