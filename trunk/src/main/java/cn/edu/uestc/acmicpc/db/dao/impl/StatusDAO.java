package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.StatusDTO;
import cn.edu.uestc.acmicpc.db.entity.Status;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for status.
 */
@Repository
public class StatusDAO extends DAO<Status, Integer, StatusDTO> implements IStatusDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Status> getReferenceClass() {
    return Status.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public StatusDTO persist(StatusDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
