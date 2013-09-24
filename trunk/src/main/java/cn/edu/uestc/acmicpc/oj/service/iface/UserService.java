package cn.edu.uestc.acmicpc.oj.service.iface;

import java.util.List;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.UserLoginDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.db.view.impl.UserView;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.Global;
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
   * @param user user dto to be updated.
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

  /**
   * Search user by condition and page info.
   *
   * @param userCondition condition
   * @param pageInfo page range
   * @return All user correspond to the condition and range.
   * @throws AppException
   */
  List<UserView> search(UserCondition userCondition, PageInfo pageInfo) throws AppException;

  /**
   * Count user number by condition
   *
   * @param userCondition condition
   * @return Tot users correspond to the condition.
   * @throws AppException
   */
  Long count(UserCondition userCondition) throws AppException;

  /**
   * Return UserView entity by user name
   *
   * @param userName user's name
   * @return UserView entity
   * @throws AppException
   */
  UserView getUserViewByUserName(String userName) throws AppException;

  /**
   * Edit user, and check the permission of edit operation.
   *
   * @param userDTO Edit information collected from form
   * @param currentUser Current user who do this operation
   * @throws AppException
   */
  void edit(UserDTO userDTO, UserDTO currentUser) throws AppException;

  /**
   * Transform user to user dto
   * @param user user entity
   * @return user dto entity
   * @throws AppException
   */
  UserDTO getUserDTOByUser(User user) throws AppException;

  /**
   * FIXME here we should consider the case when userDTO.getUserId is not null.
   * Transform user dto to user
   *
   * @param userDTO user dto entity
   * @return user entity
   * @throws AppException
   */
  User getUserByUserDTO(UserDTO userDTO) throws AppException;

  /**
   * Update user by user dto
   * @param user user entity who will be update
   * @param userDTO user dto entity, from form
   * @throws AppException
   */
  void updateUserByUserDTO(User user, UserDTO userDTO) throws AppException;

  /**
   * TODO
   * @param userName
   * @return
   * @throws AppException
   */
  Map<Integer, Global.AuthorStatusType> getUserProblemStatus(String userName) throws AppException;

  /**
   * TODO
   * @param userName
   * @return
   * @throws AppException
   */
  Boolean sendSerialKey(String userName) throws AppException;
}
