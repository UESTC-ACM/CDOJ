package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Article database condition entity.
 */
public class ArticleCondition extends BaseCondition {

  public ArticleCondition() {
    super("articleId");
  }

  /**
   * Minimal article id.
   */
  @Exp(mapField = "articleId", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * Maximal article id.
   */
  @Exp(mapField = "articleId", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * Article title (partly matches).
   */
  @Exp(type = Condition.ConditionType.LIKE)
  public String title;

  /**
   * Search keyword.
   */
  public String keyword;

  /**
   * Visible state.
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Boolean isVisible;

  /**
   * Author's user id.
   */
  @Exp(mapField = "userByUserId", type = Condition.ConditionType.EQUALS)
  public Integer userId;

  /**
   * Author's name.
   */
  public String userName;

  /**
   * Problem id.
   */
  public Integer problemId;

  /**
   * Contest id.
   */
  public Integer contestId;

  /**
   * Parent id.
   */
  public Integer parentId;

  /**
   * Type
   */
  @Exp(mapField = "type", type = Condition.ConditionType.EQUALS)
  public Integer type;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    if (contestId != null) {
      if (contestId == -1) {
        condition.addEntry("contestId", Condition.ConditionType.IS_NULL, null);
      } else {
        condition.addEntry("contestId", Condition.ConditionType.EQUALS, contestId);
      }
    }
    if (problemId != null) {
      if (problemId == -1) {
        condition.addEntry("problemId", Condition.ConditionType.IS_NULL, null);
      } else {
        condition.addEntry("problemId", Condition.ConditionType.EQUALS, problemId);
      }
    }
    if (parentId != null) {
      if (parentId == -1) {
        condition.addEntry("parentId", Condition.ConditionType.IS_NULL, null);
      } else {
        condition.addEntry("parentId", Condition.ConditionType.EQUALS, parentId);
      }
    }
    if (userName != null) {
      condition.addEntry("userByUserId.userName", Condition.ConditionType.STRING_EQUALS, userName);
    }
    if (keyword != null) {
      Condition keywordCondition = new Condition(Condition.JoinedType.OR);
      keywordCondition.addEntry("title", Condition.ConditionType.LIKE, keyword);
      keywordCondition.addEntry("content", Condition.ConditionType.LIKE, keyword);
      keywordCondition.addEntry("author", Condition.ConditionType.LIKE, keyword);
      condition.addEntry(keywordCondition);
    }
    return condition;
  }

}
