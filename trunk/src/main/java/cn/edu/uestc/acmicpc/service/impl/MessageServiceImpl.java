package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IMessageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description
 */
@Service
public class MessageServiceImpl extends AbstractService implements MessageService {

  private IMessageDAO messageDAO;
  private Settings settings;

  @Autowired
  public MessageServiceImpl(IMessageDAO messageDAO, Settings settings) {
    this.messageDAO = messageDAO;
    this.settings = settings;
  }

  @Override
  public IMessageDAO getDAO() {
    return messageDAO;
  }

  @Override
  public Integer createNewMessage(MessageDTO messageDTO) throws AppException {
    AppExceptionUtil.assertNotNull(messageDTO.getSenderId());
    AppExceptionUtil.assertNotNull(messageDTO.getReceiverId());
    Message message = new Message();
    message.setTitle(messageDTO.getTitle());
    message.setContent(messageDTO.getContent());
    message.setTime(messageDTO.getTime());
    message.setIsOpened(messageDTO.getIsOpened());
    message.setSenderId(messageDTO.getSenderId());
    message.setReceiverId(messageDTO.getReceiverId());
    message.setMessageId(null);
    messageDAO.add(message);
    return message.getMessageId();
  }

  @Override
  public MessageDTO getMessageDTO(Integer messageId) throws AppException {
    AppExceptionUtil.assertNotNull(messageId);
    return messageDAO.getDTOByUniqueField(MessageDTO.class, MessageDTO.builder(), "messageId",
        messageId);
  }

  @Override
  public Long count(MessageCondition condition) throws AppException {
    return messageDAO.count(condition.getCondition());
  }

  @Override
  public List<MessageForReceiverDTO> getMessageForReceiverDTOList(MessageCondition messageCondition
      , PageInfo pageInfo) throws AppException {
    Condition condition = messageCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return messageDAO.findAll(MessageForReceiverDTO.class, MessageForReceiverDTO.builder(), condition);
  }

  @Override
  public List<MessageForUserDTO> getMessageForUserDTOList(MessageCondition messageCondition, PageInfo pageInfo) throws AppException {
    Condition condition = messageCondition.getCondition();
    condition.setPageInfo(pageInfo);
    return messageDAO.findAll(MessageForUserDTO.class, MessageForUserDTO.builder(), condition);
  }

  @Override
  public void read(Integer messageId) throws AppException {
    messageDAO.updateEntitiesByField("isOpened", true, "messageId", messageId.toString());
  }
}
