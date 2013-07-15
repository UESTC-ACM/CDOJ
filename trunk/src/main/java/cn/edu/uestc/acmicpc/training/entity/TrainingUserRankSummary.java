package cn.edu.uestc.acmicpc.training.entity;

import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingUserView;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.TrainingRankListFormatParser;
import cn.edu.uestc.acmicpc.util.exception.ParserException;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingUserRankSummary {
    private Integer penalty;
    private Integer solved;

    private Integer rank;
    private TrainingUserView user;
    private Integer userId;
    private String nickName;
    private TrainingProblemSummaryInfo[] trainingProblemSummaryInfoList;

    public TrainingUserRankSummary(TrainingUser user, String[] userInfo, Integer type) throws ParserException {
        setUser(new TrainingUserView(user));
        userId = user.getTrainingUserId();
        nickName = user.getName();
        penalty = solved = 0;

        if (type != Global.TrainingContestType.NORMAL.ordinal()) {
            trainingProblemSummaryInfoList = new TrainingProblemSummaryInfo[0];
            penalty = Integer.parseInt(userInfo[1]);
            System.out.println(penalty + " " + userInfo[0] + " " + userInfo[1]);
        } else {
            Integer problemCount = userInfo.length - 1;

            trainingProblemSummaryInfoList = new TrainingProblemSummaryInfo[problemCount];
            for (int i = 0; i < problemCount; i++) {
                TrainingProblemSummaryInfo trainingProblemSummaryInfo = TrainingRankListFormatParser.getProblemSummaryInfo(userInfo[i + 1]);
                if (trainingProblemSummaryInfo.getSolved()) {
                    solved++;
                    penalty += trainingProblemSummaryInfo.getPenalty();
                }
                trainingProblemSummaryInfoList[i] = trainingProblemSummaryInfo;
            }
        }
    }

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public TrainingUserView getUser() {
        return user;
    }

    public void setUser(TrainingUserView user) {
        this.user = user;
    }

    public TrainingProblemSummaryInfo[] getTrainingProblemSummaryInfoList() {
        return trainingProblemSummaryInfoList;
    }

    public void setTrainingProblemSummaryInfoList(TrainingProblemSummaryInfo[] trainingProblemSummaryInfoList) {
        this.trainingProblemSummaryInfoList = trainingProblemSummaryInfoList;
    }
}
