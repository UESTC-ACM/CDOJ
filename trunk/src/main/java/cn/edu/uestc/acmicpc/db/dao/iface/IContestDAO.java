package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestDTO;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ContestDAO AOP interface.
 */
public interface IContestDAO extends IDAO<Contest, Integer, ContestDTO> {

  @Override
  public ContestDTO persist(ContestDTO dto) throws AppException;
}
