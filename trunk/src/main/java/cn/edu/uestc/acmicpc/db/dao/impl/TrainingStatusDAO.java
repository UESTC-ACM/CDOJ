package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITrainingStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;

import org.springframework.stereotype.Repository;

@Repository
public class TrainingStatusDAO extends DAO<TrainingStatus, Integer> implements ITrainingStatusDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<TrainingStatus> getReferenceClass() {
    return TrainingStatus.class;
  }
}
