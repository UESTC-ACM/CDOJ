package cn.edu.uestc.acmicpc.db.dto.base;

import java.util.Map;

/**
 * DTO builder interface.
 *
 * @param <T> DTO type.
 */
public interface BaseBuilder<T extends BaseDTO<?>> {

  /**
   * Build DTO entity.
   *
   * @return DTO entity needed.
   */
  T build();

  /**
   * Build DTO by properties.
   *
   * @param properties property set in
   *                   {@link cn.edu.uestc.acmicpc.db.dao.iface.Dao#findAll(Class, BaseBuilder, cn.edu.uestc.acmicpc.db.condition.base.Condition)}.
   * @return DTO entity needed
   */
  T build(Map<String, Object> properties);
}
