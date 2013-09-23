package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * TrainingContestDAO AOP interface.
 */
public interface ITrainingContestDAO extends IDAO<TrainingContest, Integer, TrainingContestDTO> {

  @Override
  public TrainingContestDTO persist(TrainingContestDTO dto) throws AppException;
}
