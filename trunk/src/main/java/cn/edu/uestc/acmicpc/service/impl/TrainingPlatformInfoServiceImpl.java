package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.impl.TrainingPlatformInfoCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.TrainingPlatformInfoDao;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingPlatformInfoFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.db.entity.TrainingPlatformInfo;
import cn.edu.uestc.acmicpc.service.iface.TrainingPlatformInfoService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainingPlatformInfoServiceImpl extends AbstractService
    implements TrainingPlatformInfoService {

  private final TrainingPlatformInfoDao trainingPlatformInfoDao;

  @Autowired
  public TrainingPlatformInfoServiceImpl(TrainingPlatformInfoDao trainingPlatformInfoDao) {
    this.trainingPlatformInfoDao = trainingPlatformInfoDao;
  }

  @Override
  public TrainingPlatformInfoDto getTrainingPlatformInfoDto(Integer trainingPlatformInfoId,
      TrainingPlatformInfoFields trainingPlatformInfoFields) throws AppException {
    AppExceptionUtil.assertNotNull(trainingPlatformInfoId);
    TrainingPlatformInfoCriteria trainingPlatformInfoCriteria = new TrainingPlatformInfoCriteria(
        trainingPlatformInfoFields);
    trainingPlatformInfoCriteria.startId = trainingPlatformInfoCriteria.endId = trainingPlatformInfoId;
    return trainingPlatformInfoDao.getDtoByUniqueField(trainingPlatformInfoCriteria.getCriteria());
  }

  @Override
  public List<TrainingPlatformInfoDto> getTrainingPlatformInfoList(
      TrainingPlatformInfoCriteria trainingPlatformInfoCriteria) throws AppException {
    return trainingPlatformInfoDao.findAll(trainingPlatformInfoCriteria.getCriteria(), null);
  }

  @Override
  public void updateTrainingPlatformInfo(TrainingPlatformInfoDto trainingPlatformInfoDto)
      throws AppException {
    AppExceptionUtil.assertNotNull(trainingPlatformInfoDto);
    AppExceptionUtil.assertNotNull(trainingPlatformInfoDto.getTrainingPlatformInfoId());
    TrainingPlatformInfo trainingPlatformInfo = trainingPlatformInfoDao.get(trainingPlatformInfoDto
        .getTrainingPlatformInfoId());
    AppExceptionUtil.assertNotNull(trainingPlatformInfo);

    if (trainingPlatformInfoDto.getTrainingUserId() != null) {
      trainingPlatformInfo.setTrainingUserId(trainingPlatformInfoDto.getTrainingUserId());
    }
    if (trainingPlatformInfoDto.getUserName() != null) {
      trainingPlatformInfo.setUserName(trainingPlatformInfoDto.getUserName());
    }
    if (trainingPlatformInfoDto.getUserId() != null) {
      trainingPlatformInfo.setUserId(trainingPlatformInfoDto.getUserId());
    }
    if (trainingPlatformInfoDto.getType() != null) {
      trainingPlatformInfo.setType(trainingPlatformInfoDto.getType());
    }

    trainingPlatformInfoDao.addOrUpdate(trainingPlatformInfo);
  }

  @Override
  public Integer createNewTrainingPlatformInfo(Integer trainingUserId) throws AppException {
    TrainingPlatformInfo trainingPlatformInfo = new TrainingPlatformInfo();
    trainingPlatformInfo.setTrainingUserId(trainingUserId);
    trainingPlatformInfo.setUserName("");
    trainingPlatformInfo.setUserId("");
    trainingPlatformInfo.setType(0);
    trainingPlatformInfoDao.addOrUpdate(trainingPlatformInfo);
    return trainingPlatformInfo.getTrainingPlatformInfoId();
  }

  @Override
  public void removeTrainingPlatformInfo(Integer trainingPlatformInfoId) throws AppException {
    trainingPlatformInfoDao.deleteEntitiesByField("trainingPlatformInfoId",
        trainingPlatformInfoId.toString());
  }
}
