package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.userSerialKey.UserSerialKeyDTO;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * User serial key service interface.
 */
public interface UserSerialKeyService extends DatabaseService<UserSerialKey, Integer> {

  /**
   * Get {@link UserSerialKeyDTO} by user id.
   *
   * @param userId user id.
   * @return {@link UserSerialKeyDTO} entity.
   * @throws AppException
   */
  public UserSerialKeyDTO findUserSerialKeyDTOByUserId(Integer userId) throws AppException;

  /**
   * Generate a new user serial key record for specified user id.
   *
   * @param userId user id
   * @return {@link UserSerialKeyDTO} entity.
   * @throws AppException if this user generated a user serial key in 30 minutes, throw a
   *                      exception and do nothing.
   */
  public UserSerialKeyDTO generateUserSerialKey(Integer userId) throws AppException;

  /**
   * Save user serial key by {@link UserSerialKeyDTO} entity.
   *
   * @param userSerialKeyDTO {@link UserSerialKeyDTO} entity
   * @throws AppException
   */
  public void updateUserSerialKey(UserSerialKeyDTO userSerialKeyDTO) throws AppException;

}
