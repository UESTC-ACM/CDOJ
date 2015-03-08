package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.MessageDao;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDto;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDto;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    AppExceptionUtil.assertNotNull(messageId);
    return messageDao.getDtoByUniqueField(MessageDto.class, MessageDto.builder(), "messageId",
        messageId);
  }

  @Override
  public Long count(MessageCondition condition) throws AppException {
    return messageDao.count(condition.getCondition());
  }

  @Override
  public List<MessageForReceiverDto> getMessageForReceiverDtoList(MessageCondition messageCondition
      , PageInfo pageInfo) throws AppException {
    Condition condition = messageCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return messageDao.findAll(MessageForReceiverDto.class, MessageForReceiverDto.builder(),
        condition);
  }

  @Override
  public List<MessageForUserDto> getMessageForUserDtoList(MessageCondition messageCondition,
      PageInfo pageInfo) throws AppException {
    Condition condition = messageCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return messageDao.findAll(MessageForUserDto.class, MessageForUserDto.builder(), condition);
  }

  @Override
  public void read(Integer messageId) throws AppException {
    messageDao.updateEntitiesByField("isOpened", true, "messageId", messageId.toString());
  }
}
