package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;

import java.util.Set;


/**
 * OnlineUsers service interface.
 */
public interface OnlineUsersService {

  /**
   * Update the online users when a user logins.
   *
   * @param userDTO {@link UserDTO} entity.
   */
  public void addOnlineUsers(UserDTO userDTO);

  /**
   * Update the online users when a user logouts.
   *
   * @param userDTO {@link UserDTO} entity.
   */
  public void removeOnlineUsers(UserDTO userDTO);

  /**
   * Get the number of online users.
   *
   * @return total number of online users.
   */
  public Integer getNumberOfOnlineUsers();

  /**
   * Get the list of online users.
   *
   * @return the list of online users.
   */
  public Set<UserDTO> getOnlineList();
}
