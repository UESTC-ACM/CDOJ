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
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.*;
import cn.edu.uestc.acmicpc.db.view.impl.*;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.CodeDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.entity.ContestRankList;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.struts2.interceptor.validation.SkipValidation;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Action for list and search all submit status in contest
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class ContestRankListAction extends BaseAction
        implements StatusConditionAware, StatusDAOAware, ContestDAOAware, ProblemDAOAware {

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
     * Get contest rank list action.
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
    public String toRankList() {
        try {
            if (targetContestId == null)
                throw new AppException("Contest Id is empty!");

            Contest contest = contestDAO.get(targetContestId);
            if (contest == null)
                throw new AppException("Wrong contest ID!");

            if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
                if (!contest.getIsVisible())
                    throw new AppException("Contest doesn't exist");

            Timestamp contestEndTime = new Timestamp(contest.getTime().getTime() + contest.getLength() * 1000);

            ContestView targetContest = new ContestView(contest);

            //Problem information changes.
            Condition condition;

            List<ContestProblemSummaryView> contestProblems = new LinkedList<>();
            for (int id = 0; id < targetContest.getProblemList().size(); id++) {
                Integer problemId = targetContest.getProblemList().get(id);
                Problem problem = problemDAO.get(problemId);
                ContestProblemSummaryView targetProblem = new ContestProblemSummaryView(problem,
                        getCurrentUser(), problemStatus.get(problem.getProblemId()));
                targetProblem.setTried(0);
                targetProblem.setSolved(0);
                targetProblem.setOrder((char) ('A' + id));

                contestProblems.add(targetProblem);
            }

            ContestRankList rankList = getGlobal().getContestRankListMap().get(contest.getContestId());
            if (rankList == null) {
                rankList = new ContestRankList(new ContestListView(contest), contestProblems);
                getGlobal().getContestRankListMap().put(contest.getContestId(), rankList);
                rankList.setLastUpdateTime(new Timestamp(0));
                rankList.setLock(false);
            }

            if (!rankList.getLock()) {
                if (new Date().getTime() - rankList.getLastUpdateTime().getTime() >= 5 * 1000) {
                    System.out.println(rankList.getLastUpdateTime() + " " + rankList.getLock());
                    //lock it!
                    rankList.setLock(true);

                    rankList.clear(new ContestListView(contest), contestProblems);

                    statusCondition.clear();
                    statusCondition.setContestId(contest.getContestId());
                    statusCondition.setStartTime(contest.getTime());
                    statusCondition.setEndTime(contestEndTime);
                    condition = statusCondition.getCondition();
                    condition.addOrder("statusId", true);
                    List<Status> statusList = (List<Status>) statusDAO.findAll(condition);

                    for (Status status : statusList)
                        rankList.updateRankList(status);

                    System.out.println("Success update!");
                    rankList.setLastUpdateTime(new Timestamp(new Date().getTime()));
                    rankList.setLock(false);
                }
            }

            json.put("rankList", rankList);
            json.put("result", "ok");
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

    @Autowired
    private IProblemDAO problemDAO;

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }
}
