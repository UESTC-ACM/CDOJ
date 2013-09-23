package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Training Contest DAO implementation.
 */
@Repository
public class TrainingContestDAO extends DAO<TrainingContest, Integer, TrainingContestDTO>
    implements ITrainingContestDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<TrainingContest> getReferenceClass() {
    return TrainingContest.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public TrainingContestDTO persist(TrainingContestDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
