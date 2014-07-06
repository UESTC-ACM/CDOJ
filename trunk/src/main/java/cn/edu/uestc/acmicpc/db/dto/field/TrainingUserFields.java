package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Field projection settings for TrainingUserDto
 */
public enum TrainingUserFields implements Fields {
  ALL_FIELDS(
      FieldProjection.Alias("userByUserId", "user"),
      FieldProjection.Property("trainingUserId"),
      FieldProjection.Property("trainingId"),
      FieldProjection.Property("userId"),
      FieldProjection.Property("user.userName", "userName"),
      FieldProjection.Property("trainingUserName"),
      FieldProjection.Property("type"),
      FieldProjection.Property("currentRating"),
      FieldProjection.Property("currentVolatility"),
      FieldProjection.Property("competitions"),
      FieldProjection.Property("rank"),
      FieldProjection.Property("maximumRating"),
      FieldProjection.Property("minimumRating"),
      FieldProjection.Property("mostRecentEventId"),
      FieldProjection.Property("mostRecentEventName"),
      FieldProjection.Property("ratingHistory")
  ),
  FIELDS_FOR_LIST_PAGE(
      FieldProjection.Alias("userByUserId", "user"),
      FieldProjection.Property("trainingUserId"),
      FieldProjection.Property("trainingUserName"),
      FieldProjection.Property("type"),
      FieldProjection.Property("currentRating"),
      FieldProjection.Property("currentVolatility"),
      FieldProjection.Property("competitions"),
      FieldProjection.Property("rank"),
      FieldProjection.Property("mostRecentEventId"),
      FieldProjection.Property("mostRecentEventName")
  );

  private final FieldProjection[] projections;

  public FieldProjection[] getProjections() {
    return projections;
  }

  TrainingUserFields(FieldProjection... projections) {
    this.projections = projections;
  }
}
