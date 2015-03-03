package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserListDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserTypeAheadDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;
import java.util.Map;

/**
 * User service interface.
 */
@SuppressWarnings("deprecation")
public interface UserService {

  /**
   * Get {@link UserDto} by user id.
   *
   * @param userId user's id.
   * @return {@link UserDto} entity.
   * @throws AppException
   */
  public UserDto getUserDtoByUserId(Integer userId) throws AppException;

  /**
   * Get {@link UserDto} by user name.
   *
   * @param userName user's name.
   * @return {@link UserDto} entity.
   * @throws AppException
   */
  public UserDto getUserDtoByUserName(String userName) throws AppException;

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDto} by user
   * name.
   *
   * @param userName user's name.
   * @return {@link cn.edu.uestc.acmicpc.db.dto.impl.user.UserEditorDto} entity.
   * @throws AppException
   */
  public UserEditorDto getUserEditorDtoByUserName(String userName) throws AppException;

  /**
   * Get {@link UserCenterDto} by user name.
   *
   * @param userName user's name.
   * @return {@link UserDto} entity.
   * @throws AppException
   */
  public UserCenterDto getUserCenterDtoByUserName(String userName) throws AppException;

  /**
   * Get {@link UserDto} by user email.
   *
   * @param email user's email.
   * @return {@link UserDto} entity.
   * @throws AppException
   */
  public UserDto getUserDtoByEmail(String email) throws AppException;

  /**
   * Update user by {@link UserDto} entity.
   *
   * @param userDto {@link UserDto} entity.
   * @throws AppException
   */
  public void updateUser(UserDto userDto) throws AppException;

  /**
   * Create a new user record {@link UserDto} entity.
   *
   * @param userDto {@link UserDto} entity.
   * @return New user's user ID.
   * @throws AppException
   */
  public Integer createNewUser(UserDto userDto) throws AppException;

  /**
   * Counts the number of users fit in condition.
   *
   * @param condition {@link UserCondition} entity.
   * @return total number of users fit in the condition.
   * @throws AppException
   */
  @Deprecated
  public Long count(UserCondition condition) throws AppException;

  /**
   * Get the users fit in condition and page range.
   *
   * @param condition {@link UserCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of {@link UserListDto} entities.
   * @throws AppException
   */
  @Deprecated
  public List<UserListDto> getUserListDtoList(UserCondition condition, PageInfo pageInfo)
      throws AppException;

  /**
   * Get the users fit in condition and page range.
   *
   * @param condition {@link UserCondition} entity.
   * @param pageInfo  {@link PageInfo} entity.
   * @return List of
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.user.UserTypeAheadDto}
   * entities
   * @throws AppException
   */
  @Deprecated
  public List<UserTypeAheadDto> getUserTypeAheadDtoList(UserCondition condition, PageInfo pageInfo)
      throws AppException;

  /**
   * Update some fields of one user according the user id.
   *
   * @param properties field name and field value map.
   * @param userId     user's id.
   * @throws AppException
   */
  public void updateUserByUserId(Map<String, Object> properties, Integer userId)
      throws AppException;

  /**
   * Check whether a user exists.
   *
   * @param userName user's name
   * @return true if exists
   * @throws AppException
   */
  public Boolean checkUserExists(String userName) throws AppException;

  /**
   * Check whether a user exists.
   *
   * @param userId user's id
   * @return true if exists
   * @throws AppException
   */
  public Boolean checkUserExists(Integer userId) throws AppException;

  /**
   * Create onsite users by {@link UserDto} list.
   *
   * @param userList {@link cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto} list
   * @return
   * @throws AppException
   */
  public List<Integer> createOnsiteUsersByUserList(List<UserDto> userList) throws AppException;

  /**
   * Fetch all onsite users by contest's id. return as a list of {@link UserDto}
   * entities.
   *
   * @param contestId contest's id.
   * @return list of {@link UserDto} entities.
   * @throws AppException
   */
  public List<UserDto> fetchAllOnsiteUsersByContestId(Integer contestId) throws AppException;
}