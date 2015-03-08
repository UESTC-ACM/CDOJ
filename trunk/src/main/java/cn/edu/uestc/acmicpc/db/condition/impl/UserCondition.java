package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * User database condition entity.
 */
@Deprecated
public class UserCondition extends BaseCondition {

  public UserCondition() {
    super("userId");
  }

  /**
   * Start user id.
   */
  @Exp(mapField = "userId", type = ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * End user id.
   */
  @Exp(mapField = "userId", type = ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * User name (partly matches).
   */
  @Exp(type = ConditionType.LIKE)
  public String userName;

  /**
   * Nick name (partly matches).
   */
  @Exp(type = ConditionType.LIKE)
  public String nickName;

  /**
   * User's type.
   *
   * @see cn.edu.uestc.acmicpc.util.enums.AuthenticationType
   */
  @Exp(type = ConditionType.EQUALS)
  public Integer type;

  /**
   * User's department's id.
   */
  @Exp(type = ConditionType.EQUALS)
  public Integer departmentId;

  /**
   * User's school(partly matches).
   */
  @Exp(type = ConditionType.LIKE)
  public String school;

  public String keyword;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    if (keyword != null) {
      Condition keywordCondition = new Condition(Condition.JoinedType.OR);
      keywordCondition.addEntry("userName", ConditionType.LIKE, keyword);
      keywordCondition.addEntry("nickName", ConditionType.LIKE, keyword);
      condition.addEntry(keywordCondition);
    }
    return condition;
  }
}
