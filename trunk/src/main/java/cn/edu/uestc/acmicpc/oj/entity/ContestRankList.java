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
import cn.edu.uestc.acmicpc.db.view.impl.ContestListView;
import cn.edu.uestc.acmicpc.db.view.impl.ContestProblemSummaryView;
import com.opensymphony.xwork2.util.ArrayUtils;
import org.apache.commons.collections.ListUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ContestRankList {
    private ContestListView contestSummary;
    private List<ContestProblemSummaryView> problemSummary;
    private List<UserRankSummary> userRankSummaryList;

    public ContestListView getContestSummary() {
        return contestSummary;
    }

    public void setContestSummary(ContestListView contestSummary) {
        this.contestSummary = contestSummary;
    }

    public List<ContestProblemSummaryView> getProblemSummary() {
        return problemSummary;
    }

    public void setProblemSummary(List<ContestProblemSummaryView> problemSummary) {
        this.problemSummary = problemSummary;
    }

    public List<UserRankSummary> getUserRankSummaryList() {
        return userRankSummaryList;
    }

    public void setUserRankSummaryList(List<UserRankSummary> userRankSummaryList) {
        this.userRankSummaryList = userRankSummaryList;
    }

    public ContestRankList(ContestListView contestSummary, List<ContestProblemSummaryView> problemSummary) {
        this.contestSummary = contestSummary;
        this.problemSummary = problemSummary;
        userRankSummaryList = new LinkedList<>();
    }

    public void updateRankList(Status status) {
        Boolean isNewUser = true;
        if (userRankSummaryList.size() > 0) {
            for (UserRankSummary userRankSummary: userRankSummaryList) {
                if (userRankSummary.getUserId().equals(status.getUserByUserId().getUserId())) {
                    isNewUser = false;
                    break;
                }
            }
        }
        if (isNewUser)
            userRankSummaryList.add(new UserRankSummary(status.getUserByUserId(), problemSummary));

        for (UserRankSummary userRankSummary: userRankSummaryList) {
            if (userRankSummary.getUserId().equals(status.getUserByUserId().getUserId())) {
                userRankSummary.updateUserRank(status, contestSummary, problemSummary);
                break;
            }
        }

        Collections.sort(userRankSummaryList, new Comparator<UserRankSummary>() {
            @Override
            public int compare(UserRankSummary a, UserRankSummary b) {
                if (a.getSolved().equals(b.getSolved())) {
                    if (a.getPenalty().equals(b.getPenalty())) {
                        return a.getNickName().compareTo(b.getNickName());
                    }
                    return a.getPenalty().compareTo(b.getPenalty());
                }
                return (b.getSolved().compareTo(a.getSolved()));
            }
        });

        for (int i = 0; i < userRankSummaryList.size(); i++)
            userRankSummaryList.get(i).setRank(i + 1);
    }
}
