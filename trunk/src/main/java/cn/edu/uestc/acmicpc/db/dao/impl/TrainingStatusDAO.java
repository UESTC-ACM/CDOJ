package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingStatusDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * // TODO(mzry1992) Description
 */
@Repository
public class TrainingStatusDAO extends DAO<TrainingStatus, Integer, TrainingStatusDTO>
    implements ITrainingStatusDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<TrainingStatus> getReferenceClass() {
    return TrainingStatus.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public TrainingStatusDTO persist(TrainingStatusDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
