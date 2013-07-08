package cn.edu.uestc.acmicpc.training.rank;

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
    private List<ProblemSummaryInfo> problemSummaryInfoList;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<ProblemSummaryInfo> getProblemSummaryInfoList() {
        return problemSummaryInfoList;
    }

    public void setProblemSummaryInfoList(List<ProblemSummaryInfo> problemSummaryInfoList) {
        this.problemSummaryInfoList = problemSummaryInfoList;
    }
}
