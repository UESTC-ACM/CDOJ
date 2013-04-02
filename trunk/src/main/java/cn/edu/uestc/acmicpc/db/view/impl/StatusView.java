/*
 * cdoj, UESTC ACMICPC Online Judge
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;

import java.sql.Timestamp;

/**
 * Use for return status information with json type.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class StatusView extends View<Status> {
    private Integer statusId;
    private Integer userId;
    private String userName;
    private Integer problemId;
    private String returnType;
    private Integer returnTypeId;

    public Integer getReturnTypeId() {
        return returnTypeId;
    }

    @Ignore
    public void setReturnTypeId(Integer returnTypeId) {
        this.returnTypeId = returnTypeId;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public Integer getUserId() {
        return userId;
    }

    @Ignore
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    @Ignore
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getProblemId() {
        return problemId;
    }

    @Ignore
    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getReturnType() {
        return returnType;
    }

    @Ignore
    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public Integer getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(Integer caseNumber) {
        this.caseNumber = caseNumber;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    @Ignore
    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public String getTimeCost() {
        return timeCost;
    }

    @Ignore
    public void setTimeCost(String timeCost) {
        this.timeCost = timeCost;
    }

    public String getMemoryCost() {
        return memoryCost;
    }

    @Ignore
    public void setMemoryCost(String memoryCost) {
        this.memoryCost = memoryCost;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    private Integer caseNumber;
    private Integer languageId;
    private String language;

    public String getLanguage() {
        return language;
    }

    @Ignore
    public void setLanguage(String language) {
        this.language = language;
    }

    private Integer length;
    private String timeCost;
    private String memoryCost;
    private Timestamp time;

    /**
     * Fetch data from status entity.
     *
     * @param status specific status entity
     */
    public StatusView(Status status) {
        super(status);
        if (status.getResult() == Global.OnlineJudgeReturnType.OJ_AC.ordinal()) {
            setTimeCost(Integer.toString(status.getTimeCost()));
            setMemoryCost(Integer.toString(status.getMemoryCost()));
        } else {
            setTimeCost("");
            setMemoryCost("");
        }
        setUserId(status.getUserByUserId().getUserId());
        setUserName(status.getUserByUserId().getUserName());
        setProblemId(status.getProblemByProblemId().getProblemId());
        setLanguageId(status.getLanguageByLanguageId().getLanguageId());
        setReturnTypeId(status.getResult());
        setReturnType(StringUtil.getStatusDescription(Global.OnlineJudgeReturnType.values()[status.getResult()],
                status.getCaseNumber()));
        setLanguage(status.getLanguageByLanguageId().getName());
    }
}
