package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

/**
 * Contest problem database condition entity.
 */
@Deprecated
public class ContestProblemCondition extends BaseCondition {

  public ContestProblemCondition() {
    super("contestProblemId");
  }

  /**
   * contest id.
   */
  public Integer contestId;

  /**
   * problem id.
   */
  @Exp(mapField = "problemId", type = Condition.ConditionType.EQUALS)
  public Integer problemId;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    AppExceptionUtil.assertNotNull(contestId);
    condition.addEntry("contestID", Condition.ConditionType.EQUALS, contestId);
    return condition;
  }
}