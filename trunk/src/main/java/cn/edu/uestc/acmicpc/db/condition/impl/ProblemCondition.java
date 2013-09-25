package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * Problem search condition.
 */
public class ProblemCondition extends BaseCondition {

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
   * Keyword for {@code description}, {@code input}, {@code output}, {@code sampleInput},
   * {@code sampleOutput} and {@code hint}.
   */
  public String keyword;

  /**
   * Is problem need spj?
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
      condition.addEntry("title", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
      condition.addEntry("description", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
      condition.addEntry("source", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
    }
    return condition;
  }
}
