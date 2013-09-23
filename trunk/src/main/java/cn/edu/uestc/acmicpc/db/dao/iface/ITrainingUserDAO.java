package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * TrainingUserDAO AOP interface.
 */
public interface ITrainingUserDAO extends IDAO<TrainingUser, Integer, TrainingUserDTO> {

  @Override
  public TrainingUserDTO persist(TrainingUserDTO dto) throws AppException;
}
