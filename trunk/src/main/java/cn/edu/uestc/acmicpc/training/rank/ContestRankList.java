package cn.edu.uestc.acmicpc.training.rank;

import java.util.LinkedList;
import java.util.List;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ContestRankList {
    private List<ContestProblemSummaryView> problemSummary;
    private List<UserRankSummary> userRankSummaryList;

    public ContestRankList(List<String[]> ranklist) {
        problemSummary = new LinkedList<>();
        userRankSummaryList = new LinkedList<>();

        Integer problemCount = ranklist.get(0).length - 4;
        for (int i = 0; i < problemCount; i++) {
            ContestProblemSummaryView contestProblemSummaryView = new ContestProblemSummaryView(i);
            problemSummary.add(contestProblemSummaryView);
        }

        for (int i = 1; i < ranklist.size(); i++) {
            String[] userInfo = ranklist.get(i);
        }
    }

    public List<ContestProblemSummaryView> getProblemSummary() {
        return problemSummary;
    }

    public void setProblemSummary(List<ContestProblemSummaryView> problemSummary) {
        this.problemSummary = problemSummary;
    }

    public List<UserRankSummary> getUserRankSummaryList() {
        return userRankSummaryList;
    }

    public void setUserRankSummaryList(List<UserRankSummary> userRankSummaryList) {
        this.userRankSummaryList = userRankSummaryList;
    }
}
