package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * UserSerialKeyDAO AOP interface.
 */
public interface IUserSerialKeyDAO extends IDAO<UserSerialKey, Integer, UserSerialKeyDTO> {

  @Override
  public UserSerialKeyDTO persist(UserSerialKeyDTO dto) throws AppException;
}
