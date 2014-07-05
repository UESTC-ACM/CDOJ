package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.impl.TrainingUserCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingUserFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

/**
 * Training user service interface.
 */
public interface TrainingUserService extends DatabaseService<TrainingUser, Integer> {

  /**
   * Get {@link TrainingUserDto} by Training user id.
   *
   * @param trainingUserId training user record's id.
   * @param trainingUserFields request fields.
   * @return result {@link TrainingUserDto} entity.
   * @throws AppException
   */
  public TrainingUserDto getTrainingUserDto(Integer trainingUserId,
      TrainingUserFields trainingUserFields) throws AppException;

  /**
   * Get all {@link TrainingUserDto} fit in criteria.
   *
   * @param trainingUserCriteria search criteria
   * @return all records in database fit in the criteria.
   * @throws AppException
   */
  public List<TrainingUserDto> getTrainingUserList(TrainingUserCriteria trainingUserCriteria) throws AppException;

  /**
   * Update training user record by none-null fields in {@link TrainingUserDto} entity.
   *
   * @param trainingUserDto {@link TrainingUserDto} entity with none-null id.
   * @throws AppException
   */
  public void updateTrainingUser(TrainingUserDto trainingUserDto) throws AppException;

  /**
   * Create a new training user record with specified cdoj user id.
   *
   * @param userId CDOJ user id.
   * @param trainingId training id.
   * @return Id of new record.
   * @throws AppException
   */
  Integer createNewTrainingUser(Integer userId, Integer trainingId) throws AppException;
}
