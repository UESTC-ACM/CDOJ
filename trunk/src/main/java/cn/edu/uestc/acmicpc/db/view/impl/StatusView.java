package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.annotation.Ignore;

/**
 * Use for return status information with json type.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
public class StatusView extends View<Status> {
    private Integer statusId;
    private Integer userId;
    private Integer problemId;
    private Integer returnType;

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

    public Integer getProblemId() {
        return problemId;
    }

    @Ignore
    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public Integer getReturnType() {
        return returnType;
    }

    public void setReturnType(Integer returnType) {
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

    public Integer getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(Integer timeCost) {
        this.timeCost = timeCost;
    }

    public Integer getMemoryCost() {
        return memoryCost;
    }

    public void setMemoryCost(Integer memoryCost) {
        this.memoryCost = memoryCost;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    private Integer caseNumber;
    private Integer languageId;
    private Integer length;
    private Integer timeCost;
    private Integer memoryCost;
    private Integer time;

    /**
     * Fetch data from status entity.
     *
     * @param status specific status entity
     */
    public StatusView(Status status) {
        super(status);
        setUserId(status.getUserByUserId().getUserId());
        setProblemId(status.getProblemByProblemId().getProblemId());
        setLanguageId(status.getLanguageByLanguageId().getLanguageId());
    }
}
