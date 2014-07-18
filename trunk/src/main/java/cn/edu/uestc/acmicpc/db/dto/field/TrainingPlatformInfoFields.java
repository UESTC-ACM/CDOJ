package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Field projection settings for TrainingPlatformInfoDto
 */
public enum TrainingPlatformInfoFields implements Fields {
  ALL_FIELDS(
      FieldProjection.Alias("trainingUserByTrainingUserId", "trainingUser"),
      FieldProjection.Property("trainingPlatformInfoId"),
      FieldProjection.Property("trainingUserId"),
      FieldProjection.Property("trainingUser.trainingUserName", "trainingUserName"),
      FieldProjection.Property("userName"),
      FieldProjection.Property("userId"),
      FieldProjection.Property("type")
  );

  private final FieldProjection[] projections;

  public FieldProjection[] getProjections() {
    return projections;
  }

  TrainingPlatformInfoFields(FieldProjection... projections) {
    this.projections = projections;
  }
}
