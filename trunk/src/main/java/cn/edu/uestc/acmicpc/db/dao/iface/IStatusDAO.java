package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.StatusDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * StatusDAO AOP interface.
 */
public interface IStatusDAO extends IDAO<Status, Integer, StatusDTO> {

  @Override
  public StatusDTO persist(StatusDTO dto) throws AppException;
}
