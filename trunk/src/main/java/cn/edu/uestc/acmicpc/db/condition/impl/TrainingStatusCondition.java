package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * // TODO(mzry1992) Description
 */
public class TrainingStatusCondition extends BaseCondition {
//
//  private static final Logger LOGGER = LogManager.getLogger(TrainingStatusCondition.class);
//
//  @Autowired
//  public TrainingStatusCondition(ITrainingContestDAO trainingContestDAO) {
//    this.trainingContestDAO = trainingContestDAO;
//  }
//
//  private Integer startId;
//  private Integer endId;
//  private Integer trainingContestId;
//
//  @Exp(mapField = "trainingStatusId", type = ConditionType.ge)
//  public Integer getStartId() {
//    return startId;
//  }
//
//  public void setStartId(Integer startId) {
//    this.startId = startId;
//  }
//
//  @Exp(mapField = "trainingStatusId", type = ConditionType.le)
//  public Integer getEndId() {
//    return endId;
//  }
//
//  public void setEndId(Integer endId) {
//    this.endId = endId;
//  }
//
//  public Integer getTrainingContestId() {
//    return trainingContestId;
//  }
//
//  public void setTrainingContestId(Integer trainingContestId) {
//    this.trainingContestId = trainingContestId;
//  }
//
//  @Override
//  public void invoke(Condition condition) {
//    super.invoke(condition);
//
//    if (trainingContestId != null) {
//      try {
//        TrainingContest trainingContest = trainingContestDAO.get(trainingContestId);
//        if (trainingContest == null) {
//          return;
//        }
//        JoinedProperty joinedProperty =
//            new JoinedProperty(Restrictions.eq("trainingContestByTrainingContestId",
//                trainingContest), trainingContest.getTrainingContestId(), ConditionType.eq);
//        condition.addJoinedProperty("trainingContestByTrainingContestId", joinedProperty);
//      } catch (AppException e) {
//        LOGGER.error(e);
//      }
//    }
//  }
//
//  private ITrainingContestDAO trainingContestDAO;
}
