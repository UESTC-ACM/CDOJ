package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestProblemDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for contestproblem entity.
 */
@Repository
public class ContestProblemDAO extends DAO<ContestProblem, Integer, ContestProblemDTO>
    implements IContestProblemDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ContestProblem> getReferenceClass() {
    return ContestProblem.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ContestProblemDTO persist(ContestProblemDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
