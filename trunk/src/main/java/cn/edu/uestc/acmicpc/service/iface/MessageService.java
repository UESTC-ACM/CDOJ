package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.MessageCriteria;
import cn.edu.uestc.acmicpc.db.dto.impl.MessageDto;
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
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.MessageDto} entity.
   *
   * @param messageDto {@link cn.edu.uestc.acmicpc.db.dto.impl.MessageDto}
   *                   entity.
   * @return new messages record's id.
   * @throws AppException
   */
  Integer createNewMessage(MessageDto messageDto) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.MessageDto} entity by
   * message's id
   *
   * @param messageId message's id
   * @return {@link MessageDto} entity
   * @throws AppException
   */
  MessageDto getMessageDto(Integer messageId) throws AppException;

  /**
   * Counts the number of messages fit in condition.
   *
   * @param condition {@link MessageCriteria} entity.
   * @return total number of users fit in the condition.
   * @throws AppException
   */
  @Deprecated
  Long count(MessageCriteria condition) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.MessageDto}
   * list
   *
   * @param criteria condition
   * @param pageInfo page info
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.MessageDto}
   * list
   * @throws AppException
   */
  List<MessageDto> getMessageForReceiverDtoList(
      MessageCriteria criteria, PageInfo pageInfo) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.MessageDto} list
   *
   * @param criteria condition
   * @param pageInfo page info
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.MessageDto}
   * list
   * @throws AppException
   */
  List<MessageDto> getMessageForUserDtoList(
      MessageCriteria criteria, PageInfo pageInfo) throws AppException;

  /**
   * Mark one message as opened.
   *
   * @param messageId message's id
   * @throws AppException
   */
  void read(Integer messageId) throws AppException;
}
