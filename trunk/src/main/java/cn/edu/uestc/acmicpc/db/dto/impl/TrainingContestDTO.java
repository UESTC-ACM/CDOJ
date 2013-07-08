package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingContestDTO extends BaseDTO<TrainingContest> {

    private Integer trainingContestId;
    private Boolean isPersonal;
    private String title;

    public Integer getTrainingContestId() {
        return trainingContestId;
    }

    public void setTrainingContestId(Integer trainingContestId) {
        this.trainingContestId = trainingContestId;
    }

    public Boolean getIsPersonal() {
        return isPersonal;
    }

    public void setIsPersonal(Boolean personal) {
        isPersonal = personal;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    protected Class<TrainingContest> getReferenceClass() {
        return TrainingContest.class;
    }

    @Override
    public TrainingContest getEntity() throws AppException {
        TrainingContest trainingContest = super.getEntity();
        trainingContest.setIsPersonal(true);
        return trainingContest;
    }
}
