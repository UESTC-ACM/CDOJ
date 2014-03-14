package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * Service for {@link cn.edu.uestc.acmicpc.web.oj.listener.onlineusers.OnlineUsersListener}
 */
@Service
public class OnlineUsersServiceImpl extends AbstractService implements OnlineUsersService{
  private static final Set<String> userPool = new HashSet<>();

  public OnlineUsersServiceImpl() {
  }

  public synchronized void addOnlineUsers(UserDTO user) {
    userPool.add(user.getUserName());
  }

  public synchronized void removeOnlineUsers(UserDTO user) {
    userPool.remove(user.getUserName());
  }

  public Integer getOnlineNumber() {
    return userPool.size();
  }

  public Set<String> getOnlineList() {
    return userPool;
  }
}
