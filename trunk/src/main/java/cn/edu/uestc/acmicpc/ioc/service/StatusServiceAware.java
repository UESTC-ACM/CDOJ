package cn.edu.uestc.acmicpc.ioc.service;

import cn.edu.uestc.acmicpc.oj.service.iface.StatusService;

/**
 * Aware interface for {@link StatusService}.
 */
public interface StatusServiceAware {

  void setStatusService(StatusService statusService);
}
