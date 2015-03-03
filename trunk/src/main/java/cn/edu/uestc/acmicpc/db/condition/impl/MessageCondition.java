package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Description
 */
@Deprecated
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

  // Messages send/receive by this user
  public Integer userId;

  // Messages send/receive between userA and userB
  public Integer userAId;
  public Integer userBId;

  @Override
  public Condition getCondition() throws AppException {
    Condition condition = super.getCondition();
    if (userId != null) {
      Condition userIdCondition = new Condition(Condition.JoinedType.OR);
      userIdCondition.addEntry("senderId", Condition.ConditionType.EQUALS, userId);
      userIdCondition.addEntry("receiverId", Condition.ConditionType.EQUALS, userId);
      condition.addEntry(userIdCondition);
    } else if (userAId != null && userBId != null) {
      Condition userAIdCondition = new Condition(Condition.JoinedType.AND);
      userAIdCondition.addEntry("senderId", Condition.ConditionType.EQUALS, userAId);
      userAIdCondition.addEntry("receiverId", Condition.ConditionType.EQUALS, userBId);

      Condition userBIdCondition = new Condition(Condition.JoinedType.AND);
      userBIdCondition.addEntry("senderId", Condition.ConditionType.EQUALS, userBId);
      userBIdCondition.addEntry("receiverId", Condition.ConditionType.EQUALS, userAId);

      Condition userIdCondition = new Condition(Condition.JoinedType.OR);
      userIdCondition.addEntry(userAIdCondition);
      userIdCondition.addEntry(userBIdCondition);

      condition.addEntry(userIdCondition);
    }
    return condition;
  }
}
