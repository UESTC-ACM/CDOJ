package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.MessageDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * MessageDAO AOP interface.
 */
public interface IMessageDAO extends IDAO<Message, Integer, MessageDTO> {

  @Override
  public MessageDTO persist(MessageDTO dto) throws AppException;
}
