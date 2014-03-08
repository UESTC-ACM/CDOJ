package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Team database condition entity.
 */
public class TeamCondition extends BaseCondition {

  public TeamCondition() {
    super("teamId");
  }

  @Exp(mapField = "teamName", type = Condition.ConditionType.LIKE)
  public String teamName;

  public Integer userId;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    return condition;
  }
}
