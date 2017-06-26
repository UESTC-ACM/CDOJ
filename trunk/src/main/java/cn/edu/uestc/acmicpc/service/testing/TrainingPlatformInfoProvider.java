package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.TrainingPlatformInfoFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TrainingPlatformInfo provider for integration testing.
 */
@Component
public class TrainingPlatformInfoProvider {

  @Autowired
  private TrainingPlatformInfoService service;

  @Autowired
  private TrainingUserProvider trainingUserProvider;

  public TrainingPlatformInfoDto createTrainingPlatformInfo() throws AppException {
    Integer id = service.createNewTrainingPlatformInfo(
        trainingUserProvider.createTrainingUser().getTrainingUserId());
    return service.getTrainingPlatformInfoDto(id, TrainingPlatformInfoFields.ALL_FIELDS);
  }
}
