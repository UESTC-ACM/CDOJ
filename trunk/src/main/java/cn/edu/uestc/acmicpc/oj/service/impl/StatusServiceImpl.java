package cn.edu.uestc.acmicpc.oj.service.impl;

import java.util.List;

import cn.edu.uestc.acmicpc.util.Global;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.StatusCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.oj.service.iface.StatusService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Implementation for {@link StatusService}.
 */
@Service
@Primary
public class StatusServiceImpl extends AbstractService implements StatusService {

  private final IStatusDAO statusDAO;

  @Autowired
  public StatusServiceImpl(IStatusDAO statusDAO) {
    this.statusDAO = statusDAO;
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> findAllUserAcceptedProblemIds(Integer userId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    statusCondition.resultId = Global.OnlineJudgeReturnType.OJ_AC.ordinal();
    return (List<Integer>) statusDAO.findAll(statusCondition.getCondition().addProjection(
        Projections.groupProperty("problemByProblemId.problemId")));
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> findAllUserTriedProblemIds(Integer userId) throws AppException {
    StatusCondition statusCondition = new StatusCondition();
    statusCondition.userId = userId;
    return (List<Integer>) statusDAO.findAll(statusCondition.getCondition().addProjection(
        Projections.groupProperty("problemByProblemId.problemId")));
  }

  @Override
  public IStatusDAO getDAO() {
    return statusDAO;
  }
}
