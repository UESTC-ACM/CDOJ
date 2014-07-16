package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Field projection settings for TrainingContestDto.
 */
public enum TrainingContestFields implements Fields {
  ALL_FIELDS(
      FieldProjection.Property("trainingContestId"),
      FieldProjection.Property("trainingId"),
      FieldProjection.Property("title"),
      FieldProjection.Property("link"),
      FieldProjection.Property("rankList"),
      FieldProjection.Property("type"),
      FieldProjection.Property("platformType")
  ),
  FIELDS_FOR_LIST_PAGE(
      FieldProjection.Property("trainingContestId"),
      FieldProjection.Property("trainingId"),
      FieldProjection.Property("title"),
      FieldProjection.Property("link"),
      FieldProjection.Property("type"),
      FieldProjection.Property("platformType")
  );

  private final FieldProjection[] projections;

  public FieldProjection[] getProjections() {
    return projections;
  }

  TrainingContestFields(FieldProjection... projections) {
    this.projections = projections;
  }
}
