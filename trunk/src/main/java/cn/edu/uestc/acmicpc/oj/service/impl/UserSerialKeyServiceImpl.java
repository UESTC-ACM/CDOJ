package cn.edu.uestc.acmicpc.oj.service.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dao.iface.IUserSerialKeyDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.oj.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;

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

  private UserSerialKeyDTO getUserSerialKeyDTOByUserSerialKey(UserSerialKey userSerialKey) {
    return UserSerialKeyDTO.builder()
        .setUserSerialKeyId(userSerialKey.getUserSerialKeyId())
        .setSerialKey(userSerialKey.getSerialKey())
        .setTime(userSerialKey.getTime())
        .setUserId(userSerialKey.getUserId())
        .build();
  }

  @Override
  public UserSerialKeyDTO findUserSerialKeyByUserId(Integer userId) throws AppException {
    UserSerialKey userSerialKey = (UserSerialKey) getDAO().getEntityByUniqueField("userId", userId);
    return getUserSerialKeyDTOByUserSerialKey(userSerialKey);
  }

  @Override
  public UserSerialKeyDTO generateUserSerialKey(Integer userId) throws AppException {
    UserSerialKeyDTO userSerialKeyDTO = findUserSerialKeyByUserId(userId);
    if (userSerialKeyDTO != null) {
      //less than 30 minutes
      if (new Date().getTime() - userSerialKeyDTO.getTime().getTime() <= 1800000) {
        throw new AppException( "serial key can only be generated in every 30 minutes.");
      }
    } else {
      StringBuilder stringBuilder = new StringBuilder();
      Random random = new Random();
      for (int i = 0; i < Global.USER_SERIAL_KEY_LENGTH; ++i) {
        stringBuilder.append((char) (random.nextInt(126 - 33 + 1) + 33));
      }
      String serialKey = stringBuilder.toString();
      userSerialKeyDTO = UserSerialKeyDTO.builder()
          .setSerialKey(serialKey)
          .setUserId(userId)
          .setTime(new Timestamp(new Date().getTime()))
          .build();
    }
    return userSerialKeyDTO;
  }

  @Override
  public void createNewUserSerialKey(UserSerialKeyDTO userSerialKeyDTO) throws AppException {
    UserSerialKey userSerialKey = new UserSerialKey();
    userSerialKey.setTime(userSerialKeyDTO.getTime());
    userSerialKey.setSerialKey(userSerialKeyDTO.getSerialKey());
    userSerialKey.setUserId(userSerialKeyDTO.getUserId());
    userSerialKeyDAO.add(userSerialKey);
  }

  @Override
  public IUserSerialKeyDAO getDAO() {
    return userSerialKeyDAO;
  }
}
