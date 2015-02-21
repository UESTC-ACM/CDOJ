package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.impl.TrainingCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.TrainingDao;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.db.entity.Training;
import cn.edu.uestc.acmicpc.service.iface.TrainingService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TrainingServiceImpl extends AbstractService implements TrainingService {

  private final TrainingDao trainingDao;

  @Autowired
  public TrainingServiceImpl(TrainingDao trainingDao) {
    this.trainingDao = trainingDao;
  }

  @Override
  public TrainingDto getTrainingDto(Integer trainingId, Set<Fields> trainingFields)
      throws AppException {
    AppExceptionUtil.assertNotNull(trainingId);
    TrainingCriteria trainingCriteria = new TrainingCriteria(trainingFields);
    trainingCriteria.startId = trainingCriteria.endId = trainingId;
    return trainingDao.getDtoByUniqueField(trainingCriteria.getCriteria());
  }

  @Override
  public Long count(TrainingCriteria trainingCriteria) throws AppException {
    return trainingDao.count(trainingCriteria.getCriteria());
  }

  @Override
  public List<TrainingDto> getTrainingList(TrainingCriteria trainingCriteria, PageInfo pageInfo)
      throws AppException {
    return trainingDao.findAll(trainingCriteria.getCriteria(), pageInfo);
  }

  @Override
  public void updateTraining(TrainingDto trainingDto) throws AppException {
    AppExceptionUtil.assertNotNull(trainingDto);
    AppExceptionUtil.assertNotNull(trainingDto.getTrainingId());
    Training training = trainingDao.get(trainingDto.getTrainingId());
    AppExceptionUtil.assertNotNull(training);

    if (trainingDto.getTitle() != null) {
      training.setTitle(trainingDto.getTitle());
    }
    if (trainingDto.getDescription() != null) {
      training.setDescription(trainingDto.getDescription());
    }

    trainingDao.addOrUpdate(training);
  }

  @Override
  public Integer createNewTraining(String title) throws AppException {
    Training training = new Training();
    training.setTitle(title);
    training.setDescription("");
    trainingDao.addOrUpdate(training);
    return training.getTrainingId();
  }
}
