package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.util.settings.Global.AuthenticationType;

/**
 * User database condition entity.
 */
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
   * User's type.
   * @see AuthenticationType
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

  /**
   * Hide some user with specific type from user list.
   */
  @Exp(mapField = "type", type = ConditionType.NOT_EQUALS)
  public Integer typeExclude;
}
