package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingUserDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;

import org.springframework.stereotype.Repository;

/**
 * // TODO(mzry1992): Description
 */
@Repository
public class TrainingUserDAO extends DAO<TrainingUser, Integer> implements ITrainingUserDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<TrainingUser> getReferenceClass() {
    return TrainingUser.class;
  }
}
