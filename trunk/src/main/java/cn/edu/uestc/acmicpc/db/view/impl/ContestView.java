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

package cn.edu.uestc.acmicpc.db.view.impl;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.view.base.View;

/**
 * Contest view
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 * @version 1
 */
public class ContestView extends View<Contest> {

  private Integer contestId;
  private String description;
  private Boolean isVisible;
  private Integer length;
  private Timestamp time;
  private String title;
  private Byte type;
  private List<Integer> problemList;
  private String problemListString;
  private String status;
  private Long timeLeft;

  public Long getTimeLeft() {
    return timeLeft;
  }

  public void setTimeLeft(Long timeLeft) {
    this.timeLeft = timeLeft;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Boolean getVisible() {
    return isVisible;
  }

  public void setVisible(Boolean visible) {
    isVisible = visible;
  }

  public String getProblemListString() {
    return problemListString;
  }

  public void setProblemListString(String problemListString) {
    this.problemListString = problemListString;
  }

  public List<Integer> getProblemList() {
    return problemList;
  }

  public void setProblemList(List<Integer> problemList) {
    this.problemList = problemList;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    isVisible = visible;
  }

  @Deprecated
  public ContestView(Contest contest) {
    super(contest);
    Timestamp now = new Timestamp(new Date().getTime());
    if (time.after(now))
      status = "Pending";
    else {
      Timestamp endTime = new Timestamp(time.getTime() + length * 1000);
      if (endTime.after(now)) {
        status = "Running";
        timeLeft = (endTime.getTime() - now.getTime()) / 1000;
      } else
        status = "Ended";
    }

    List<ContestProblem> contestProblems =
        (List<ContestProblem>) contest.getContestProblemsByContestId();
    Collections.sort(contestProblems, new Comparator<ContestProblem>() {

      @Override
      public int compare(ContestProblem a, ContestProblem b) {
        return a.getOrder().compareTo(b.getOrder());
      }
    });
    problemList = new LinkedList<>();
    problemListString = "";
    for (ContestProblem contestProblem : contest.getContestProblemsByContestId()) {
      Problem problem = contestProblem.getProblemByProblemId();
      problemList.add(problem.getProblemId());
      problemListString = problemListString + problem.getProblemId() + ",";
    }
  }
}
