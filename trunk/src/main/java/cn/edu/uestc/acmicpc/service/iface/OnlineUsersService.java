package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;

import java.util.Set;

/**
 * OnlineUsers service interface.
 */
public interface OnlineUsersService {

  /**
   * Update the online users when a user logins.
   *
   * @param userDto
   *          {@link UserDto} entity.
   */
  public void addOnlineUsers(UserDto userDto);

  /**
   * Update the online users when a user logouts.
   *
   * @param userDto
   *          {@link UserDto} entity.
   */
  public void removeOnlineUsers(UserDto userDto);

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
  public Set<UserDto> getOnlineList();
}
