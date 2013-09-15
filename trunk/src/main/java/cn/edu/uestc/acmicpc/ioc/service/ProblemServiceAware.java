package cn.edu.uestc.acmicpc.ioc.service;

import cn.edu.uestc.acmicpc.oj.service.iface.ProblemService;

/**
 * Aware interface for {@link ProblemService}.
 */
public interface ProblemServiceAware {

  void setProblemUProblemService(ProblemService problemService);
}
