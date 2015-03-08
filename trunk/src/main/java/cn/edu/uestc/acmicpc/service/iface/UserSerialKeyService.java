package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * User serial key service interface.
 */
public interface UserSerialKeyService {

  /**
   * Get {@link UserSerialKeyDto} by user id.
   *
   * @param userId user id.
   * @return {@link UserSerialKeyDto} entity.
   * @throws AppException
   */
  public UserSerialKeyDto findUserSerialKeyDtoByUserId(Integer userId) throws AppException;

  /**
   * Generate a new user serial key record for specified user id.
   *
   * @param userId user id
   * @return {@link UserSerialKeyDto} entity.
   * @throws AppException if this user generated a user serial key in 30 minutes, throw a
   *                      exception and do nothing.
   */
  public UserSerialKeyDto generateUserSerialKey(Integer userId) throws AppException;

  /**
   * Save user serial key by {@link UserSerialKeyDto} entity.
   *
   * @param userSerialKeyDto {@link UserSerialKeyDto} entity
   * @throws AppException
   */
  public void updateUserSerialKey(UserSerialKeyDto userSerialKeyDto) throws AppException;

}
