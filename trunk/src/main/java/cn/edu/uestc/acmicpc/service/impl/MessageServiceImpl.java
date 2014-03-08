package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IMessageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.service.iface.MessageService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

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
    String url = settings.SETTING_HOST
        + "/#/user/team/" + receiver.getUserName();
    StringBuilder messageContent = new StringBuilder();
    messageContent.append(sender.getUserName())
        .append(" has invited you to join team ")
        .append(teamDTO.getTeamName())
        .append(".\n")
        .append("See ")
        .append(url)
        .append(" for more details.");
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
}
