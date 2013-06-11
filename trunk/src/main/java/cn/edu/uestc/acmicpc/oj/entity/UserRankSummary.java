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

package cn.edu.uestc.acmicpc.oj.entity;

import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.ContestListView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestProblemSummaryView;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ObjectUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class UserRankSummary {
    private Integer penalty;
    private Integer solved;

    private Integer rank;
    private Integer userId;
    private String userName;
    private String nickName;
    private String email;
    private List<ProblemSummaryInfo> problemSummaryInfoList;

    public UserRankSummary(User user, List<ContestProblemSummaryView> problemSummary) {
        this.solved = this.penalty = 0;
        this.userId = user.getUserId();
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.email = user.getEmail();
        problemSummaryInfoList = new LinkedList<>();
        for (ContestProblemSummaryView contestProblemSummaryView : problemSummary) {
            problemSummaryInfoList.add(new ProblemSummaryInfo(contestProblemSummaryView));
        }
    }

    public void updateUserRank(Status status,
                               ContestListView contestSummary,
                               List<ContestProblemSummaryView> problemSummary,
                               Boolean visible) {
        for (int id = 0; id < problemSummary.size(); id++) {
            ProblemSummaryInfo problemSummaryInfo = problemSummaryInfoList.get(id);
            ContestProblemSummaryView contestProblemSummaryView = problemSummary.get(id);

            if (problemSummaryInfo.getProblemId().equals(status.getProblemByProblemId().getProblemId())) {
                contestProblemSummaryView.setTried(contestProblemSummaryView.getTried() + 1);
                //If this problem has passed
                if (problemSummaryInfo.getSolved())
                    return;

                //After 4:00:00
                if (visible != null && !visible) {
                    problemSummaryInfo.setPending(true);
                    problemSummaryInfo.setTried(problemSummaryInfo.getTried() + 1);
                } //If AC
                else if (status.getResult() == Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
                    problemSummaryInfo.setSolved(true);
                    problemSummaryInfo.setSolutionRunId(status.getStatusId());
                    Long timePassed = (status.getTime().getTime() - contestSummary.getTime().getTime()) / 60 / 1000;
                    problemSummaryInfo.setSolutionTime(timePassed.intValue());
                    problemSummaryInfo.setPenalty(problemSummaryInfo.getSolutionTime() + 20 * problemSummaryInfo.getTried());
                    problemSummaryInfo.setTried(problemSummaryInfo.getTried() + 1);

                    this.solved++;
                    this.penalty += problemSummaryInfo.getPenalty();

                    if (contestProblemSummaryView.getSolved() == 0)
                        problemSummaryInfo.setFirstSolved(true);
                    contestProblemSummaryView.setSolved(contestProblemSummaryView.getSolved() + 1);
                } //If pending
                else if (status.getResult() == Global.OnlineJudgeReturnType.OJ_JUDGING.ordinal() ||
                        status.getResult() == Global.OnlineJudgeReturnType.OJ_WAIT.ordinal() ||
                        status.getResult() == Global.OnlineJudgeReturnType.OJ_REJUDGING.ordinal()) {
                    problemSummaryInfo.setPending(true);
                    problemSummaryInfo.setTried(problemSummaryInfo.getTried() + 1);
                } //WA
                else
                    problemSummaryInfo.setTried(problemSummaryInfo.getTried() + 1);
                return;
            }
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPenalty() {
        return penalty;
    }

    public void setPenalty(Integer penalty) {
        this.penalty = penalty;
    }

    public Integer getSolved() {
        return solved;
    }

    public void setSolved(Integer solved) {
        this.solved = solved;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public List<ProblemSummaryInfo> getProblemSummaryInfoList() {
        return problemSummaryInfoList;
    }

    public void setProblemSummaryInfoList(List<ProblemSummaryInfo> problemSummaryInfoList) {
        this.problemSummaryInfoList = problemSummaryInfoList;
    }

}
