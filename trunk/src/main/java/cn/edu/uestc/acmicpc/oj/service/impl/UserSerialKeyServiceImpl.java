package cn.edu.uestc.acmicpc.oj.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IUserSerialKeyDAO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.oj.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Implementation for {@link UserSerialKeyService}.
 */
@Service
@Primary
public class UserSerialKeyServiceImpl extends AbstractService implements UserSerialKeyService {

  private final IUserSerialKeyDAO userSerialKeyDAO;

  @Autowired
  public UserSerialKeyServiceImpl(IUserSerialKeyDAO userSerialKeyDAO) {
    this.userSerialKeyDAO = userSerialKeyDAO;
  }

  @Override
  public UserSerialKey findUserSerialKeyByUserId(Integer userId) throws AppException {
    try {
      return getDAO().getEntityByUniqueField("userId", userId);
    } catch (FieldNotUniqueException e) {
      throw new AppException(e);
    }
  }

  @Override
  public UserSerialKey generateUserSerialKey(Integer userId) throws AppException {
    UserSerialKey userSerialKey = findUserSerialKeyByUserId(userId);
    if (userSerialKey != null) {
      //less than 30 minutes
      if (new Date().getTime() - userSerialKey.getTime().getTime() <= 1800000) {
        throw new AppException( "serial key can only be generated in every 30 minutes.");
      }
    } else {
      userSerialKey = new UserSerialKey();
    }
    return userSerialKey;
  }

  @Override
  public void createNewUserSerialKey(UserSerialKey userSerialKey) throws AppException {
    userSerialKeyDAO.add(userSerialKey);
  }

  @Override
  public IUserSerialKeyDAO getDAO() {
    return userSerialKeyDAO;
  }
}
