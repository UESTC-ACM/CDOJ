package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.MessageCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDto;
import cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Message service interface
 */
@SuppressWarnings("deprecation")
public interface MessageService {

  /**
   * Create a message by
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto} entity.
   *
   * @param messageDto {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto}
   *                   entity.
   * @return new messages record's id.
   * @throws AppException
   */
  public Integer createNewMessage(MessageDto messageDto) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageDto} entity by
   * message's id
   *
   * @param messageId message's id
   * @return {@link MessageDto} entity
   * @throws AppException
   */
  public MessageDto getMessageDto(Integer messageId) throws AppException;

  /**
   * Counts the number of messages fit in condition.
   *
   * @param condition {@link MessageCondition} entity.
   * @return total number of users fit in the condition.
   * @throws AppException
   */
  @Deprecated
  public Long count(MessageCondition condition) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDto}
   * list
   *
   * @param messageCondition condition
   * @param pageInfo         page info
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForReceiverDto}
   * list
   * @throws AppException
   */
  @Deprecated
  public List<MessageForReceiverDto> getMessageForReceiverDtoList(MessageCondition messageCondition
      , PageInfo pageInfo) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDto} list
   *
   * @param messageCondition condition
   * @param pageInfo         page info
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.message.MessageForUserDto}
   * list
   * @throws AppException
   */
  public List<MessageForUserDto> getMessageForUserDtoList(MessageCondition messageCondition,
      PageInfo pageInfo) throws AppException;

  /**
   * Mark one message as opened.
   *
   * @param messageId message's id
   * @throws AppException
   */
  public void read(Integer messageId) throws AppException;
}
