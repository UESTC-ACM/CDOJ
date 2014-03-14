package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Problem database condition entity.
 */
public class ProblemCondition extends BaseCondition {

  public ProblemCondition() {
    super("problemId");
  }

  /**
   * Start problem id.
   */
  @Exp(mapField = "problemId", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * End problem id.
   */
  @Exp(mapField = "problemId", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * Problem title (partly matches)
   */
  @Exp(type = Condition.ConditionType.LIKE)
  public String title;

  /**
   * Problem source (partly matches)
   */
  @Exp(type = Condition.ConditionType.LIKE)
  public String source;

  /**
   * Search keyword
   */
  public String keyword;

  /**
   * SPJ state
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Boolean isSpj;

  /**
   * Is problem visible?
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Boolean isVisible;

  /**
   * Minimal problem difficulty
   */
  @Exp(mapField = "difficulty", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer startDifficulty;

  /**
   * Maximal problem difficulty
   */
  @Exp(mapField = "difficulty", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endDifficulty;

  /**
   * Is problem has an empty title?
   */
  public Boolean isTitleEmpty;

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
      Condition keywordCondition = new Condition(Condition.JoinedType.OR);
      keywordCondition.addEntry("title", Condition.ConditionType.LIKE, keyword);
      keywordCondition.addEntry("description", Condition.ConditionType.LIKE, keyword);
      keywordCondition.addEntry("source", Condition.ConditionType.LIKE, keyword);
      condition.addEntry(keywordCondition);
    }
    return condition;
  }
}
