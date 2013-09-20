package cn.edu.uestc.acmicpc.ioc.service;

import cn.edu.uestc.acmicpc.service.iface.GlobalService;

/**
 * Aware interface for {@link GlobalService}.
 */
public interface GlobalServiceAware {

  void setGlobalService(GlobalService globalService);
}
