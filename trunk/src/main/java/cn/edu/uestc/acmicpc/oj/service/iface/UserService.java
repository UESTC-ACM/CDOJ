package cn.edu.uestc.acmicpc.oj.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
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
  User getUserByUserId(Integer userId) throws AppException;

  /**
   * Get unique user entity from database by user name.
   *
   * @param userName name of the user
   * @return the unique user entity from database
   * @throws AppException
   */
  User getUserByUserName(String userName) throws AppException;

  /**
   * Get unique user entity from database by user's email.
   *
   * @param email user's email.
   * @return the unique user entity from database.
   * @throws AppException
   */
  User getUserByEmail(String email) throws AppException;

  /**
   * Update user entity.
   *
   * @param user user entity to be updated.
   * @throws AppException
   */
  void updateUser(User user) throws AppException;

  /**
   * Create a new user entity and make persistence with it.
   *
   * @param user new user entity with {@code null} id.
   * @throws AppException
   */
  void createNewUser(User user) throws AppException;

  /**
   * User login operation
   *
   * @param userLoginDTO User need login (collect from form)
   * @return User dto
   * @throws AppException
   */
  UserDTO login(UserLoginDTO userLoginDTO) throws AppException;

  /**
   * User register operation
   *
   * @param userDTO User information (collect from form)
   * @return User dto
   * @throws AppException
   */
  UserDTO register(UserDTO userDTO) throws AppException;
}
