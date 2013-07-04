package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.db.view.base.View;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingStatusTeamView extends View<TrainingStatus> {

    private Integer contestId;
    private String contestName;
    private Integer rating;
    private Integer volatility;

    public TrainingStatusTeamView(TrainingStatus trainingStatus) {
        super(trainingStatus);

    }
}
