package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemTagDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ProblemTagDTO;
import cn.edu.uestc.acmicpc.db.entity.ProblemTag;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for problemtag entity
 */
@Repository
public class ProblemTagDAO extends DAO<ProblemTag, Integer, ProblemTagDTO>
    implements IProblemTagDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ProblemTag> getReferenceClass() {
    return ProblemTag.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ProblemTagDTO persist(ProblemTagDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
