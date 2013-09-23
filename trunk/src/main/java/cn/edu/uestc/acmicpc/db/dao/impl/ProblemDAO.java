package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for problem entity.
 */
@Repository
public class ProblemDAO extends DAO<Problem, Integer, ProblemDTO> implements IProblemDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Problem> getReferenceClass() {
    return Problem.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ProblemDTO persist(ProblemDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
