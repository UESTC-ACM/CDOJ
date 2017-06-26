package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.TrainingCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.List;
import java.util.Set;

/**
 * Training service interface.
 */
public interface TrainingService {

  /**
   * Get {@link TrainingDto} by Training id.
   *
   * @param trainingId
   *          training record's id.
   * @param fields
   *          result fields to be fetched
   * @return result {@link TrainingDto} entity.
   * @throws AppException
   */
  public TrainingDto getTrainingDto(Integer trainingId, Set<TrainingFields> fields)
      throws AppException;

  /**
   * Count number of trainings fit in criteria.
   *
   * @param trainingCriteria
   *          search criteria
   * @return total records in database hit the criteria
   * @throws AppException
   */
  public Long count(TrainingCriteria trainingCriteria) throws AppException;

  /**
   * Get all {@link TrainingDto} fit in criteria.
   *
   * @param trainingCriteria
   *          search criteria
   * @param pageInfo
   *          page range restriction
   * @param fields
   *          result fields to be fetched
   * @return all records in database fit in the criteria
   * @throws AppException
   */
  public List<TrainingDto> getTrainingList(TrainingCriteria trainingCriteria,
      PageInfo pageInfo, Set<TrainingFields> fields) throws AppException;

  /**
   * Update training record by none-null fields in {@link TrainingDto} entity
   *
   * @param trainingDto
   *          {@link TrainingDto} entity with none-null id
   * @throws AppException
   */
  public void updateTraining(TrainingDto trainingDto) throws AppException;

  /**
   * Create a new training record with specified title
   *
   * @param title
   *          training title
   * @return Id of new record
   * @throws AppException
   */
  public Integer createNewTraining(String title) throws AppException;

}
