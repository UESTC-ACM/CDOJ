package cn.edu.uestc.acmicpc.training.rank;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ContestProblemSummaryView {
    private Integer problemId;
    private Integer solved;
    private Integer tried;

    public ContestProblemSummaryView(Integer problemId) {
        solved = 0;
        tried = 0;
        this.problemId = problemId;
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
