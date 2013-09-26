package cn.edu.uestc.acmicpc.db.condition.impl;

import java.sql.Timestamp;
import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Status search condition.
 */
public class StatusCondition extends BaseCondition {

  public StatusCondition() {
    super("statusId");
  }

  /**
   * Minimal status id
   */
  @Exp(mapField = "statusId", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer stratId;

  /**
   * Maximal status id
   */
  @Exp(mapField = "statusId", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * Minimal submit time
   */
  @Exp(mapField = "time", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Timestamp startTime;

  /**
   * Maximal submit time
   */
  @Exp(mapField = "time", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Timestamp endTime;

  /**
   * Submit user name
   */
  public String userName;

  /**
   * Submit user id
   */
  @Exp(mapField = "userByUserId", type = Condition.ConditionType.EQUALS)
  public Integer userId;

  /**
   * Problem id
   */
  @Exp(mapField = "problemByProblemId", type = Condition.ConditionType.EQUALS)
  public Integer problemId;

  /**
   * Language
   */
  @Exp(mapField = "languageByLanguageId", type = Condition.ConditionType.EQUALS)
  public Integer languageId;

  /**
   * Contest id
   */
  public Integer contestId;

  /**
   * Result list
   */
  public List<Global.OnlineJudgeReturnType> result;

  /**
   * Result id
   */
  public Integer resultId;

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

    if (userName != null) {
      condition.addEntry("userByUserId.userName", Condition.ConditionType.STRING_EQUALS, userName);
    }

    if ((result != null && !result.isEmpty()) || resultId != null) {
      if (result != null && !result.isEmpty()) {
        //TODO(fish): add in property
      } else {
        condition.addEntry("result", Condition.ConditionType.EQUALS, resultId);
      }
    }
    return condition;
  }

}
