package cn.edu.uestc.acmicpc.oj.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.exception.AppException;
/**
 * User service interface to handle operations about {@link User}.
 */
public interface UserService extends OnlineJudgeService<User, Integer> {

  /**
   * Get unique user entity from database by user id.
   *
   * @param userId user's entity id.
   * @return the unique user entity from database.
   * @throws AppException
   */
  UserDTO getUserByUserId(Integer userId) throws AppException;

  /**
   * Get unique user entity from database by user name.
   *
   * @param userName name of the user
   * @return the unique user entity from database
   * @throws AppException
   */
  UserDTO getUserByUserName(String userName) throws AppException;

  /**
   * Get unique user entity from database by user's email.
   *
   * @param email user's email.
   * @return the unique user entity from database.
   * @throws AppException
   */
  UserDTO getUserByEmail(String email) throws AppException;

  /**
   * Update user entity.
   *
   * @param userDTO user dto to be updated.
   * @throws AppException
   */
  void updateUser(UserDTO userDTO) throws AppException;

  /**
   * Create a new user entity and make persistence with it.
   *
   * @param userDTO new user entity with {@code null} id.
   * @throws AppException
   */
  void createNewUser(UserDTO userDTO) throws AppException;

  /**
   * Search user by condition and page info.
   *
   * @param userCondition condition
   * @param pageInfo page range
   * @return All user correspond to the condition and range.
   * @throws AppException
   */
  List<UserDTO> search(UserCondition userCondition, PageInfo pageInfo) throws AppException;

  /**
   * Count user number by condition
   *
   * @param userCondition condition
   * @return Tot users correspond to the condition.
   * @throws AppException
   */
  Long count(UserCondition userCondition) throws AppException;

}
