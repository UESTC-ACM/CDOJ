package cn.edu.uestc.acmicpc.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;

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
