package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;

/**
 * Description
 */
@Deprecated
public class ContestTeamCondition extends BaseCondition {

  public ContestTeamCondition() {
    super("contestTeamId");
  }

  @Exp(mapField = "contestId", type = Condition.ConditionType.EQUALS)
  public Integer contestId;

  @Exp(mapField = "teamId", type = Condition.ConditionType.EQUALS)
  public Integer teamId;

  @Exp(mapField = "status", type = Condition.ConditionType.EQUALS)
  public Integer status;

}
