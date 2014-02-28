package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dao.iface.IUserSerialKeyDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.userSerialKey.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.settings.Global;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;

/**
 * Implementation for {@link UserSerialKeyService}.
 */
@Service
public class UserSerialKeyServiceImpl extends AbstractService implements UserSerialKeyService {

  private final IUserSerialKeyDAO userSerialKeyDAO;

  @Autowired
  public UserSerialKeyServiceImpl(IUserSerialKeyDAO userSerialKeyDAO) {
    this.userSerialKeyDAO = userSerialKeyDAO;
  }

  @Override
  public UserSerialKeyDTO findUserSerialKeyDTOByUserId(Integer userId) throws AppException {
    return userSerialKeyDAO.getDTOByUniqueField(UserSerialKeyDTO.class, UserSerialKeyDTO.builder(),
        "userId", userId);
  }

  @Override
  public UserSerialKeyDTO generateUserSerialKey(Integer userId) throws AppException {
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < Global.USER_SERIAL_KEY_LENGTH; ++i) {
      stringBuilder.append((char) (random.nextInt(126 - 33 + 1) + 33));
    }
    String serialKey = stringBuilder.toString();

    UserSerialKeyDTO userSerialKeyDTO = findUserSerialKeyDTOByUserId(userId);
    if (userSerialKeyDTO != null) {
      //less than 30 minutes
      if (new Date().getTime() - userSerialKeyDTO.getTime().getTime() <= 1800000) {
        throw new AppException("serial key can only be generated in every 30 minutes.");
      }
      userSerialKeyDTO.setSerialKey(serialKey);
      userSerialKeyDTO.setTime(new Timestamp(new Date().getTime()));
    } else {
      userSerialKeyDTO = UserSerialKeyDTO.builder()
          .setSerialKey(serialKey)
          .setUserId(userId)
          .setTime(new Timestamp(new Date().getTime()))
          .build();
    }
    updateUserSerialKey(userSerialKeyDTO);
    return userSerialKeyDTO;
  }

  @Override
  public void updateUserSerialKey(UserSerialKeyDTO userSerialKeyDTO) throws AppException {
    UserSerialKey userSerialKey;
    if (userSerialKeyDTO.getUserSerialKeyId() == null)
      userSerialKey = new UserSerialKey();
    else
      userSerialKey = userSerialKeyDAO.get(userSerialKeyDTO.getUserSerialKeyId());
    userSerialKey.setTime(userSerialKeyDTO.getTime());
    userSerialKey.setSerialKey(userSerialKeyDTO.getSerialKey());
    userSerialKey.setUserId(userSerialKeyDTO.getUserId());
    userSerialKeyDAO.addOrUpdate(userSerialKey);
  }

  @Override
  public IUserSerialKeyDAO getDAO() {
    return userSerialKeyDAO;
  }
}
