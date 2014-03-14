package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;

import org.springframework.stereotype.Repository;

/**
 * DAO for user entity.
 */
@Repository
public class UserDAO extends DAO<User, Integer> implements IUserDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<User> getReferenceClass() {
    return User.class;
  }
}
