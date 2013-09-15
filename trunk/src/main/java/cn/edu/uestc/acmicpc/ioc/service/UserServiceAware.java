package cn.edu.uestc.acmicpc.ioc.service;

import cn.edu.uestc.acmicpc.oj.service.iface.UserService;

/**
 * Aware interface for {@link UserService}.
 */
public interface UserServiceAware {

  void setUserService(UserService userService);
}
