package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;

/**
 * Description
 */
public class MessageCondition extends BaseCondition {

  public MessageCondition() {
    super("messageId");
  }

  @Exp(mapField = "senderId", type = Condition.ConditionType.EQUALS)
  public Integer senderId;

  @Exp(mapField = "receiverId", type = Condition.ConditionType.EQUALS)
  public Integer receiverId;

  @Exp(mapField = "isOpened", type = Condition.ConditionType.EQUALS)
  public Boolean isOpened;
}
