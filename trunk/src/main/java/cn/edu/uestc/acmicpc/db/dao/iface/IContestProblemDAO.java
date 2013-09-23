package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ContestProblemDAO AOP interface.
 */
public interface IContestProblemDAO extends IDAO<ContestProblem, Integer, ContestProblemDTO> {

  @Override
  public ContestProblemDTO persist(ContestProblemDTO dto) throws AppException;
}
