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

package cn.edu.uestc.acmicpc.oj.action.contest;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.view.impl.ContestProblemView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestView;
import cn.edu.uestc.acmicpc.db.view.impl.ProblemView;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class ContestAction extends BaseAction implements ContestDAOAware, ProblemDAOAware, StatusDAOAware, StatusConditionAware {

    private Integer targetContestId;

    public Integer getTargetContestId() {
        return targetContestId;
    }

    public void setTargetContestId(Integer targetContestId) {
        this.targetContestId = targetContestId;
    }

    private ContestView targetContest;

    private List<ContestProblemView> contestProblems;

    public List<ContestProblemView> getContestProblems() {
        return contestProblems;
    }

    public void setContestProblems(List<ContestProblemView> contestProblems) {
        this.contestProblems = contestProblems;
    }

    public ContestView getTargetContest() {
        return targetContest;
    }

    public void setTargetContest(ContestView targetContest) {
        this.targetContest = targetContest;
    }

    public String toContestProblemList() {
        return JSON;
    }

    public String toContest() {
        try {
            if (targetContestId == null)
                throw new AppException("Contest Id is empty!");

            Contest contest = contestDAO.get(targetContestId);
            if (contest == null)
                throw new AppException("Wrong contest ID!");

            if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
                if (!contest.getIsVisible())
                    throw new AppException("Contest doesn't exist");

            Condition condition;
            Long count;

            targetContest = new ContestView(contest);
            contestProblems = new LinkedList<>();
            for (int id = 0; id < targetContest.getProblemList().size(); id++) {
                Integer problemId = targetContest.getProblemList().get(id);
                Problem problem = problemDAO.get(problemId);
                ContestProblemView targetProblem = new ContestProblemView(problem);
                targetProblem.setOrder((char)('A' + id));

                //get solved
                statusCondition.clear();
                statusCondition.setProblemId(problem.getProblemId());
                statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
                condition = statusCondition.getCondition();
                condition.addProjection(Projections.countDistinct("userByUserId"));
                count = statusDAO.customCount(condition);
                targetProblem.setSolved((int) count.longValue());
                //get tried
                statusCondition.clear();
                statusCondition.setProblemId(problem.getProblemId());
                condition = statusCondition.getCondition();
                count = statusDAO.count(condition);
                targetProblem.setTried((int) count.longValue());

                contestProblems.add(targetProblem);
            }
        } catch (AppException e) {
            return redirect(getActionURL("/", "index"), e.getMessage());
        } catch (Exception e) {
            return redirect(getActionURL("/", "index"), "Unknown exception occurred.");
        }
        return SUCCESS;
    }

    @Autowired
    private IContestDAO contestDAO;
    @Autowired
    private IProblemDAO problemDAO;
    @Autowired
    private IStatusDAO statusDAO;

    @Override
    public void setContestDAO(IContestDAO contestDAO) {
        this.contestDAO = contestDAO;
    }

    @Override
    public void setProblemDAO(IProblemDAO problemDAO) {
        this.problemDAO = problemDAO;
    }

    @Autowired
    private StatusCondition statusCondition;

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
