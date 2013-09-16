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

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.stereotype.Controller;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link Status}.
 */
public class StatusDTO extends BaseDTO<Status> {

  private Integer statusId;
  private Integer result;
  private Integer memoryCost;
  private Integer timeCost;
  private Integer length;
  private Timestamp time;
  private Integer caseNumber;
  private Integer codeId;
  private Code code;
  private Integer compileInfoId;

  @Ignore
  public Code getCode() {
    return code;
  }

  public void setCode(Code code) {
    this.code = code;
  }

  @Ignore
  public CompileInfo getCompileInfo() {
    return compileInfo;
  }

  public void setCompileInfo(CompileInfo compileInfo) {
    this.compileInfo = compileInfo;
  }

  @Ignore
  public Contest getContest() {
    return contest;
  }

  public void setContest(Contest contest) {
    this.contest = contest;
  }

  @Ignore
  public Problem getProblem() {
    return problem;
  }

  public void setProblem(Problem problem) {
    this.problem = problem;
  }

  @Ignore
  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  @Ignore
  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  private CompileInfo compileInfo;
  private Integer contestId;
  private Contest contest;
  private Integer languageId;
  private Language language;

  @Override
  public Status getEntity() throws AppException {
    Status status = super.getEntity();
    status.setCodeByCodeId(code);
    status.setCompileInfoByCompileInfoId(null);
    status.setContestByContestId(contest);
    status.setLanguageByLanguageId(language);
    status.setResult(Global.OnlineJudgeReturnType.OJ_WAIT.ordinal());
    status.setMemoryCost(0);
    status.setTimeCost(0);
    status.setTime(new Timestamp(new Date().getTime()));
    status.setCaseNumber(0);
    status.setUserByUserId(user);
    status.setProblemByProblemId(problem);
    return status;
  }

  @Override
  public void updateEntity(Status status) throws AppException {
    super.updateEntity(status);
    status.setTime(new Timestamp(new Date().getTime()));
  }

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public Integer getResult() {
    return result;
  }

  public void setResult(Integer result) {
    this.result = result;
  }

  public Integer getMemoryCost() {
    return memoryCost;
  }

  public void setMemoryCost(Integer memoryCost) {
    this.memoryCost = memoryCost;
  }

  public Integer getTimeCost() {
    return timeCost;
  }

  public void setTimeCost(Integer timeCost) {
    this.timeCost = timeCost;
  }

  public Integer getLength() {
    return length;
  }

  public void setLength(Integer length) {
    this.length = length;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Integer getCaseNumber() {
    return caseNumber;
  }

  public void setCaseNumber(Integer caseNumber) {
    this.caseNumber = caseNumber;
  }

  @Ignore
  public Integer getCodeId() {
    return codeId;
  }

  public void setCodeId(Integer codeId) {
    this.codeId = codeId;
  }

  @Ignore
  public Integer getCompileInfoId() {
    return compileInfoId;
  }

  public void setCompileInfoId(Integer compileInfoId) {
    this.compileInfoId = compileInfoId;
  }

  @Ignore
  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  @Ignore
  public Integer getLanguageId() {
    return languageId;
  }

  public void setLanguageId(Integer languageId) {
    this.languageId = languageId;
  }

  @Ignore
  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  @Ignore
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private Integer problemId;
  private Problem problem;
  private Integer userId;
  private User user;

  @Override
  protected Class<Status> getReferenceClass() {
    return Status.class;
  }
}
