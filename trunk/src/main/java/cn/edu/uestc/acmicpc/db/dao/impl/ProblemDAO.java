package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.entity.Problem;

/**
 * DAO for problem entity.
 */
@Repository
public class ProblemDAO extends DAO<Problem, Integer> implements IProblemDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Problem> getReferenceClass() {
    return Problem.class;
  }
}
