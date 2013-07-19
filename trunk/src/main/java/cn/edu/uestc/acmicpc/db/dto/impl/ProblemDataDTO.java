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

package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Problem entity data transform object.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class ProblemDataDTO extends BaseDTO<Problem> {

  public ProblemDataDTO() {
    super();
  }

  /**
   * Input: problem id
   */
  private Integer problemId;

  public Integer getTimeLimit() {
    return timeLimit;
  }

  public void setTimeLimit(Integer timeLimit) {
    this.timeLimit = timeLimit;
  }

  public Integer getMemoryLimit() {
    return memoryLimit;
  }

  public void setMemoryLimit(Integer memoryLimit) {
    this.memoryLimit = memoryLimit;
  }

  @Ignore
  public Boolean getIsSpj() {
    return isSpj;
  }

  public void setIsSpj(Boolean spj) {
    isSpj = spj;
  }

  public Integer getOutputLimit() {
    return outputLimit;
  }

  public void setOutputLimit(Integer outputLimit) {
    this.outputLimit = outputLimit;
  }

  public Integer getJavaTimeLimit() {
    return javaTimeLimit;
  }

  public void setJavaTimeLimit(Integer javaTimeLimit) {
    this.javaTimeLimit = javaTimeLimit;
  }

  public Integer getJavaMemoryLimit() {
    return javaMemoryLimit;
  }

  public void setJavaMemoryLimit(Integer javaMemoryLimit) {
    this.javaMemoryLimit = javaMemoryLimit;
  }

  private Integer timeLimit;
  private Integer memoryLimit;
  private Boolean isSpj;
  private Integer outputLimit;
  private Integer javaTimeLimit;
  private Integer javaMemoryLimit;

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  @Override
  @Deprecated
  public Problem getEntity() throws AppException {
    Problem problem = super.getEntity();
    problem.setTimeLimit(1000);
    problem.setJavaTimeLimit(3000);
    problem.setMemoryLimit(65535);
    problem.setJavaMemoryLimit(65535);
    problem.setOutputLimit(8192);
    problem.setSolved(0);
    problem.setTried(0);
    problem.setDataCount(0);
    problem.setIsSpj(false);
    problem.setIsVisible(false);
    problem.setProblemId(null);
    return problem;
  }

  @Override
  public void updateEntity(Problem problem) throws AppException {
    super.updateEntity(problem);
    if (getIsSpj() != null)
      problem.setIsSpj(getIsSpj());
  }

  @Override
  protected Class<Problem> getReferenceClass() {
    return Problem.class;
  }
}
