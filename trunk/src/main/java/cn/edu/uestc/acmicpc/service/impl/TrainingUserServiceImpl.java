package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.TrainingUserCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.TrainingUserDao;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingUserFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.service.iface.TrainingUserService;
import cn.edu.uestc.acmicpc.util.enums.TrainingUserType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TrainingUserServiceImpl extends AbstractService implements TrainingUserService {

  private final TrainingUserDao trainingUserDao;

  @Autowired
  public TrainingUserServiceImpl(TrainingUserDao trainingUserDao) {
    this.trainingUserDao = trainingUserDao;
  }

  @Override
  public TrainingUserDto getTrainingUserDto(Integer trainingUserId, Set<TrainingUserFields> fields)
      throws AppException {
    AppExceptionUtil.assertNotNull(trainingUserId);
    TrainingUserCriteria trainingUserCriteria = new TrainingUserCriteria();
    trainingUserCriteria.startId = trainingUserCriteria.endId = trainingUserId;
    return trainingUserDao.getUniqueDto(trainingUserCriteria, fields);
  }

  @Override
  public List<TrainingUserDto> getTrainingUserList(TrainingUserCriteria trainingUserCriteria,
      Set<TrainingUserFields> fields) throws AppException {
    return trainingUserDao.findAll(trainingUserCriteria, null, fields);
  }

  @Override
  public void updateTrainingUser(TrainingUserDto trainingUserDto) throws AppException {
    AppExceptionUtil.assertNotNull(trainingUserDto);
    AppExceptionUtil.assertNotNull(trainingUserDto.getTrainingUserId());
    TrainingUser trainingUser = trainingUserDao.get(trainingUserDto.getTrainingUserId());
    AppExceptionUtil.assertNotNull(trainingUser);

    if (trainingUserDto.getTrainingId() != null) {
      trainingUser.setTrainingId(trainingUserDto.getTrainingId());
    }
    if (trainingUserDto.getUserId() != null) {
      trainingUser.setUserId(trainingUserDto.getUserId());
    }
    if (trainingUserDto.getTrainingUserName() != null) {
      trainingUser.setTrainingUserName(trainingUserDto.getTrainingUserName());
    }
    if (trainingUserDto.getType() != null) {
      trainingUser.setType(trainingUserDto.getType());
    }
    if (trainingUserDto.getCurrentRating() != null) {
      trainingUser.setCurrentRating(trainingUserDto.getCurrentRating());
    }
    if (trainingUserDto.getCurrentVolatility() != null) {
      trainingUser.setCurrentVolatility(trainingUserDto.getCurrentVolatility());
    }
    if (trainingUserDto.getCompetitions() != null) {
      trainingUser.setCompetitions(trainingUserDto.getCompetitions());
    }
    if (trainingUserDto.getRank() != null) {
      trainingUser.setRank(trainingUserDto.getRank());
    }
    if (trainingUserDto.getMinimumRating() != null) {
      trainingUser.setMinimumRating(trainingUserDto.getMinimumRating());
    }
    if (trainingUserDto.getMaximumRating() != null) {
      trainingUser.setMaximumRating(trainingUserDto.getMaximumRating());
    }
    if (trainingUserDto.getMostRecentEventId() != null) {
      trainingUser.setMostRecentEventId(trainingUserDto.getMostRecentEventId());
    }
    if (trainingUserDto.getMostRecentEventName() != null) {
      trainingUser.setMostRecentEventName(trainingUserDto.getMostRecentEventName());
    }
    if (trainingUserDto.getRatingHistory() != null) {
      trainingUser.setRatingHistory(trainingUserDto.getRatingHistory());
    }

    trainingUserDao.addOrUpdate(trainingUser);
  }

  @Override
  public Integer createNewTrainingUser(Integer userId, Integer trainingId) throws AppException {
    TrainingUser trainingUser = new TrainingUser();
    trainingUser.setTrainingId(trainingId);
    trainingUser.setUserId(userId);
    trainingUser.setTrainingUserName("");
    trainingUser.setType(TrainingUserType.PERSONAL.ordinal());
    trainingUser.setCurrentRating(1200.0);
    trainingUser.setCurrentVolatility(550.0);
    trainingUser.setCompetitions(0);
    trainingUser.setRank(0);
    trainingUser.setMinimumRating(1200.0);
    trainingUser.setMaximumRating(1200.0);
    trainingUser.setRatingHistory("[]");
    trainingUserDao.addOrUpdate(trainingUser);
    return trainingUser.getTrainingUserId();
  }
}
