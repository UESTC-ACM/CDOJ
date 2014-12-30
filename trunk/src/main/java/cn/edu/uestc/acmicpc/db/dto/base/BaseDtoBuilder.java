package cn.edu.uestc.acmicpc.db.dto.base;

import java.util.Map;

/**
 * Dto builder interface.
 *
 * @param <T>
 *          data transfer object type.
 */
public interface BaseDtoBuilder<T extends BaseDto<?>> {

  /**
   * Build Dto entity.
   *
   * @return Dto entity needed.
   */
  T build();

  /**
   * Build Dto by properties.
   *
   * @param properties
   *          property set in
   *          {@link cn.edu.uestc.acmicpc.db.dao.iface.Dao#findAll(Class, BaseDtoBuilder, cn.edu.uestc.acmicpc.db.condition.base.Condition)}
   *          .
   * @return Dto entity needed
   */
  T build(Map<String, Object> properties);
}
