package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Global.ContestType;

import java.sql.Timestamp;

/**
 * Contest database condition entity.
 */
public class ContestCondition extends BaseCondition {

  public ContestCondition() {
    super("contestId");
  }

  /**
   * Minimal contest id.
   */
  @Exp(mapField = "contestId", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * Maximal contest id.
   */
  @Exp(mapField = "contestId", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * Contest tile (partly matches).
   */
  @Exp(type = Condition.ConditionType.LIKE)
  public String title;

  /**
   * Contest description
   */
  //(TODO(mzry1992) remove it and use keyword).
  @Exp(type = Condition.ConditionType.LIKE)
  public String description;

  /**
   * Contest type.
   *
   * @see ContestType
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Byte type;

  /**
   * Minimal contest start time.
   */
  @Exp(mapField = "time", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Timestamp startTime;

  /**
   * Maximal contest end time.
   */
  @Exp(mapField = "time", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Timestamp endTime;

  /**
   * Visible state.
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Boolean isVisible;

  /**
   * Search keyword.
   */
  public String keyword;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    if (keyword != null) {
      Condition keywordCondition = new Condition(Condition.JoinedType.OR);
      keywordCondition.addEntry("title", Condition.ConditionType.LIKE, keyword);
      keywordCondition.addEntry("description", Condition.ConditionType.LIKE, keyword);
      condition.addEntry(keywordCondition);
    }
    return condition;
  }

}
