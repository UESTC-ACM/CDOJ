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

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.view.impl.ContestListView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestProblemSummaryView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestView;
import cn.edu.uestc.acmicpc.ioc.condition.StatusConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ContestDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.ioc.dao.StatusDAOAware;
import cn.edu.uestc.acmicpc.oj.action.file.ExcelExportAction;
import cn.edu.uestc.acmicpc.oj.entity.ContestRankList;
import cn.edu.uestc.acmicpc.oj.entity.ProblemSummaryInfo;
import cn.edu.uestc.acmicpc.oj.entity.UserRankSummary;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.stereotype.Controller;

/**
 * Export rank list
 */
@SuppressWarnings("serial")
@Controller
@LoginPermit(NeedLogin = false)
public class ContestRankListExportAction extends ExcelExportAction implements StatusConditionAware,
    StatusDAOAware, ContestDAOAware, ProblemDAOAware {

  //TODO lyhypacm Please set serialVersionUID to this class

  private InputStream excelStream;

  public Integer getTargetContestId() {
    return targetContestId;
  }

  public void setTargetContestId(Integer targetContestId) {
    this.targetContestId = targetContestId;
  }

  private Integer targetContestId;

  @SuppressWarnings("unchecked")
  public String toExportRankList() {
    List<String[]> table = new LinkedList<>();
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

      ContestView targetContest = new ContestView(contest);

      // Problem information changes.
      Condition condition;

      List<ContestProblemSummaryView> contestProblems = new LinkedList<>();
      for (int id = 0; id < targetContest.getProblemList().size(); id++) {
        Integer problemId = targetContest.getProblemList().get(id);
        Problem problem = problemDAO.get(problemId);
        ContestProblemSummaryView targetProblem =
            new ContestProblemSummaryView(problem, getCurrentUser(), problemStatus.get(problem
                .getProblemId()));
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
          // lock it!
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

      String[] header = {"rank", "user", "solved", "penalty"};
      table.add(header);
      for (UserRankSummary userRankSummary : rankList.getUserRankSummaryList()) {
        String[] row = new String[4 + contestProblems.size()];
        row[0] = userRankSummary.getRank().toString();
        row[1] = userRankSummary.getNickName();
        row[2] = userRankSummary.getSolved().toString();
        row[3] = userRankSummary.getPenalty().toString();
        for (int i = 0; i < userRankSummary.getProblemSummaryInfoList().size(); i++) {
          ProblemSummaryInfo problemSummaryInfo = userRankSummary.getProblemSummaryInfoList().get(i);
          if (problemSummaryInfo.getSolved()) {
            row[i + 4] = problemSummaryInfo.getTried() + "/" + problemSummaryInfo.getSolutionTime();
          } else if (problemSummaryInfo.getTried() > 0) {
            row[i + 4] = problemSummaryInfo.getTried() + "/--";
          } else
            row[i + 4] = "";
        }
        table.add(row);
      }
    } catch (AppException e) {
      e.printStackTrace();
      table.add(new String[]{e.getMessage()});
    }
    excelStream = getExcelInputStream(table);
    return "excel";
  }

  public InputStream getExcelStream() {
    return excelStream;
  }

  public void setExcelStream(InputStream excelStream) {
    this.excelStream = excelStream;
  }

  /**
   * StatusDAO for status queries.
   */
  @Autowired
  private IStatusDAO statusDAO;
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
