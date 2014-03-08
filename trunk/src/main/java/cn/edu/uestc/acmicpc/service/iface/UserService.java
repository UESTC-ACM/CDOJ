package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserTypeAheadDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * User service interface.
 */
public interface UserService extends DatabaseService<User, Integer> {

  /**
   * Get {@link UserDTO} by user id.
   *
   * @param userId user's id.
   * @return {@link UserDTO} entity.
   * @throws AppException
   */
  public UserDTO getUserDTOByUserId(Integer userId) throws AppException;

  /**
   * Get {@link UserDTO} by user name.
   *
   * @param userName user's name.
   * @return {@link UserDTO} entity.
   * @throws AppException
   */
  public UserDTO getUserDTOByUserName(String userName) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDTO} by user name.
   *
   * @param userName user's name.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDTO} entity.
   * @throws AppException
   */
  public UserEditorDTO getUserEditorDTOByUserName(String userName) throws AppException;

  /**
   * Get {@link UserCenterDTO} by user name.
   *
   * @param userName user's name.
   * @return {@link UserDTO} entity.
   * @throws AppException
   */
  public UserCenterDTO getUserCenterDTOByUserName(String userName) throws AppException;

  /**
   * Get {@link UserDTO} by user email.
   *
   * @param email user's email.
   * @return {@link UserDTO} entity.
   * @throws AppException
   */
  public UserDTO getUserDTOByEmail(String email) throws AppException;

  /**
   * Update user by {@link UserDTO} entity.
   *
   * @param userDTO {@link UserDTO} entity.
   * @throws AppException
   */
  public void updateUser(UserDTO userDTO) throws AppException;

  /**
   * Create a new user record {@link UserDTO} entity.
   *
   * @param userDTO {@link UserDTO} entity.
   * @throws AppException
   */
  public void createNewUser(UserDTO userDTO) throws AppException;

  /**
   * Counts the number of users fit in condition.
   *
   * @param condition {@link UserCondition} entity.
   * @return total number of users fit in the condition.
   * @throws AppException
   */
  public Long count(UserCondition condition) throws AppException;

  /**
   * Get the users fit in condition and page range.
   *
   * @param condition {@link UserCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of {@link UserListDTO} entities.
   * @throws AppException
   */
  public List<UserListDTO> getUserListDTOList(UserCondition condition, PageInfo pageInfo)
      throws AppException;

  /**
   * Get the users fit in condition and page range.
   *
   * @param condition {@link UserCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of {@link cn.edu.uestc.acmicpc.db.dto.impl.user.UserTypeAheadDTO} entities
   * @throws AppException
   */
  public List<UserTypeAheadDTO> getUserTypeAheadDTOList(UserCondition condition, PageInfo pageInfo)
    throws AppException;

  /**
   * Update some fields of one user according the user id.
   *
   * @param properties field name and field value map.
   * @param userId     user's id.
   * @throws AppException
   */
  public void updateUserByUserId(Map<String, Object> properties, Integer userId) throws AppException;

  /**
   * Check whether a user exists.
   * @param userName user's name
   * @return true if exists
   * @throws AppException
   */
  public Boolean checkUserExists(String userName) throws AppException;

  /**
   * Check whether a user exists.
   * @param userId user's id
   * @return true if exists
   * @throws AppException
   */
  public Boolean checkUserExists(Integer userId) throws AppException;
}
