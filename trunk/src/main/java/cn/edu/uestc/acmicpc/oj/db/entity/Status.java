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

package cn.edu.uestc.acmicpc.oj.db.entity;

import cn.edu.uestc.acmicpc.oj.annotation.IdSetter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Status information.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
@Table(name = "status", schema = "", catalog = "uestcoj")
@Entity
public class Status implements Serializable {
    private static final long serialVersionUID = 4819326443036942394L;
    private int statusId;

    @Column(name = "statusId", nullable = false, insertable = true,
            updatable = true, length = 10, precision = 0, unique = true)
    @Id
    @GeneratedValue
    public int getStatusId() {
        return statusId;
    }

    @IdSetter
    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    private int result;

    @Column(name = "result", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    private int memoryCost;

    @Column(name = "memoryCost", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getMemoryCost() {
        return memoryCost;
    }

    public void setMemoryCost(int memoryCost) {
        this.memoryCost = memoryCost;
    }

    private int timeCost;

    @Column(name = "timeCost", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }

    private int length;

    @Column(name = "length", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    private Timestamp time;

    @Column(name = "time", nullable = false, insertable = true, updatable = true,
            length = 19, precision = 0)
    @Basic
    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    private int caseNumber;

    @Column(name = "caseNumber", nullable = false, insertable = true, updatable = true,
            length = 10, precision = 0)
    @Basic
    public int getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(int caseNumber) {
        this.caseNumber = caseNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Status status = (Status) o;

        if (caseNumber != status.caseNumber) return false;
        if (length != status.length) return false;
        if (memoryCost != status.memoryCost) return false;
        if (result != status.result) return false;
        if (statusId != status.statusId) return false;
        if (timeCost != status.timeCost) return false;
        if (time != null ? !time.equals(status.time) : status.time != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result1 = statusId;
        result1 = 31 * result1 + result;
        result1 = 31 * result1 + memoryCost;
        result1 = 31 * result1 + timeCost;
        result1 = 31 * result1 + length;
        result1 = 31 * result1 + (time != null ? time.hashCode() : 0);
        result1 = 31 * result1 + caseNumber;
        return result1;
    }

    private Code codeByCodeId;

    @ManyToOne
    @JoinColumn(name = "codeId", referencedColumnName = "codeId", nullable = false)
    public Code getCodeByCodeId() {
        return codeByCodeId;
    }

    public void setCodeByCodeId(Code codeByCodeId) {
        this.codeByCodeId = codeByCodeId;
    }

    private CompileInfo compileInfoByCompileInfoId;

    @ManyToOne
    @JoinColumn(name = "compileInfoId", referencedColumnName = "compileInfoId")
    public CompileInfo getCompileInfoByCompileInfoId() {
        return compileInfoByCompileInfoId;
    }

    public void setCompileInfoByCompileInfoId(CompileInfo compileInfoByCompileInfoId) {
        this.compileInfoByCompileInfoId = compileInfoByCompileInfoId;
    }

    private Contest contestByContestId;

    @ManyToOne
    @JoinColumn(name = "contestId", referencedColumnName = "contestId")
    public Contest getContestByContestId() {
        return contestByContestId;
    }

    public void setContestByContestId(Contest contestByContestId) {
        this.contestByContestId = contestByContestId;
    }

    private Language languageByLanguageId;

    @ManyToOne
    @JoinColumn(name = "languageId", referencedColumnName = "languageId", nullable = false)
    public Language getLanguageByLanguageId() {
        return languageByLanguageId;
    }

    public void setLanguageByLanguageId(Language languageByLanguageId) {
        this.languageByLanguageId = languageByLanguageId;
    }

    private Problem problemByProblemId;

    @ManyToOne
    @JoinColumn(name = "problemId", referencedColumnName = "problemId", nullable = false)
    public Problem getProblemByProblemId() {
        return problemByProblemId;
    }

    public void setProblemByProblemId(Problem problemByProblemId) {
        this.problemByProblemId = problemByProblemId;
    }

    private User userByUserId;

    @ManyToOne
    @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = false)
    public User getUserByUserId() {
        return userByUserId;
    }

    public void setUserByUserId(User userByUserId) {
        this.userByUserId = userByUserId;
    }
}
