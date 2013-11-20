package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

public class ContestProblemCondition extends BaseCondition{

  public ContestProblemCondition() {
    super("contestProblemId");
  }

  /**
   * contestId
   */
  public Integer contestId;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    AppExceptionUtil.assertNotNull(contestId);
    if(contestId != null) {
      condition.addEntry("contestID", Condition.ConditionType.EQUALS, contestId);
    }
    return condition;
  }
}