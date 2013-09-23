package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.Problem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ProblemDAO AOP interface.
 */
public interface IProblemDAO extends IDAO<Problem, Integer, ProblemDTO> {

  @Override
  public ProblemDTO persist(ProblemDTO dto) throws AppException;
}
