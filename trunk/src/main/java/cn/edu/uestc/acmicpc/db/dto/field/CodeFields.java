package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Field projection settings for CodeDto.
 */
public enum CodeFields implements Fields {
  ALL_FIELDS(
      FieldProjection.Property("codeId"),
      FieldProjection.Property("content"),
      FieldProjection.Property("share")
  );

  private final FieldProjection[] projections;

  public FieldProjection[] getProjections() {
    return projections;
  }

  CodeFields(FieldProjection... projections) {
    this.projections = projections;
  }
}
