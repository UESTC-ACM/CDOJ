package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.UserSerialKeyCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.UserSerialKeyDao;
import cn.edu.uestc.acmicpc.db.dto.field.UserSerialKeyFields;
import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDto;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.service.iface.UserSerialKeyService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation for {@link UserSerialKeyService}.
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserSerialKeyServiceImpl extends AbstractService implements UserSerialKeyService {

  private final UserSerialKeyDao userSerialKeyDao;
  private final Integer USER_SERIAL_KEY_LENGTH = 128;

  @Autowired
  public UserSerialKeyServiceImpl(UserSerialKeyDao userSerialKeyDao) {
    this.userSerialKeyDao = userSerialKeyDao;
  }

  @Override
  public UserSerialKeyDto findUserSerialKeyDtoByUserId(Integer userId) throws AppException {
    UserSerialKeyCriteria criteria = new UserSerialKeyCriteria();
    criteria.userId = userId;
    return userSerialKeyDao.getUniqueDto(criteria, UserSerialKeyFields.ALL_FIELDS);
  }

  @Override
  public UserSerialKeyDto generateUserSerialKey(Integer userId) throws AppException {
    StringBuilder stringBuilder = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < USER_SERIAL_KEY_LENGTH; ++i) {
      stringBuilder.append((char) (random.nextInt(126 - 33 + 1) + 33));
    }
    String serialKey = stringBuilder.toString();

    UserSerialKeyDto userSerialKeyDto = findUserSerialKeyDtoByUserId(userId);
    if (userSerialKeyDto != null) {
      // less than 30 minutes
      if (new Date().getTime() - userSerialKeyDto.getTime().getTime() <= 1800000) {
        throw new AppException("serial key can only be generated in every 30 minutes.");
      }
      userSerialKeyDto.setSerialKey(serialKey);
      userSerialKeyDto.setTime(new Timestamp(new Date().getTime()));
    } else {
      userSerialKeyDto = UserSerialKeyDto.builder()
          .setSerialKey(serialKey)
          .setUserId(userId)
          .setTime(new Timestamp(new Date().getTime()))
          .build();
    }
    updateUserSerialKey(userSerialKeyDto);
    return userSerialKeyDto;
  }

  @Override
  public void updateUserSerialKey(UserSerialKeyDto userSerialKeyDto) throws AppException {
    UserSerialKey userSerialKey;
    if (userSerialKeyDto.getUserSerialKeyId() == null) {
      userSerialKey = new UserSerialKey();
    } else {
      userSerialKey = userSerialKeyDao.get(userSerialKeyDto.getUserSerialKeyId());
    }
    userSerialKey.setTime(userSerialKeyDto.getTime());
    userSerialKey.setSerialKey(userSerialKeyDto.getSerialKey());
    userSerialKey.setUserId(userSerialKeyDto.getUserId());
    userSerialKeyDao.addOrUpdate(userSerialKey);
  }
}
