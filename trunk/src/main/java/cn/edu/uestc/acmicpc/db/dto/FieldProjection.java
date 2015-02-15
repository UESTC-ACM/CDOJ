package cn.edu.uestc.acmicpc.db.dto;

/**
 * Field projection
 */
public class FieldProjection {

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
  private String type;

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

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public FieldProjection(String field, String alias, String type) {
    this.field = field;
    this.alias = alias;
    this.type = type;
  }

  public static FieldProjection property(String field, String alias) {
    return new FieldProjection(field, alias, "property");
  }

  public static FieldProjection property(String field) {
    return new FieldProjection(field, field, "property");
  }

  public static FieldProjection alias(String field, String alias) {
    return new FieldProjection(field, alias, "alias");
  }
}
