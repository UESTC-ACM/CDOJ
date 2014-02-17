package cn.edu.uestc.acmicpc.web.oj.listener.onlineusers;

import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.springframework.stereotype.Component;

import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.OnlineUsersService;
import cn.edu.uestc.acmicpc.web.oj.controller.springbean.SpringBeanController;

/**
 * Listener for listen user login logout operation.
 */
@Component
public class OnlineUsersListener implements HttpSessionAttributeListener {

  private OnlineUsersService onlineUsersService;

  @Override
  public void attributeAdded(HttpSessionBindingEvent event) {
    onlineUsersService = (OnlineUsersService) SpringBeanController.getBean("onlineUsersService");
    if (event.getName().equals("currentUser")) {
      onlineUsersService.addOnlineUsers((UserDTO) event.getValue());
    }
  }

  @Override
  public void attributeRemoved(HttpSessionBindingEvent event) {
    onlineUsersService = (OnlineUsersService) SpringBeanController.getBean("onlineUsersService");
    if (event.getName().equals("currentUser")) {
      onlineUsersService.removeOnlineUsers((UserDTO) event.getValue());
    }
  }

  @Override
  public void attributeReplaced(HttpSessionBindingEvent event) {
  }

}
