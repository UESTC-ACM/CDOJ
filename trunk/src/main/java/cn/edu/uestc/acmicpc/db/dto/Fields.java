package cn.edu.uestc.acmicpc.db.dto;

/**
 * Field projection settings interface
 */
@FunctionalInterface
public interface Fields {

  /**
   * Get projection
   *
   * @return {@link cn.edu.uestc.acmicpc.db.dto.FieldProjection} entity
   */
  FieldProjection getProjection();
}
