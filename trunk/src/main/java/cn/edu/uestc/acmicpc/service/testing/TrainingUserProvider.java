package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.TrainingUserFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.service.iface.TrainingUserService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * TrainingUser provider for integration testing.
 */
@Component
public class TrainingUserProvider {

  @Autowired
  private TrainingUserService service;

  @Autowired
  private UserProvider userProvider;

  @Autowired
  private TrainingProvider trainingProvider;

  public TrainingUserDto createTrainingUser() throws AppException {
    Integer id = service.createNewTrainingUser(
        userProvider.createUser().getUserId(),
        trainingProvider.createTraining().getTrainingId());
    return service.getTrainingUserDto(id, TrainingUserFields.ALL_FIELDS);
  }
}
