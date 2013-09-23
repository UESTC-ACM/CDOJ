package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingStatus;

/**
 * Data transfer object for {@link TrainingStatus}.
 */
public class TrainingStatusDTO implements BaseDTO<TrainingStatus> {

  public TrainingStatusDTO() {
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
