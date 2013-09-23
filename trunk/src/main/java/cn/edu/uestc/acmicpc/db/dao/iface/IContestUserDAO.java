package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestUserDTO;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ContestUserDAO AOP interface.
 */
public interface IContestUserDAO extends IDAO<ContestUser, Integer, ContestUserDTO> {

  @Override
  public ContestUserDTO persist(ContestUserDTO dto) throws AppException;
}
