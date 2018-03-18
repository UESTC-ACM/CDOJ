package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.MessageDto;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Message database criteria entity.
 */
public class MessageCriteria extends BaseCriteria<Message, MessageDto> {
  private static final Logger logger = Logger.getLogger(MessageCriteria.class);

  public MessageCriteria() {
    super(Message.class, MessageDto.class);
  }

  public Integer senderId;
  public Integer receiverId;
  public Boolean isOpened;
  public Integer MessageId;

  // Messages send/receive by this user
  public Integer userId;

  // Messages send/receive between userA and userB
  public Integer userAId;
  public Integer userBId;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (userId != null) {
      criteria = criteria.add(Restrictions.eq("receiverId",this.userId));
    }else if (userAId != null && userBId != null) {
      criteria.add(Restrictions.or(
          Restrictions.and(
              Restrictions.eq("senderId", userAId),
              Restrictions.eq("receiverId", userBId)),
          Restrictions.and(
              Restrictions.eq("senderId", userBId),
              Restrictions.eq("receiverId", userAId))));
    }
    if( this.MessageId != null )
      criteria = criteria.add(Restrictions.eq("messageId",this.MessageId));
    if( isOpened != null )
      criteria = criteria.add(Restrictions.eq("isOpened",isOpened));
    return criteria;
  }
}
