package cn.edu.uestc.acmicpc.util.checker.base;

import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Checker for file uploader or unzip tools.
 * <p/>
 * We can use {@code Checker} Entity when upload files or unzip a {@code zip} file.
 * <p/>
 * Override {@code check} method for checker working.
 *
 * @param <Entity> Entity type
 */
public interface Checker<Entity> {

  /**
   * Check certain entity, if the entity is invalid, throws an {@code AppException} object.
   *
   * @param entity entity to be checked
   * @throws AppException if the entity is invalid.
   */
  public void check(Entity entity) throws AppException;
}
