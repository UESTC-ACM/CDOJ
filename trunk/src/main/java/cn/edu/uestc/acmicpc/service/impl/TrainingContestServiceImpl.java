package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.TrainingContestCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.TrainingContestDao;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingContestFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.service.iface.TrainingContestService;
import cn.edu.uestc.acmicpc.util.enums.TrainingContestType;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TrainingContestServiceImpl extends AbstractService
    implements TrainingContestService {

  private final TrainingContestDao trainingContestDao;

  @Autowired
  public TrainingContestServiceImpl(TrainingContestDao trainingContestDao) {
    this.trainingContestDao = trainingContestDao;
  }

  @Override
  public TrainingContestDto getTrainingContestDto(Integer trainingContestId,
      Set<TrainingContestFields> trainingContestFields) throws AppException {
    AppExceptionUtil.assertNotNull(trainingContestId);
    TrainingContestCriteria trainingContestCriteria = new TrainingContestCriteria();
    trainingContestCriteria.startId = trainingContestCriteria.endId = trainingContestId;
    return trainingContestDao.getUniqueDto(trainingContestCriteria,
        trainingContestFields);
  }

  @Override
  public List<TrainingContestDto> getTrainingContestList(
      TrainingContestCriteria trainingContestCriteria,
      Set<TrainingContestFields> trainingContestFields) throws AppException {
    return trainingContestDao.findAll(trainingContestCriteria, null,
        trainingContestFields);
  }

  @Override
  public void updateTrainingContest(TrainingContestDto trainingContestDto) throws AppException {
    AppExceptionUtil.assertNotNull(trainingContestDto);
    AppExceptionUtil.assertNotNull(trainingContestDto.getTrainingContestId());
    TrainingContest trainingContest = trainingContestDao.get(trainingContestDto
        .getTrainingContestId());
    AppExceptionUtil.assertNotNull(trainingContest);

    if (trainingContestDto.getTrainingId() != null) {
      trainingContest.setTrainingId(trainingContestDto.getTrainingId());
    }
    if (trainingContestDto.getTitle() != null) {
      trainingContest.setTitle(trainingContestDto.getTitle());
    }
    if (trainingContestDto.getLink() != null) {
      trainingContest.setLink(trainingContestDto.getLink());
    }
    if (trainingContestDto.getRankList() != null) {
      trainingContest.setRankList(trainingContestDto.getRankList());
    }
    if (trainingContestDto.getType() != null) {
      trainingContest.setType(trainingContestDto.getType());
    }
    if (trainingContestDto.getPlatformType() != null) {
      trainingContest.setPlatformType(trainingContestDto.getPlatformType());
    }

    trainingContestDao.addOrUpdate(trainingContest);
  }

  @Override
  public Integer createNewTrainingContest(Integer trainingId) throws AppException {
    TrainingContest trainingContest = new TrainingContest();
    trainingContest.setTrainingId(trainingId);
    trainingContest.setTitle("");
    trainingContest.setLink("");
    trainingContest.setRankList("");
    trainingContest.setType(TrainingContestType.CONTEST.ordinal());
    trainingContest.setPlatformType(TrainingPlatformType.TC.ordinal());
    trainingContestDao.addOrUpdate(trainingContest);
    return trainingContest.getTrainingContestId();
  }
}
