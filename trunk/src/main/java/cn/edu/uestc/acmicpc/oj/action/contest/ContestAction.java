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
import cn.edu.uestc.acmicpc.db.view.impl.ContestProblemSummaryView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestProblemView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestView;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.interceptor.AppInterceptor;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.sql.Timestamp;
import java.util.*;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
@LoginPermit(NeedLogin = false)
public class ContestAction extends BaseAction implements ContestDAOAware, ProblemDAOAware,
    StatusDAOAware, StatusConditionAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = -8675313474670269408L;

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

  /**
   * Get contest real time information.
   * <p/>
   * Find new Clarification or current problem summary.
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok", "pageInfo":<strong>PageInfo object</strong>, "contestProblems",
   * <strong>Current problem summary</strong>, "time":<strong>Server time</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   * 
   * @return <strong>JSON</strong> signal
   */
  public String toContestChanges() {
    try {
      if (targetContestId == null)
        throw new AppException("Contest Id is empty!");

      Contest contest = contestDAO.get(targetContestId);
      if (contest == null)
        throw new AppException("Wrong contest ID!");

      if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
        if (!contest.getIsVisible())
          throw new AppException("Contest doesn't exist");

      targetContest = new ContestView(contest);
      Timestamp contestEndTime =
          new Timestamp(contest.getTime().getTime() + contest.getLength() * 1000);

      // Sync time left
      json.put("timeLeft", (contestEndTime.getTime() - new Date().getTime()) / 1000);

      if (contest.getLength() == 5 * 60 * 60) {
        if (targetContest.getStatus().equals("Running"))
          contestEndTime = new Timestamp(contest.getTime().getTime() + 4 * 60 * 60 * 1000);
      }

      // Problem information changes.
      Condition condition;
      Long count;

      List<ContestProblemSummaryView> contestProblems = new LinkedList<>();
      if (!targetContest.getStatus().equals("Pending") || (currentUser != null && currentUser.getType() == Global.AuthenticationType.ADMIN.ordinal())) {
        for (int id = 0; id < targetContest.getProblemList().size(); id++) {
          Integer problemId = targetContest.getProblemList().get(id);
          Problem problem = problemDAO.get(problemId);
          ContestProblemSummaryView targetProblem =
              new ContestProblemSummaryView(problem, getCurrentUser(), problemStatus.get(problem
                  .getProblemId()));
          targetProblem.setOrder((char) ('A' + id));

          // get solved
          statusCondition.clear();
          statusCondition.setStartTime(contest.getTime());
          statusCondition.setEndTime(contestEndTime);
          statusCondition.setContestId(contest.getContestId());
          statusCondition.setProblemId(problem.getProblemId());
          statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
          condition = statusCondition.getCondition();
          condition.addProjection(Projections.countDistinct("userByUserId"));
          count = statusDAO.customCount(condition);
          targetProblem.setSolved((int) count.longValue());
          // get tried
          statusCondition.clear();
          statusCondition.setStartTime(contest.getTime());
          statusCondition.setEndTime(contestEndTime);
          statusCondition.setContestId(contest.getContestId());
          statusCondition.setProblemId(problem.getProblemId());
          condition = statusCondition.getCondition();
          count = statusDAO.count(condition);
          targetProblem.setTried((int) count.longValue());

          contestProblems.add(targetProblem);
        }
      }
      json.put("contestProblems", contestProblems);

      // Clarification

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

  /**
   * Goto contest page.
   * 
   * @return <strong>SUCCESS Signal</strong>
   */
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

      targetContest = new ContestView(contest);
      Timestamp contestEndTime =
          new Timestamp(contest.getTime().getTime() + contest.getLength() * 1000);
      if (contest.getLength() == 5 * 60 * 60) {
        if (targetContest.getStatus().equals("Running"))
          contestEndTime = new Timestamp(contest.getTime().getTime() + 4 * 60 * 60 * 1000);
      }
      Condition condition;
      Long count;

      contestProblems = new LinkedList<>();
      if (targetContest.getStatus().equals("Pending") && (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()))
        return SUCCESS;
      for (int id = 0; id < targetContest.getProblemList().size(); id++) {
        Integer problemId = targetContest.getProblemList().get(id);
        Problem problem = problemDAO.get(problemId);
        ContestProblemView targetProblem = new ContestProblemView(problem);
        targetProblem.setOrder((char) ('A' + id));

        // get solved
        statusCondition.clear();
        statusCondition.setStartTime(contest.getTime());
        statusCondition.setEndTime(contestEndTime);
        statusCondition.setContestId(contest.getContestId());
        statusCondition.setProblemId(problem.getProblemId());
        statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
        condition = statusCondition.getCondition();
        condition.addProjection(Projections.countDistinct("userByUserId"));
        count = statusDAO.customCount(condition);
        targetProblem.setSolved((int) count.longValue());
        // get tried
        statusCondition.clear();
        statusCondition.setStartTime(contest.getTime());
        statusCondition.setEndTime(contestEndTime);
        statusCondition.setContestId(contest.getContestId());
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

  @SuppressWarnings("unchecked")
  @Override
  public void onActionExecuting(AppInterceptor.ActionInfo actionInfo) {
    super.onActionExecuting(actionInfo);

    Map<Integer, Global.AuthorStatusType> problemStatus = new HashMap<>();
    try {
      if (targetContestId == null)
        throw new AppException("Contest Id is empty!");

      Contest contest = contestDAO.get(targetContestId);
      if (contest == null)
        throw new AppException("Wrong contest ID!");

      if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
        if (!contest.getIsVisible())
          throw new AppException("Contest doesn't exist");

      Timestamp contestEndTime =
          new Timestamp(contest.getTime().getTime() + contest.getLength() * 1000);

      if (currentUser != null) {
        problemStatus.clear();

        statusCondition.clear();
        statusCondition.setStartTime(contest.getTime());
        statusCondition.setEndTime(contestEndTime);
        statusCondition.setContestId(contest.getContestId());
        statusCondition.setUserId(currentUser.getUserId());
        statusCondition.setResultId(null);
        Condition condition = statusCondition.getCondition();
        condition.addProjection(Projections.groupProperty("problemByProblemId.problemId"));
        List<Integer> results = (List<Integer>) statusDAO.findAll(condition);
        for (Integer result : results)
          if (!problemStatus.containsKey(result))
            problemStatus.put(result, Global.AuthorStatusType.FAIL);

        statusCondition.clear();
        statusCondition.setStartTime(contest.getTime());
        statusCondition.setEndTime(contestEndTime);
        statusCondition.setContestId(contest.getContestId());
        statusCondition.setUserId(currentUser.getUserId());
        statusCondition.setResultId(Global.OnlineJudgeReturnType.OJ_AC.ordinal());
        condition = statusCondition.getCondition();
        condition.addProjection(Projections.groupProperty("problemByProblemId.problemId"));
        results = (List<Integer>) statusDAO.findAll(condition);
        for (Integer result : results)
          problemStatus.put(result, Global.AuthorStatusType.PASS);
      }
    } catch (AppException ignored) {
    }
    session.put("problemStatus", problemStatus);
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
