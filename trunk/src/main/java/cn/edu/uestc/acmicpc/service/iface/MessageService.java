package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.team.TeamDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Message service interface
 */
public interface MessageService extends DatabaseService<Message, Integer> {

  /**
   * Create a message by {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO} entity.
   *
   * @param messageDTO {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO} entity.
   * @return new messages record's id.
   * @throws AppException
   */
  public Integer createNewMessage(MessageDTO messageDTO) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDTO} entity by message's id
   *
   * @param messageId message's id
   * @return {@link MessageDTO} entity
   * @throws AppException
   */
  public MessageDTO getMessageDTO(Integer messageId) throws AppException;

  /**
   * Send a team invitation.
   *
   * @param sender   invitation sender
   * @param receiver invitation receiver
   * @param teamDTO  team information
   * @throws AppException
   */
  public void sendTeamInvitation(UserDTO sender, UserDTO receiver, TeamDTO teamDTO) throws AppException;

  /**
   * Counts the number of messages fit in condition.
   *
   * @param condition {@link MessageCondition} entity.
   * @return total number of users fit in the condition.
   * @throws AppException
   */
  public Long count(MessageCondition condition) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDTO} list
   *
   * @param messageCondition condition
   * @param pageInfo         page info
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDTO} list
   * @throws AppException
   */
  public List<MessageForReceiverDTO> getMessageForReceiverDTOList(MessageCondition messageCondition
      , PageInfo pageInfo) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDTO} list
   *
   * @param messageCondition condition
   * @param pageInfo         page info
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDTO} list
   * @throws AppException
   */
  public List<MessageForUserDTO> getMessageForUserDTOList(MessageCondition messageCondition, PageInfo pageInfo) throws AppException;

  /**
   * Mark one message as opened.
   *
   * @param messageId message's id
   * @throws AppException
   */
  public void read(Integer messageId) throws AppException;
}
