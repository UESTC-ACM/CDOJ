package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.view.base.View;

/**
 * Created with IntelliJ IDEA.
 * User: mzry1992
 * Date: 13-7-10
 * Time: 下午11:12
 * To change this template use File | Settings | File Templates.
 */
public class TrainingContestListView extends View<TrainingContest> {
    private Integer trainingContestId;
    private String title;
    private Boolean isPersonal;

    public Integer getTrainingContestId() {
        return trainingContestId;
    }

    public void setTrainingContestId(Integer trainingContestId) {
        this.trainingContestId = trainingContestId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean personal) {
        isPersonal = personal;
    }

    public TrainingContestListView(TrainingContest trainingContest) {
        super(trainingContest);
    }
}
