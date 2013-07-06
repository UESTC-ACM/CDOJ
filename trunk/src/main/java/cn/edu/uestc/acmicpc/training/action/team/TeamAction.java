package cn.edu.uestc.acmicpc.training.action.team;

import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.view.impl.TrainingStatusTeamView;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(NeedLogin = false)
public class TeamAction extends BaseAction {

    private List<TrainingStatusTeamView> trainingStatusTeamViewList;

    public List<TrainingStatusTeamView> getTrainingStatusTeamViewList() {
        return trainingStatusTeamViewList;
    }

    public void setTrainingStatusTeamViewList(List<TrainingStatusTeamView> trainingStatusTeamViewList) {
        this.trainingStatusTeamViewList = trainingStatusTeamViewList;
    }

    public String toTeamInfoPage() {
        return SUCCESS;
    }

    public String toTeamInfoList() {
        trainingStatusTeamViewList = new LinkedList<>();
        Integer lastRating = 1200;
        Integer lastVolatility = 550;
        for (int i = 0; i < 10; i++) {
            TrainingStatus trainingStatus = new TrainingStatus();
            Random random = new Random();
            trainingStatus.setRating(lastRating.doubleValue() + Math.abs(random.nextInt()) % 200);
            trainingStatus.setVolatility(lastVolatility.doubleValue() + Math.abs(random.nextInt()) % 10);

            TrainingStatusTeamView trainingStatusTeamView = new TrainingStatusTeamView(trainingStatus);

            trainingStatusTeamView.setContestId(i + 1);
            trainingStatusTeamView.setContestName("Contest " + (i + 1));
            trainingStatusTeamView.setRatingVary(trainingStatusTeamView.getRating() - lastRating);
            trainingStatusTeamView.setVolatilityVary(trainingStatusTeamView.getVolatility() - lastVolatility);

            trainingStatusTeamViewList.add(trainingStatusTeamView);
            lastRating = trainingStatusTeamView.getRating();
            lastVolatility = trainingStatusTeamView.getVolatility();
        }
        json.put("teamSummary", trainingStatusTeamViewList);
        json.put("result", "ok");
        return JSON;
    }
}
