package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * // TODO(mzry1992) Description
 */
@Repository
public class TrainingUserDAO extends DAO<TrainingUser, Integer, TrainingUserDTO>
    implements ITrainingUserDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<TrainingUser> getReferenceClass() {
    return TrainingUser.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public TrainingUserDTO persist(TrainingUserDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
