package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.userSerialKey.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * User serial key service interface to handle operations about {@link UserSerialKey}.
 */
public interface UserSerialKeyService extends DatabaseService<UserSerialKey, Integer> {

  /**
   * Find user serial key by user id
   * @param userId user id
   * @return user serial key of that user
   * @throws AppException
   */
  public UserSerialKeyDTO findUserSerialKeyDTOByUserId(Integer userId) throws AppException;

  /**
   * Generate a new user serial key for user
   * @param userId user id
   * @return a new user serial key if there isn't exists
   * @throws AppException if this user generated a user serial key in 30 minutes
   */
  public UserSerialKeyDTO generateUserSerialKey(Integer userId) throws AppException;

  /**
   * Save new user serial key
   * @param userSerialKeyDTO Entity
   * @throws AppException
   */
  public void updateUserSerialKey(UserSerialKeyDTO userSerialKeyDTO) throws AppException;

}
