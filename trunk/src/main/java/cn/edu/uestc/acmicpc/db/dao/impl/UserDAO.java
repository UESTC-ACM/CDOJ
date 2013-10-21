package cn.edu.uestc.acmicpc.db.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;

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

  @Override
  @Deprecated
  public User getUserByName(String name) {
    if (name == null)
      return null;
    Session session = getSession();
    Criteria criteria = session.createCriteria(User.class);
    criteria.add(Restrictions.eq("userName", name));
    List<?> list = criteria.list();
    if (list == null || list.isEmpty())
      return null;
    return (User) list.get(0);
  }
}
