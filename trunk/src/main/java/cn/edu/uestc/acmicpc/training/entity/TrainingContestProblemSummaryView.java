package cn.edu.uestc.acmicpc.training.entity;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingContestProblemSummaryView {
    private Integer problemId;
    private char order;
    private Integer solved;
    private Integer tried;

    public TrainingContestProblemSummaryView(Integer problemId) {
        solved = 0;
        tried = 0;
        this.problemId = problemId;
        order = (char)('A' + problemId);
    }

    public char getOrder() {
        return order;
    }

    public void setOrder(char order) {
        this.order = order;
    }

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
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
}
