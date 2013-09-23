package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.entity.User;

/**
 * UserDAO AOP interface.
 */
public interface IUserDAO extends IDAO<User, Integer> {

  /**
   * Get user by it's unique name.
   *
   * @param name user's name
   * @return user entity, null if not exists
   * @deprecated this method is instead by {@code getEntityByUniqueField("userName", value)}
   */
  @Deprecated
  public User getUserByName(String name);
}
