package cn.edu.uestc.acmicpc.service.iface;

import java.util.Set;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;

/**
 * OnlineUsers service interface.
 */
public interface OnlineUsersService{

  public void login(UserDTO user) ;

  public void logout(UserDTO user) ;

  public Integer getOnlineNumber() ;

  public Set<String> getOnlineList() ;
}
