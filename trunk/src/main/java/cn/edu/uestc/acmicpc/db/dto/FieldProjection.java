package cn.edu.uestc.acmicpc.db.dto;

import com.google.common.collect.Lists;
import java.util.List;

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
   * Field name
   */
  private String name;

  /**
   * Field in databases
   */
  private String field;

  /**
   * Projection type
   */
  private ProjectionType type;

  private List<Fields> aliases;

  public String getName() {
    return name;
  }

  public String getField() {
    return field;
  }

  public ProjectionType getType() {
    return type;
  }

  public List<Fields> getAliases() {
    return aliases;
  }

  public FieldProjection(String name, String field, ProjectionType type, Fields... aliases) {
    this.name = name;
    this.field = field;
    this.type = type;
    this.aliases = Lists.newArrayList(aliases);
  }

  public static FieldProjection dbField(String field, String alias, Fields... aliases) {
    return new FieldProjection(field, alias, ProjectionType.DB_FIELD, aliases);
  }

  public static FieldProjection dbField(String field, Fields... aliases) {
    return dbField(field, field, aliases);
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
