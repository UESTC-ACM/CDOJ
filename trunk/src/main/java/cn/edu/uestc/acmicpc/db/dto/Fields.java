package cn.edu.uestc.acmicpc.db.dto;

/**
 * Field projection settings interface
 */
public interface Fields {
  /**
   * Get projection list
   *
   * @return List of {@link cn.edu.uestc.acmicpc.db.dto.FieldProjection}
   */
  public FieldProjection[] getProjections();
}
