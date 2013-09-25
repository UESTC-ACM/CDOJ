package cn.edu.uestc.acmicpc.db.dao.iface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Global DAO interface.
 *
 * @param <Entity> Entity's type
 * @param <PK> Primary key's type
 */
public interface IDAO<Entity extends Serializable, PK extends Serializable> {

  /**
   * Add entity into database, and return number of Row changed.
   *
   * @param entity entity to be added.
   * @return number of rows changed.
   * @throws AppException
   */
  Serializable add(Entity entity) throws AppException;

  /**
   * Add entity or update entity, according to key value of the entity.
   *
   * @param entity entity to be added or updated
   * @throws AppException
   */
  void addOrUpdate(Entity entity) throws AppException;

  /**
   * Get entity by key value.
   *
   * @param key key value
   * @return entity which key value matches
   * @throws AppException
   */
  Entity get(PK key) throws AppException;

  /**
   * Update an entity object.
   *
   * @param entity entity to be updated
   * @throws AppException
   */
  public void update(Entity entity) throws AppException;

  /**
   * List all entities in tables.
   *
   * @return entity list in tables.
   * @throws AppException
   */
  List<?> findAll() throws AppException;

  /**
   * List all entities in tables by conditions.
   *
   * @param condition extra conditions for query
   * @return expected entity list
   * @throws AppException
   */
  List<?> findAll(Condition condition) throws AppException;

  /**
   * Count the number of records in the table.
   *
   * @return number of records we query
   * @throws AppException
   */
  Long count() throws AppException;

  /**
   * Count the number of records in the table by conditions.
   *
   * @param condition condition object
   * @return number of records we query
   * @throws AppException
   */
  Long count(Condition condition) throws AppException;

  /**
   * Get unique entity by the field name, if the field is not unique field, throw
   * {@code AppException}.
   *
   * @param fieldName the unique field name
   * @param value field's value
   * @return unique result, null if not exist
   * @throws FieldNotUniqueException
   * @throws AppException
   */
  Entity getEntityByUniqueField(String fieldName, Object value)
      throws FieldNotUniqueException, AppException;

  /**
   * Get unique entity by the field name, if the field is not unique field, throw
   * {@code AppException}.
   *
   * @param fieldName the unique field name
   * @param value field's value
   * @param propertyName property's name for JoinColumn
   * @param forceUnique force the field's unique property
   * @return unique result, null if not exist
   * @throws FieldNotUniqueException
   * @throws AppException
   */
  Entity getEntityByUniqueField(String fieldName, Object value, String propertyName,
      boolean forceUnique) throws FieldNotUniqueException, AppException;

  /**
   * Count number of entities for custom counting.
   *
   * @param condition user custom condition entity
   * @return number of records for database query result
   * @throws AppException
   * @Deprecated this method is not supported in new API, we are design new interface for that.
   */
  @Deprecated
  Long customCount(Condition condition) throws AppException;

  /**
   * Update all records according condition entity.
   *
   * @param properties properties for setting
   * @param condition specific condition entity
   * @throws AppException
   */
  void updateEntitiesByCondition(Map<String, Object> properties, Condition condition)
      throws AppException;

  /**
   * Delete all records according condition entity.
   *
   * @param condition specific condition entity
   * @throws AppException
   */
  void deleteEntitiesByCondition(Condition condition) throws AppException;

  /**
   * Execute SQL immediately
   */
  void flush();

  /**
   * Crate a hibernate query.
   *
   * @param hql hibernate query string
   * @return number of rows effected
   */
  int executeHQL(String hql);

  /**
   * Create a basic database query.
   *
   * @param sql SQL string for query
   * @return number of rows effected
   */
  int executeSQL(String sql);

  /**
   * Delete entity by key.
   *
   * @param key entity's key
   * @throws AppException
   */
  void delete(PK key) throws AppException;
}
