package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.impl.UserCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserAdminSummaryDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserCenterDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserSummaryDTO;
import cn.edu.uestc.acmicpc.db.entity.User;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;
/**
 * User service interface to handle operations about {@link User}.
 */
public interface UserService extends DatabaseService<User, Integer> {

  /**
   * Get unique user dto from database by user id.
   *
   * @param userId user's entity id.
   * @return the unique user entity from database.
   * @throws AppException
   */
  public UserDTO getUserDTOByUserId(Integer userId) throws AppException;

  /**
   * Get unique user dto from database by user name.
   *
   * @param userName name of the user
   * @return the unique user entity from database
   * @throws AppException
   */
  public UserDTO getUserDTOByUserName(String userName) throws AppException;

  /**
   * Get unique user center dto from database by user name.
   *
   * @param userName name of the user
   * @return the unique user entity from database
   * @throws AppException
   */
  public UserCenterDTO getUserCenterDTOByUserName(String userName) throws AppException;

  /**
   * Get unique user dto from database by user's email.
   *
   * @param email user's email.
   * @return the unique user entity from database.
   * @throws AppException
   */
  public UserDTO getUserDTOByEmail(String email) throws AppException;

  /**
   * Update user entity.
   *
   * @param userDTO user dto to be updated.
   * @throws AppException
   */
  public void updateUser(UserDTO userDTO) throws AppException;

  /**
   * Create a new user entity and make persistence with it.
   *
   * @param userDTO new user entity with {@code null} id.
   * @throws AppException
   */
  public void createNewUser(UserDTO userDTO) throws AppException;

  /**
   * Search user by condition and page info.
   *
   * @param userCondition condition
   * @param pageInfo page range
   * @return All user correspond to the condition and range.
   * @throws AppException
   */
  public List<UserSummaryDTO> search(UserCondition userCondition, PageInfo pageInfo)
      throws AppException;
  
  /**
   * Search user by condition and page info for admin dashboard.
   *
   * @param userCondition condition
   * @param pageInfo page range
   * @return All user correspond to the condition and range.
   * @throws AppException
   */
  public List<UserAdminSummaryDTO> adminSearch(UserCondition userCondition, PageInfo pageInfo)
      throws AppException;
  
  /**
   * Count user number by condition
   *
   * @param userCondition condition
   * @return Tot users correspond to the condition.
   * @throws AppException
   */
  public Long count(UserCondition userCondition) throws AppException;

}
