package cn.edu.uestc.acmicpc.web.oj.listener.onlineusers;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDto;
import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;
import cn.edu.uestc.acmicpc.util.helper.BeanUtil;

import org.springframework.stereotype.Component;

import java.util.Objects;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

/**
 * Listener for listen user login logout operation.
 */
@Component
public class OnlineUsersListener implements HttpSessionAttributeListener {

  private OnlineUsersService onlineUsersService;

  private void setup() {
    if (this.onlineUsersService == null) {
      this.onlineUsersService = BeanUtil.getBean(OnlineUsersService.class);
    }
  }

  @Override
  public void attributeAdded(HttpSessionBindingEvent event) {
    setup();
    if (Objects.equals(event.getName(), "currentUser")) {
      onlineUsersService.addOnlineUsers((UserDto) event.getValue());
    }
  }

  @Override
  public void attributeRemoved(HttpSessionBindingEvent event) {
    setup();
    if (Objects.equals(event.getName(), "currentUser")) {
      onlineUsersService.removeOnlineUsers((UserDto) event.getValue());
    }
  }

  @Override
  public void attributeReplaced(HttpSessionBindingEvent event) {
  }
}
