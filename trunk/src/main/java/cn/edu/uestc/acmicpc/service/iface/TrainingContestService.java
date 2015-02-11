package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.criteria.impl.TrainingContestCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.TrainingContestFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import java.util.List;

/**
 * Training contest service interface.
 */
public interface TrainingContestService {

  /**
   * Get {@link cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto} by training
   * contest id.
   *
   * @param trainingContestId
   *          record's id.
   * @param trainingContestFields
   *          request fields.
   * @return result {@link cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto}
   *         entity.
   * @throws AppException
   */
  public TrainingContestDto getTrainingContestDto(Integer trainingContestId,
      TrainingContestFields trainingContestFields) throws AppException;

  /**
   * Get all {@link cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto} fit in
   * criteria.
   *
   * @param trainingContestCriteria
   *          search criteria
   * @return all records in database fit in the criteria.
   * @throws AppException
   */
  public List<TrainingContestDto> getTrainingContestList(
      TrainingContestCriteria trainingContestCriteria) throws AppException;

  /**
   * Update training contest record by none-null fields in
   * {@link cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto} entity.
   *
   * @param trainingContestDto
   *          {@link cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto} entity
   *          with none-null id.
   * @throws AppException
   */
  public void updateTrainingContest(TrainingContestDto trainingContestDto) throws AppException;

  /**
   * Create a new training contest record with specified training id.
   *
   * @param trainingId
   *          training id.
   * @return Id of new record.
   * @throws AppException
   */
  public Integer createNewTrainingContest(Integer trainingId) throws AppException;
}
