package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;

/**
 * Article databse condition entity.
 */
public class ArticleCondition extends BaseCondition {

  /**
   * Minimal article id
   */
  @Exp(mapField = "articleId", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * Maximal article id
   */
  @Exp(mapField = "articleId", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * Article title (partly matches)
   */
  @Exp(type = Condition.ConditionType.LIKE)
  public String title;

  /**
   * Article keyword
   */
  public String keyword;

  /**
   * Is article visible?
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Boolean isVisible;

  /**
   * Is this a system notice?
   */
  @Exp(type = Condition.ConditionType.EQUALS)
  public Boolean isNotice;

  /**
   * Is the title empty?
   */
  public Boolean isTitleEmpty;

  /**
   * Author's user id
   */
  @Exp(mapField = "userByUserId", type = Condition.ConditionType.EQUALS)
  public Integer userId;

  /**
   * Author's name
   */
  public String userName;

  /**
   * Problem id
   */
  public Integer problemId;

  /**
   * Contest id
   */
  public Integer contestId;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    if (contestId != null) {
      if (contestId == -1) {
        condition.addEntry("contestByContestId", Condition.ConditionType.IS_NULL, null);
      } else {
        condition.addEntry("contestByContestId", Condition.ConditionType.EQUALS, contestId);
      }
    }
    if (problemId != null) {
      if (problemId == -1) {
        condition.addEntry("problemByProblemId", Condition.ConditionType.IS_NULL, null);
      } else {
        condition.addEntry("problemByProblemId", Condition.ConditionType.EQUALS, problemId);
      }
    }
    if (userName != null) {
      condition.addEntry("userByUserId.userName", Condition.ConditionType.STRING_EQUALS, userName);
    }
    if (isTitleEmpty != null) {
      if (isTitleEmpty) {
        condition.addEntry("title", Condition.ConditionType.STRING_EQUALS, "");
      } else {
        condition.addEntry("title", Condition.ConditionType.STRING_EQUALS, "_%");
      }
    }
    if (keyword != null) {
      condition.addEntry("title", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
      condition.addEntry("content", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
      condition.addEntry("author", Condition.ConditionType.STRING_EQUALS, String.format("%%%s%%", keyword));
    }
    return condition;
  }

}
