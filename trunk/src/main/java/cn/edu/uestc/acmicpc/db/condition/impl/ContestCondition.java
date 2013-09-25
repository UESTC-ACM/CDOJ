package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

import java.sql.Timestamp;

/**
 * Contest database condition entity.
 */
public class ContestCondition extends BaseCondition {

  /**
   * Minimal contest id
   */
  @Exp(mapField = "contestId", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * Maximal contest id
   */
  @Exp(mapField = "contestId", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * Contest tile (partly matches)
   */
  @Exp(type = Condition.ConditionType.LIKE)
  public String title;

  /**
   * Description
   */
  @Exp(type = Condition.ConditionType.LIKE)
  public String description;

  /**
   * Contest type
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Byte type;

  /**
   * Is contest have an empty title?
   */
  public Boolean isTitleEmpty;

  /**
   * Minimal contest start time
   */
  @Exp(mapField = "time", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Timestamp startTime;

  /**
   * Maximal contest end time
   */
  @Exp(mapField = "time", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Timestamp endTime;

  /**
   * Is contest visible?
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Boolean isVisible;

  /**
   * Contest keyword
   */
  public String keyword;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    if (isTitleEmpty != null) {
      if (isTitleEmpty) {
        condition.addEntry("title", Condition.ConditionType.STRING_EQUALS, "");
      } else {
        condition.addEntry("title", Condition.ConditionType.STRING_EQUALS, "_%");
      }
    }
    if (keyword != null) {
      condition.addEntry("title", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
      condition.addEntry("description", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
    }
    return condition;
  }

}
