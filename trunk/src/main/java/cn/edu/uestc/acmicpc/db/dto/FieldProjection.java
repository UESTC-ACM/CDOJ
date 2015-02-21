package cn.edu.uestc.acmicpc.db.dto;

/**
 * Field projection
 */
public class FieldProjection {

  public enum ProjectionType {
    ALIAS,
    DB_FIELD,
    FIELD,
  }

  /**
   * Field in database
   */
  private String field;

  /**
   * Alias
   */
  private String alias;

  /**
   * Projection type
   */
  private ProjectionType type;

  public String getField() {
    return field;
  }

  public void setField(String field) {
    this.field = field;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }

  public ProjectionType getType() {
    return type;
  }

  public void setType(ProjectionType type) {
    this.type = type;
  }

  public FieldProjection(String field, String alias, ProjectionType type) {
    this.field = field;
    this.alias = alias;
    this.type = type;
  }

  public static FieldProjection dbField(String field, String alias) {
    return new FieldProjection(field, alias, ProjectionType.DB_FIELD);
  }

  public static FieldProjection dbField(String field) {
    return dbField(field, field);
  }

  public static FieldProjection field(String field, String alias) {
    return new FieldProjection(field, alias, ProjectionType.FIELD);
  }

  public static FieldProjection field(String field) {
    return field(field, field);
  }

  public static FieldProjection alias(String field, String alias) {
    return new FieldProjection(field, alias, ProjectionType.ALIAS);
  }
}
