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

package cn.edu.uestc.acmicpc.db.view.impl;

import java.util.LinkedList;
import java.util.List;

import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ProblemListView extends View<Problem> {

  /**
   * State: not submitted
   */
  public static final int NONE = 0;
  /**
   * State: submitted but failed
   */
  public static final int FAILED = 1;
  /**
   * State: submitted and passed
   */
  public static final int PASSED = 2;

  private Integer state;

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  private Integer problemId;
  private String title;
  private String source;
  private Integer solved;
  private Integer tried;
  private Boolean isSPJ;
  private Boolean isVisible;
  private Integer difficulty;
  private List<String> tags;

  @Deprecated
  public ProblemListView(Problem problem) {
    super(problem);
  }

  @Deprecated
  public ProblemListView(Problem problem, User currentUser, Global.AuthorStatusType type)
      throws AppException {
    // TODO(mzry1992): use dto transfer.
    super(problem);
    List<String> list = new LinkedList<>();
//    Collection<ProblemTag> problemTags = problem.getProblemtagsByProblemId();
//    for (ProblemTag problemTag : problemTags) {
//      list.add(StringEscapeUtils.escapeHtml4(problemTag.getTagByTagId().getName()));
//    }
    setTags(list);
    if (currentUser == null) {
      setState(NONE);
    } else {
      setState(type == null ? NONE : type.ordinal());
    }
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Integer getSolved() {
    return solved;
  }

  public void setSolved(Integer solved) {
    this.solved = solved;
  }

  public Integer getTried() {
    return tried;
  }

  public void setTried(Integer tried) {
    this.tried = tried;
  }

  public Boolean getIsSpj() {
    return isSPJ;
  }

  public void setIsSpj(Boolean SPJ) {
    isSPJ = SPJ;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public Integer getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Integer difficulty) {
    this.difficulty = difficulty;
  }

  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }
}
