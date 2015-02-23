package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestUserDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Contest user service interface
 */
public interface ContestUserService {

  /**
   * Create a new contest user record in database by {@link ContestUserDto}
   * entity.
   *
   * @param contestUserDto {@link ContestUserDto} entity, specified the field value.
   * @return new record's id.
   * @throws AppException
   */
  public Integer createNewContestUser(ContestUserDto contestUserDto) throws AppException;

  /**
   * Remove all contest users (include the corresponding user record) in
   * database.
   *
   * @param contestId contest's id.
   * @throws AppException
   */
  public void removeContestUsersByContestId(Integer contestId) throws AppException;

  /**
   * Check whether a user has registered in a specified contest.
   *
   * @param userId    user's id.
   * @param contestId contest's id.
   * @return true if this user has registered in this contest.
   * @throws AppException
   */
  public Boolean fetchOnsiteUsersByUserIdAndContestId(Integer userId, Integer contestId)
      throws AppException;
}
