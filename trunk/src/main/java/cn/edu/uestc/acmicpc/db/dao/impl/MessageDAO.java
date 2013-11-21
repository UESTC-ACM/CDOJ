package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IMessageDAO;
import cn.edu.uestc.acmicpc.db.entity.Message;

/**
 * DAO for message entity.
 */
@Repository
public class MessageDAO extends DAO<Message, Integer> implements IMessageDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Message> getReferenceClass() {
    return Message.class;
  }
}
