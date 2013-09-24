package cn.edu.uestc.acmicpc.db.condition.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * Status search condition.
 */
@Repository
public class StatusCondition extends BaseCondition {
//
//  @Autowired
//  public StatusCondition(UserCondition userCondition) {
//    this.userCondition = userCondition;
//  }
//
//  /**
//   * Start status id.
//   */
//  private Integer startId;
//  /**
//   * End status id.
//   */
//  private Integer endId;
//
//  private Timestamp startTime;
//  private Timestamp endTime;
//
//  public String getUserName() {
//    return userName;
//  }
//
//  public void setUserName(String userName) {
//    this.userName = userName;
//  }
//
//  /**
//   * User's id.
//   */
//
//  private Integer userId;
//
//  /**
//   * User's name
//   */
//  private String userName;
//
//  /**
//   * Problem's id.
//   */
//  private Integer problemId;
//
//  /**
//   * Language's id.
//   */
//  private Integer languageId;
//
//  /**
//   * Contest's id.
//   */
//  private Integer contestId;
//
//  /**
//   * Judging result list(<strong>PRIMARY</strong>).
//   */
//  private List<Global.OnlineJudgeReturnType> result;
//
//  @Exp(mapField = "time", type = ConditionType.ge)
//  public Timestamp getStartTime() {
//    return startTime;
//  }
//
//  public void setStartTime(Timestamp startTime) {
//    this.startTime = startTime;
//  }
//
//  @Exp(mapField = "time", type = ConditionType.le)
//  public Timestamp getEndTime() {
//    return endTime;
//  }
//
//  public void setEndTime(Timestamp endTime) {
//    this.endTime = endTime;
//  }
//
//  @Exp(mapField = "statusId", type = ConditionType.ge)
//  public Integer getStartId() {
//    return startId;
//  }
//
//  public void setStartId(Integer startId) {
//    this.startId = startId;
//  }
//
//  @Exp(mapField = "statusId", type = ConditionType.le)
//  public Integer getEndId() {
//    return endId;
//  }
//
//  public void setEndId(Integer endId) {
//    this.endId = endId;
//  }
//
//  @Exp(mapField = "userByUserId", type = ConditionType.eq, MapObject = User.class)
//  public Integer getUserId() {
//    return userId;
//  }
//
//  public void setUserId(Integer userId) {
//    this.userId = userId;
//  }
//
//  @Exp(mapField = "problemByProblemId", type = ConditionType.eq, MapObject = Problem.class)
//  public Integer getProblemId() {
//    return problemId;
//  }
//
//  public void setProblemId(Integer problemId) {
//    this.problemId = problemId;
//  }
//
//  @Exp(mapField = "languageByLanguageId", type = ConditionType.eq, MapObject = Language.class)
//  public Integer getLanguageId() {
//    return languageId;
//  }
//
//  public void setLanguageId(Integer languageId) {
//    this.languageId = languageId;
//  }
//
//  public Integer getContestId() {
//    return contestId;
//  }
//
//  public void setContestId(Integer contestId) {
//    this.contestId = contestId;
//  }
//
//  public List<Global.OnlineJudgeReturnType> getResult() {
//    if (result == null) {
//      result = new LinkedList<>();
//    }
//    return result;
//  }
//
//  public void setResult(List<Global.OnlineJudgeReturnType> result) {
//    this.result = result;
//  }
//
//  /**
//   * Judging result int format.
//   */
//  private Integer resultId;
//
//  public Integer getResultId() {
//    return resultId;
//  }
//
//  public void setResultId(Integer resultId) {
//    this.resultId = resultId;
//  }
//
//  UserCondition userCondition;
//
//  /**
//   * <strong>WARN</strong>: if we set {@code contestId} with {@code -1}, that means we will query
//   * all records with contestId is {@code null}.
//   *
//   * @param condition conditions that to be considered
//   */
//  @SuppressWarnings({ "unchecked", "rawtypes" })
//  @Override
//  public void invoke(Condition condition) {
//    super.invoke(condition);
//
//    if (contestId != null) {
//      if (contestId == -1) {
//        condition.addCriterion(Restrictions.isNull("contestByContestId"));
//      } else {
//        try {
//          IDAO DAO = (IDAO) applicationContext.getBean("contestDAO");
//          JoinedProperty joinedProperty =
//              new JoinedProperty(Restrictions.eq("contestByContestId", DAO.get(contestId)),
//                  contestId, ConditionType.eq);
//          condition.addJoinedProperty("contestByContestId", joinedProperty);
//        } catch (AppException ignored) {
//        }
//      }
//    }
//
//    if (userName != null) {
//      UserDAO userDAO = applicationContext.getBean("userDAO", UserDAO.class);
//      userCondition.clear();
//      userCondition.setUserName(userName);
//      try {
//        List<User> users = (List<User>) userDAO.findAll(userCondition.getCondition());
//        if (users != null && !users.isEmpty()) {
//          JoinedProperty joinedProperty =
//              new JoinedProperty(Restrictions.eq("userByUserId", users.get(0)), users.get(0)
//                  .getUserId(), ConditionType.eq);
//          condition.addJoinedProperty("userByUserId", joinedProperty);
//        }
//      } catch (AppException ignored) {
//      }
//    }
//
//    if (result != null && !result.isEmpty() || resultId != null) {
//      if (result != null && !result.isEmpty()) {
//        Junction junction = Restrictions.disjunction();
//        for (Global.OnlineJudgeReturnType onlineJudgeReturnType : result)
//          junction.add(Restrictions.eq("result", onlineJudgeReturnType.ordinal()));
//        condition.addCriterion(junction);
//      } else {
//        condition.addCriterion(Restrictions.eq("result", resultId));
//      }
//    }
//  }
}
