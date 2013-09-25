package cn.edu.uestc.acmicpc.db.condition.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * Contest database condition entity.
 */
@Repository
public class ContestCondition extends BaseCondition {
//
//  private Integer startId;
//
//  private Integer endId;
//
//  private String title;
//
//  private String description;
//
//  private Byte type;
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
//  @Exp(mapField = "contestId", type = ConditionType.ge)
//  public Integer getStartId() {
//    return startId;
//  }
//
//  public void setStartId(Integer startId) {
//    this.startId = startId;
//  }
//
//  @Exp(mapField = "contestId", type = ConditionType.le)
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
//  @Exp(type = ConditionType.like)
//  public String getDescription() {
//    return description;
//  }
//
//  public void setDescription(String description) {
//    this.description = description;
//  }
//
//  @Exp(type = ConditionType.eq)
//  public Byte getType() {
//    return type;
//  }
//
//  public void setType(Byte type) {
//    this.type = type;
//  }
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
//  public Timestamp getEndTime() {
//    return endTime;
//  }
//
//  public void setEndTime(Timestamp endTime) {
//    this.endTime = endTime;
//  }
//
//  @Exp(type = ConditionType.eq)
//  public Boolean getIsVisible() {
//    return isVisible;
//  }
//
//  public void setIsVisible(Boolean visible) {
//    isVisible = visible;
//  }
//
//  private Timestamp startTime;
//
//  private Timestamp endTime;
//
//  private Boolean isVisible;
//
//  /**
//   * Keyword for {@code description}, {@code title}
//   */
//  private String keyword;
//
//  public String getKeyword() {
//    return keyword;
//  }
//
//  public void setKeyword(String keyword) {
//    this.keyword = keyword;
//  }
//
//  @Override
//  public void invoke(Condition condition) {
//    super.invoke(condition);
//    if (keyword != null) {
//      String[] fields = new String[] { "description", "title" };
//      Junction junction = Restrictions.disjunction();
//      for (String field : fields) {
//        junction.add(Restrictions.like(field, String.format("%%%s%%", keyword)));
//      }
//      condition.addCriterion(junction);
//    }
//    if (endTime != null) {
//      condition.addCriterion(Restrictions.lt("time", DateUtil.add(endTime, Calendar.DATE, 1)));
//    }
//    if (isTitleEmpty != null) {
//      if (isTitleEmpty) {
//        condition.addCriterion(Restrictions.like("title", ""));
//      } else {
//        condition.addCriterion(Restrictions.like("title", "_%"));
//      }
//    }
//  }
}
