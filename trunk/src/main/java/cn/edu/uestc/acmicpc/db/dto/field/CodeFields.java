package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Description
 */
public enum CodeFields implements Fields {
  ALL(FieldProjection.Property("codeId"),
      FieldProjection.Property("content"),
      FieldProjection.Property("share"));

  private final FieldProjection[] projections;

  public FieldProjection[] getProjections() {
    return projections;
  }

  CodeFields(FieldProjection... projections) {
    this.projections = projections;
  }
}
