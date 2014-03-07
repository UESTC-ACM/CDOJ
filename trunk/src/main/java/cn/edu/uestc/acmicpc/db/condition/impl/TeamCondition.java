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

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    return condition;
  }
}
