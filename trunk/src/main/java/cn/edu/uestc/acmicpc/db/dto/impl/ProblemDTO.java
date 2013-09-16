/*
 *
 *  cdoj, UESTC ACMICPC Online Judge
 *  Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  	mzry1992 <@link muziriyun@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.dto.impl;

import org.springframework.stereotype.Controller;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link Problem}.
 */
public class ProblemDTO extends BaseDTO<Problem> {

  public ProblemDTO() {
    super();
  }

  /**
   * Input: problem id
   */
  private Integer problemId;
  private String title;
  private String description;
  private String input;
  private String output;
  private String sampleInput;
  private String sampleOutput;
  private String hint;
  private String source;

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

  public String getInput() {
    return input;
  }

  public void setInput(String input) {
    this.input = input;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public String getSampleInput() {
    return sampleInput;
  }

  public void setSampleInput(String sampleInput) {
    this.sampleInput = sampleInput;
  }

  public String getSampleOutput() {
    return sampleOutput;
  }

  public void setSampleOutput(String sampleOutput) {
    this.sampleOutput = sampleOutput;
  }

  public String getHint() {
    return hint;
  }

  public void setHint(String hint) {
    this.hint = hint;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  @Override
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
    problem.setDifficulty(1);
    return problem;
  }

  @Override
  public void updateEntity(Problem problem) throws AppException {
    super.updateEntity(problem);
  }

  @Override
  protected Class<Problem> getReferenceClass() {
    return Problem.class;
  }
}
