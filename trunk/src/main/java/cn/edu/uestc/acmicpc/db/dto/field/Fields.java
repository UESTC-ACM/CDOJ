package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Field projection settings interface
 */
public interface Fields {
  /**
   * Get projection list
   *
   * @return List of {@link cn.edu.uestc.acmicpc.db.dto.field.FieldProjection}
   */
  public FieldProjection[] getProjections();
}
