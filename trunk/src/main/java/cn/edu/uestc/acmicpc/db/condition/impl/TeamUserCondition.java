package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;

/**
 * Description
 */
public class TeamUserCondition extends BaseCondition {

  public TeamUserCondition() {
    super("teamId");
  }

  @Exp(mapField = "teamId", type = Condition.ConditionType.IN)
  public String teamIdList;
}
