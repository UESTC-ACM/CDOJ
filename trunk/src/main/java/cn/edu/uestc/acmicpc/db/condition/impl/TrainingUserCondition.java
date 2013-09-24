package cn.edu.uestc.acmicpc.db.condition.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * // TODO(mzry1992) Description
 */
@Repository
public class TrainingUserCondition extends BaseCondition {
//
//  private Integer startId;
//  private Integer endId;
//  private String name;
//  private String userName;
//  private Integer type;
//  private Boolean allow;
//
//  @Exp(mapField = "allow", type = ConditionType.eq)
//  public Boolean getAllow() {
//    return allow;
//  }
//
//  public void setAllow(Boolean allow) {
//    this.allow = allow;
//  }
//
//  @Exp(mapField = "trainingUserId", type = ConditionType.ge)
//  public Integer getStartId() {
//    return startId;
//  }
//
//  public void setStartId(Integer startId) {
//    this.startId = startId;
//  }
//
//  @Exp(mapField = "trainingUserId", type = ConditionType.le)
//  public Integer getEndId() {
//    return endId;
//  }
//
//  public void setEndId(Integer endId) {
//    this.endId = endId;
//  }
//
//  @Exp(type = ConditionType.like)
//  public String getName() {
//    return name;
//  }
//
//  public void setName(String name) {
//    this.name = name;
//  }
//
//  public String getUserName() {
//    return userName;
//  }
//
//  public void setUserName(String userName) {
//    this.userName = userName;
//  }
//
//  @Exp(type = ConditionType.eq)
//  public Integer getType() {
//    return type;
//  }
//
//  public void setType(Integer type) {
//    this.type = type;
//  }
//
//  @SuppressWarnings("unchecked")
//  @Override
//  public void invoke(Condition condition) {
//    super.invoke(condition);
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
//  }
//
//  private final UserCondition userCondition;
}
