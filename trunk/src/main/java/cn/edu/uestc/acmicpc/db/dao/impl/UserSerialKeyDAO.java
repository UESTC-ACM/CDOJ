package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserSerialKeyDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO implementation for {@link UserSerialKey}.
 */
@Repository
public class UserSerialKeyDAO extends DAO<UserSerialKey, Integer, UserSerialKeyDTO>
    implements IUserSerialKeyDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<UserSerialKey> getReferenceClass() {
    return UserSerialKey.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public UserSerialKeyDTO persist(UserSerialKeyDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
