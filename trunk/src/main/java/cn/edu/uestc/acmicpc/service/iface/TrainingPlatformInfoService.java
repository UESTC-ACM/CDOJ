package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.TrainingPlatformInfoCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingPlatformInfoFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.util.List;
import java.util.Set;

/**
 * Training platform info service interface
 */
public interface TrainingPlatformInfoService {

  /**
   * Get {@link TrainingPlatformInfoDto} by Training platform info id.
   *
   * @param trainingPlatformInfoId
   *          record's id
   * @param fields
   *          request fields
   * @return result
   * @throws AppException
   */
  TrainingPlatformInfoDto getTrainingPlatformInfoDto(Integer trainingPlatformInfoId,
      Set<TrainingPlatformInfoFields> fields) throws AppException;

  /**
   * Get all {@link TrainingPlatformInfoDto} fit in criteria.
   *
   * @param trainingPlatformInfoCriteria
   *          search criteria
   * @param fields
   *          result fields to be fetched
   * @return all records in database fit in the criteria.
   * @throws AppException
   */
  public List<TrainingPlatformInfoDto> getTrainingPlatformInfoList(
      TrainingPlatformInfoCriteria trainingPlatformInfoCriteria,
      Set<TrainingPlatformInfoFields> fields) throws AppException;

  /**
   * Update training platform info record by none-null fields in
   * {@link TrainingPlatformInfoDto} entity.
   *
   * @param trainingPlatformInfoDto
   *          {@link TrainingPlatformInfoDto} entity with none-null id.
   * @throws AppException
   */
  public void updateTrainingPlatformInfo(TrainingPlatformInfoDto trainingPlatformInfoDto)
      throws AppException;

  /**
   * Create a new training platform info record with specified training user id.
   *
   * @param trainingUserId
   *          training user id.
   * @return Id of new record.
   * @throws AppException
   */
  public Integer createNewTrainingPlatformInfo(Integer trainingUserId) throws AppException;

  /**
   * Remove training platform info record by id.
   *
   * @param trainingPlatformInfoId
   *          id.
   * @throws AppException
   */
  public void removeTrainingPlatformInfo(Integer trainingPlatformInfoId) throws AppException;
}
