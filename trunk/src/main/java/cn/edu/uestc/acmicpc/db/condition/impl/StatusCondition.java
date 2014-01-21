package cn.edu.uestc.acmicpc.db.condition.impl;

import java.sql.Timestamp;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.JoinedType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.util.settings.Global.OnlineJudgeResultType;

/**
 * Status database condition entity.
 */
public class StatusCondition extends BaseCondition {

  public StatusCondition() {
    super("statusId");
  }

  /**
   * Minimal status id.
   */
  @Exp(mapField = "statusId", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Integer startId;

  /**
   * Maximal status id.
   */
  @Exp(mapField = "statusId", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Integer endId;

  /**
   * Minimal submit time.
   */
  @Exp(mapField = "time", type = Condition.ConditionType.GREATER_OR_EQUALS)
  public Timestamp startTime;

  /**
   * Maximal submit time.
   */
  @Exp(mapField = "time", type = Condition.ConditionType.LESS_OR_EQUALS)
  public Timestamp endTime;

  /**
   * Submit user name.
   */
  public String userName;

  /**
   * Submit user id.
   */
  @Exp(mapField = "userByUserId", type = Condition.ConditionType.EQUALS)
  public Integer userId;

  /**
   * Problem id.
   */
  @Exp(mapField = "problemByProblemId", type = Condition.ConditionType.EQUALS)
  public Integer problemId;

  /**
   * Language.
   */
  @Exp(mapField = "languageByLanguageId", type = Condition.ConditionType.EQUALS)
  public Integer languageId;

  /**
   * Contest id.
   */
  public Integer contestId;

  /**
   * Result.
   * @see OnlineJudgeResultType
   */
  public OnlineJudgeResultType result;

  @Exp(mapField = "problemByProblemId.isVisible", type = ConditionType.EQUALS)
  public Boolean isVisible;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    if (contestId != null) {
      if (contestId == -1) {
        condition.addEntry("contestByContestId", Condition.ConditionType.IS_NULL,
            null);
      } else {
        condition.addEntry("contestByContestId", Condition.ConditionType.EQUALS,
            contestId);
      }
    }

    if (userName != null && !userName.equals("")) {
      condition.addEntry("userByUserId.userName", Condition.ConditionType.STRING_EQUALS,
          userName);
    }

    if (result != null && result != Global.OnlineJudgeResultType.OJ_ALL) {
        Condition typeCondition = new Condition(JoinedType.OR);
        switch (result) {
        case OJ_WAIT:
          typeCondition.addEntry("result", ConditionType.EQUALS, 0);
          typeCondition.addEntry("result", ConditionType.EQUALS, 18);
          break;
        case OJ_AC:
          typeCondition.addEntry("result", ConditionType.EQUALS, 1);
          break;
        case OJ_PE:
          typeCondition.addEntry("result", ConditionType.EQUALS, 2);
          break;
        case OJ_TLE:
          typeCondition.addEntry("result", ConditionType.EQUALS, 3);
          break;
        case OJ_MLE:
          typeCondition.addEntry("result", ConditionType.EQUALS, 4);
          break;
        case OJ_WA:
          typeCondition.addEntry("result", ConditionType.EQUALS, 5);
          break;
        case OJ_OLE:
          typeCondition.addEntry("result", ConditionType.EQUALS, 6);
          break;
        case OJ_CE:
          typeCondition.addEntry("result", ConditionType.EQUALS, 7);
          break;
        case OJ_RE:
          typeCondition.addEntry("result", ConditionType.EQUALS, 8);
          typeCondition.addEntry("result", ConditionType.EQUALS, 9);
          typeCondition.addEntry("result", ConditionType.EQUALS, 10);
          typeCondition.addEntry("result", ConditionType.EQUALS, 11);
          typeCondition.addEntry("result", ConditionType.EQUALS, 12);
          typeCondition.addEntry("result", ConditionType.EQUALS, 15);
          break;
        case OJ_RF:
          typeCondition.addEntry("result", ConditionType.EQUALS, 13);
          break;
        case OJ_SE:
          typeCondition.addEntry("result", ConditionType.EQUALS, 14);
          break;
        default:
          break;
        }
        condition.addEntry(typeCondition);
    }
    return condition;
  }

}
