package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.MessageCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.MessageDao;
import cn.edu.uestc.acmicpc.db.dto.field.MessageFields;
import cn.edu.uestc.acmicpc.db.dto.impl.MessageDto;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Description
 */
@SuppressWarnings("deprecation")
@Service
@Transactional(rollbackFor = Exception.class)
public class MessageServiceImpl extends AbstractService implements MessageService {

  private final MessageDao messageDao;

  @Autowired
  public MessageServiceImpl(MessageDao messageDao) {
    this.messageDao = messageDao;
  }

  @Override
  public Integer createNewMessage(MessageDto messageDto) throws AppException {
    AppExceptionUtil.assertNotNull(messageDto.getSenderId());
    AppExceptionUtil.assertNotNull(messageDto.getReceiverId());
    Message message = new Message();
    message.setTitle(messageDto.getTitle());
    message.setContent(messageDto.getContent());
    message.setTime(messageDto.getTime());
    message.setIsOpened(messageDto.getIsOpened());
    message.setSenderId(messageDto.getSenderId());
    message.setReceiverId(messageDto.getReceiverId());
    message.setMessageId(null);
    messageDao.addOrUpdate(message);
    return message.getMessageId();
  }

  @Override
  public MessageDto getMessageDto(Integer messageId) throws AppException {
    MessageCriteria criteria = new MessageCriteria();
    criteria.MessageId = messageId;
    List<MessageDto> result = messageDao.findAll( criteria , null , MessageFields.BASIC_FIELDS  );
    return result.isEmpty() ? null : result.iterator().next();
  }

  @Override
  public Long count(MessageCriteria criteria) throws AppException {
    return messageDao.count(criteria);
  }

  @Override
  public List<MessageDto> getMessageForReceiverDtoList(
      MessageCriteria criteria, PageInfo pageInfo) throws AppException {
    return messageDao.findAll(criteria, pageInfo, MessageFields.FOR_RECEIVER_FIELDS);
  }

  @Override
  public List<MessageDto> getMessageForUserDtoList(
      MessageCriteria criteria, PageInfo pageInfo) throws AppException {
    return messageDao.findAll(criteria, pageInfo, MessageFields.FOR_USER_FIELDS);
  }

  @Override
  public void read(Integer messageId) throws AppException {
    messageDao.updateEntitiesByField("isOpened", true, "messageId", messageId.toString());
  }
}
