package cn.edu.uestc.acmicpc.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;

/**
 * Service for {@link cn.edu.uestc.acmicpc.web.listener.onlineuserslistener.OnlineUsersListener}
 */
@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
@Lazy(false)
public class OnlineUsersServiceImpl extends AbstractService implements OnlineUsersService{
  private static Map<String, UserDTO> userPool = new HashMap<>();
  private static Set<String> onlineList = new HashSet<>();

  public OnlineUsersServiceImpl() {
  }

  public synchronized void login(UserDTO user) {
    if (!userPool.containsKey(user.getUserName())) {
      userPool.put(user.getUserName(), user);
      onlineList.add(user.getUserName());
    }
  }

  public synchronized void logout(UserDTO user) {
    if (userPool.containsKey(user.getUserName())) {
      userPool.remove(user.getUserName());
      onlineList.remove(user.getUserName());
    }
  }

  public Integer getOnlineNumber() {
    return userPool.size();
  }

  public Set<String> getOnlineList() {
    return onlineList;
  }
}
