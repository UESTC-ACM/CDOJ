package cn.edu.uestc.acmicpc.ioc.entity;

import cn.edu.uestc.acmicpc.training.entity.TrainingContestRankList;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public interface TrainingContestRankListAware {
  public void setTrainingContestRankList(TrainingContestRankList trainingContestRankList);
  public TrainingContestRankList getTrainingContestRankList();
}
