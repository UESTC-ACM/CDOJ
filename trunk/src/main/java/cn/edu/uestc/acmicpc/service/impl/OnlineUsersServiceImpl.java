package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service for
 * {@link cn.edu.uestc.acmicpc.web.oj.listener.onlineusers.OnlineUsersListener}
 */
@Service
public class OnlineUsersServiceImpl extends AbstractService implements OnlineUsersService {
  private static final Set<UserDto> userPool = new HashSet<>();

  public OnlineUsersServiceImpl() {
  }

  public synchronized void addOnlineUsers(UserDto user) {
    userPool.add(user);
  }

  public synchronized void removeOnlineUsers(UserDto user) {
    userPool.remove(user);
  }

  public Integer getNumberOfOnlineUsers() {
    return userPool.size();
  }

  public Set<UserDto> getOnlineList() {
    return userPool;
  }
}
