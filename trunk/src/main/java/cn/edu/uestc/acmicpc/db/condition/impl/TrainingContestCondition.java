package cn.edu.uestc.acmicpc.db.condition.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * // TODO(mzry1992) Description
 */
@Repository
public class TrainingContestCondition extends BaseCondition {
//
//  private Integer startId;
//  private Integer endId;
//  private String title;
//  private Boolean isPersonal;
//  private Integer type;
//
//  @Exp(mapField = "type", type = ConditionType.eq)
//  public Integer getType() {
//    return type;
//  }
//
//  public void setType(Integer type) {
//    this.type = type;
//  }
//
//  private Boolean isTitleEmpty;
//
//  public Boolean getIsTitleEmpty() {
//    return isTitleEmpty;
//  }
//
//  public void setIsTitleEmpty(Boolean isTitleEmpty) {
//    this.isTitleEmpty = isTitleEmpty;
//  }
//
//  @Exp(mapField = "trainingContestId", type = ConditionType.ge)
//  public Integer getStartId() {
//    return startId;
//  }
//
//  public void setStartId(Integer startId) {
//    this.startId = startId;
//  }
//
//  @Exp(mapField = "trainingContestId", type = ConditionType.le)
//  public Integer getEndId() {
//    return endId;
//  }
//
//  public void setEndId(Integer endId) {
//    this.endId = endId;
//  }
//
//  @Exp(type = ConditionType.like)
//  public String getTitle() {
//    return title;
//  }
//
//  public void setTitle(String title) {
//    this.title = title;
//  }
//
//  @Exp(type = ConditionType.eq)
//  public Boolean getIsPersonal() {
//    return isPersonal;
//  }
//
//  public void setIsPersonal(Boolean personal) {
//    isPersonal = personal;
//  }
//
//  @Override
//  public void invoke(Condition condition) {
//    super.invoke(condition);
//    if (isTitleEmpty != null) {
//      if (isTitleEmpty) {
//        condition.addCriterion(Restrictions.like("title", ""));
//      } else {
//        condition.addCriterion(Restrictions.like("title", "_%"));
//      }
//    }
//  }
}
