package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IMessageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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
  public void sendTeamInvitation(UserDTO sender, UserDTO receiver, TeamDTO teamDTO)
      throws AppException {
    String userCenterUrl = settings.SETTING_HOST
        + "/#/user/center/" + receiver.getUserName() + "/teams";
    StringBuilder messageContent = new StringBuilder();
    messageContent.append(StringUtil.getAtLink(sender.getUserName(), sender.getUserId()))
        .append(" has invited you to join team ")
        .append(teamDTO.getTeamName())
        .append(".\n\n")
        .append("See [your teams](")
        .append(userCenterUrl)
        .append(") for more details.");
    Integer messageId = createNewMessage(MessageDTO.builder()
        .setSenderId(sender.getUserId())
        .setReceiverId(receiver.getUserId())
        .setTime(new Timestamp(System.currentTimeMillis()))
        .setIsOpened(false)
        .setTitle("Team invitation.")
        .setContent(messageContent.toString())
        .build());
    MessageDTO messageDTO = getMessageDTO(messageId);
    AppExceptionUtil.assertNotNull(messageDTO, "Error while sending notification.");
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
