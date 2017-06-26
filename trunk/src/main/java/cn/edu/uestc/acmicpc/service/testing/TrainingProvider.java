package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.TrainingFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Training provider for integration testing.
 */
@Component
public class TrainingProvider {

  @Autowired
  private TrainingService service;

  public TrainingDto createTraining() throws AppException {
    Integer id = service.createNewTraining("training " + TestUtil.getUniqueId());
    return service.getTrainingDto(id, TrainingFields.ALL_FIELDS);
  }
}
