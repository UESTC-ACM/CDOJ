package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingStatusDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * TrainingStatusDAO AOP interface.
 */
public interface ITrainingStatusDAO extends IDAO<TrainingStatus, Integer, TrainingStatusDTO> {

  @Override
  public TrainingStatusDTO persist(TrainingStatusDTO dto) throws AppException;
}
