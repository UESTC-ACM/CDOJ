package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link TrainingStatus}.
 */
public class TrainingStatusDTO extends BaseDTO<TrainingStatus> {

  private TrainingStatusDTO() {
  }

  @Override
  public TrainingStatus getEntity() throws AppException {
    TrainingStatus trainingStatus = super.getEntity();
    trainingStatus.setPenalty(0);
    trainingStatus.setRank(0);
    trainingStatus.setRating(0.0);
    trainingStatus.setRatingVary(0.0);
    trainingStatus.setSolve(0);
    trainingStatus.setSummary("");
    trainingStatus.setVolatility(0.0);
    trainingStatus.setVolatilityVary(0.0);
    return trainingStatus;
  }

  @Override
  protected Class<TrainingStatus> getReferenceClass() {
    return TrainingStatus.class;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link TrainingStatusDTO}. */
  public static class Builder {

    public TrainingStatusDTO build() {
      return new TrainingStatusDTO();
    }
  }
}
