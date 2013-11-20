package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingContestDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;

import org.springframework.stereotype.Repository;

/**
 * Training Contest DAO implementation.
 */
@Repository
public class TrainingContestDAO extends DAO<TrainingContest, Integer>
    implements ITrainingContestDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<TrainingContest> getReferenceClass() {
    return TrainingContest.class;
  }
}
