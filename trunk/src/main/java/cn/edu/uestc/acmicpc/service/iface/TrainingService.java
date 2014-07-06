package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.impl.TrainingCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.db.entity.Training;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.util.List;

/**
 * Training service interface.
 */
public interface TrainingService extends DatabaseService<Training, Integer> {

  /**
   * Get {@link TrainingDto} by Training id.
   *
   * @param trainingId training record's id.
   * @param trainingFields request fields.
   * @return result {@link TrainingDto} entity.
   * @throws AppException
   */
  public TrainingDto getTrainingDto(Integer trainingId,
                                    TrainingFields trainingFields) throws AppException;

  /**
   * Count number of trainings fit in criteria.
   *
   * @param trainingCriteria search criteria
   * @return total records in database hit the criteria
   * @throws AppException
   */
  public Long count(TrainingCriteria trainingCriteria) throws AppException;

  /**
   * Get all {@link TrainingDto} fit in criteria.
   *
   * @param trainingCriteria search criteria
   * @param pageInfo page range restriction
   * @return all records in database fit in the criteria
   * @throws AppException
   */
  public List<TrainingDto> getTrainingList(TrainingCriteria trainingCriteria,
                                           PageInfo pageInfo) throws AppException;

  /**
   * Update training record by none-null fields in {@link TrainingDto} entity
   *
   * @param trainingDto {@link TrainingDto} entity with none-null id
   * @throws AppException
   */
  public void updateTraining(TrainingDto trainingDto) throws AppException;

  /**
   * Create a new training record with specified title
   *
   * @param title training title
   * @return Id of new record
   * @throws AppException
   */
  public Integer createNewTraining(String title) throws AppException;

}
