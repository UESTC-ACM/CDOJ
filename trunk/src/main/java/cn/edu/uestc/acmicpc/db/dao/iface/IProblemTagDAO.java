package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ProblemTagDTO;
import cn.edu.uestc.acmicpc.db.entity.ProblemTag;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ProblemTagDAO AOP interface.
 */
public interface IProblemTagDAO extends IDAO<ProblemTag, Integer, ProblemTagDTO> {

  @Override
  public ProblemTagDTO persist(ProblemTagDTO dto) throws AppException;
}
