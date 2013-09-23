package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * UserDAO AOP interface.
 */
public interface IUserDAO extends IDAO<User, Integer, UserDTO> {

  /**
   * Get user by it's unique name.
   *
   * @param name user's name
   * @return user entity, null if not exists
   * @deprecated this method is instead by {@code getEntityByUniqueField("userName", value)}
   */
  @Deprecated
  public User getUserByName(String name);

  @Override
  public UserDTO persist(UserDTO userDTO) throws AppException;
}
