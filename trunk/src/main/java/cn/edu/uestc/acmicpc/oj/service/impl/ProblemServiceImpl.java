package cn.edu.uestc.acmicpc.oj.service.impl;

import java.util.List;

import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.uestc.acmicpc.db.condition.impl.ProblemCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.ioc.dao.ProblemDAOAware;
import cn.edu.uestc.acmicpc.oj.service.iface.ProblemService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Implementation for {@link ProblemService}.
 */
@Service
public class ProblemServiceImpl extends AbstractService implements ProblemService, ProblemDAOAware {

  @Autowired
  private IProblemDAO problemDAO;

  @SuppressWarnings("unchecked")
  @Override
  public List<Integer> getAllVisibleProblemIds() throws AppException {
    ProblemCondition problemCondition = applicationContext.getBean(ProblemCondition.class);
    problemCondition.setIsVisible(true);
    return (List<Integer>) problemDAO.findAll(problemCondition.getCondition().addProjection(
        Projections.id()));
  }

  @Override
  public void setProblemDAO(IProblemDAO problemDAO) {
    this.problemDAO = problemDAO;
  }

  @Override
  public IDAO<Problem, Integer> getDAO() {
    return problemDAO;
  }
}
