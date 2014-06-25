package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Field projection settings for TrainingDto.
 */
public enum TrainingFields implements Fields {
  ALL_FIELDS(
      FieldProjection.Property("trainingId"),
      FieldProjection.Property("title"),
      FieldProjection.Property("description")
  ),
  FIELDS_FOR_LIST_PAGE(
      FieldProjection.Property("trainingId"),
      FieldProjection.Property("title")
  );

  private final FieldProjection[] projections;

  public FieldProjection[] getProjections() {
    return projections;
  }

  TrainingFields(FieldProjection... projections) {
    this.projections = projections;
  }
}
