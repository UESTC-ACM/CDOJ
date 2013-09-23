package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IMessageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.MessageDTO;
import cn.edu.uestc.acmicpc.db.entity.Message;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for message entity.
 */
@Repository
public class MessageDAO extends DAO<Message, Integer, MessageDTO> implements IMessageDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Message> getReferenceClass() {
    return Message.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public MessageDTO persist(MessageDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
