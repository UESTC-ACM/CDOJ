package cn.edu.uestc.acmicpc.oj.service.impl;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserDAO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.ioc.dao.UserDAOAware;
import cn.edu.uestc.acmicpc.oj.service.iface.UserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Implementation for {@link UserService}.
 */
@Service
public class UserServiceImpl extends AbstractService implements UserService, UserDAOAware {

  private static final Logger LOGGER = LogManager.getLogger(UserServiceImpl.class);
  @Autowired
  private IUserDAO userDAO;

  @Override
  public void setUserDAO(IUserDAO userDAO) {
    this.userDAO = userDAO;
  }

  @Override
  public User getUserByUserName(String userName) throws AppException {
    try {
      return userDAO.getEntityByUniqueField("userName", userName);
    } catch (FieldNotUniqueException e) {
      LOGGER.error(e);
      throw new AppException(e);
    }
  }

  @Override
  public void updateUser(User user) throws AppException {
    userDAO.update(user);
  }

  @Override
  public User getUserByEmail(String email) throws AppException {
    try {
      return userDAO.getEntityByUniqueField("email", email);
    } catch (FieldNotUniqueException e) {
      LOGGER.error(e);
      throw new AppException(e);
    }
  }

  @Override
  public void createNewUser(User user) throws AppException {
    userDAO.add(user);
  }

  @Override
  public User getUserByUserId(Integer userId) throws AppException {
    return userDAO.get(userId);
  }

  @Override
  public IDAO<User, Integer> getDAO() {
    return userDAO;
  }
}
