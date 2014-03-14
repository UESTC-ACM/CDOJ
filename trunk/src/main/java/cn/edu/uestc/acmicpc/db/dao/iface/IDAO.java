package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Global DAO interface.
 *
 * @param <Entity> Entity's type
 * @param <PK>     Primary key's type
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
   * @deprecated this method is not supported in new API, please use
   * {@link IDAO#findAll(Class, BaseBuilder, Condition)}
   */
  @Deprecated
  List<?> findAll() throws AppException;

  /**
   * List all entities in tables by HQL.
   *
   * @param hql HQL string for query.
   * @return expected entity list
   * @throws AppException
   */
  List<?> findAll(String hql) throws AppException;

  /**
   * List all entities in tables by fields name and condition entity.
   * <p/>
   * <strong>For developers:</strong> The return list's element type is
   * {@link Object}[], every element of the array is the field value.
   *
   * @param fields    fields name for query.
   * @param condition condition entity for DB query.
   * @return result list.
   * @throws AppException
   */
  List<?> findAll(String fields, Condition condition) throws AppException;

  /**
   * List all entities in tables by fields name and condition statement.
   * <p/>
   * <strong>For developers:</strong> The return list's element type is
   * {@link Object}[], every element of the array is the field value.
   *
   * @param fields    fields name for query.
   * @param condition condition entity for DB query.
   * @param pageInfo page constraint.
   * @return result list.
   * @throws AppException
   */
  List<?> findAll(String fields, String condition, PageInfo pageInfo) throws AppException;

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
   * @param hqlCondition hql statement
   * @return number of records we query
   * @throws AppException
   */
  Long count(String hqlCondition) throws AppException;

  /**
   * Count the number of records in the table by conditions.
   *
   * @param condition condition object
   * @return number of records we query
   * @throws AppException
   */
  Long count(Condition condition) throws AppException;

  /**
   * Get unique entity by the field name, if the field is not unique field,
   * throw {@code AppException}.
   *
   * @param fieldName the unique field name
   * @param value     field's value
   * @return unique result, null if not exist
   * @throws AppException
   */
  Object getEntityByUniqueField(String fieldName, Object value)
      throws AppException;

  /**
   * Get unique entity by the field name, if the field is not unique field,
   * throw {@code AppException}.
   *
   * @param fieldName    the unique field name
   * @param value        field's value
   * @param propertyName property's name for JoinColumn
   * @param forceUnique  force the field's unique property
   * @return unique result, null if not exist
   * @throws AppException
   */
  Object getEntityByUniqueField(String fieldName, Object value, String propertyName,
                                boolean forceUnique) throws AppException;

  /**
   * Count number of entities for custom counting.
   *
   * @param fieldName count field's name
   * @param condition user custom condition entity
   * @return number of records for database query result
   * @throws AppException
   */
  Long customCount(String fieldName, Condition condition) throws AppException;

  /**
   * Count number of entities for custom counting.
   *
   * @param fieldName count field's name
   * @param hqlCondition user custom hql statement
   * @return number of records for database query result
   * @throws AppException
   */
  Long customCount(String fieldName, String hqlCondition) throws AppException;

  /**
   * Update all records according condition entity.
   *
   * @param properties properties for setting
   * @param condition  specific condition entity
   * @throws AppException
   */
  void updateEntitiesByCondition(Map<String, Object> properties, Condition condition)
      throws AppException;

  /**
   * Update all records according field value.
   *
   * @param properties properties for setting
   * @param field      specific field name
   * @param values     records need to update
   */
  void updateEntitiesByField(Map<String, Object> properties, String field, String values) throws AppException;

  /**
   * Delete all records according field value.
   *
   * @param field specific field name
   * @param value records need to delete
   */
  void deleteEntitiesByField(String field, String value) throws AppException;

  /**
   * Update all records according field value.
   *
   * @param propertyField field for setting
   * @param propertyValue field value
   * @param field         specific field name
   * @param values        records need to update
   */
  void updateEntitiesByField(String propertyField, Object propertyValue, String field, String values) throws AppException;

  /**
   * Update all records according condition entity.
   *
   * @param propertyField field for setting
   * @param propertyValue field value
   * @param condition specific condition entity
   */
  void updateEntitiesByCondition(String propertyField, Object propertyValue, Condition condition) throws AppException;

  /**
   * Execute SQL immediately.
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
   * List all entity in condition for specific DTO type.
   *
   * @param clazz     DTO class type.
   * @param builder   DTO's builder, should extends from {@link BaseBuilder}.
   * @param condition DB query condition.
   * @return DTO list for this query.
   * @throws AppException
   */
  <T extends BaseDTO<Entity>> List<T> findAll(Class<T> clazz, BaseBuilder<T> builder,
                                              Condition condition) throws AppException;


  /**
   * List all entity for specific DTO type by HQL.
   *
   * @param clazz     DTO class type.
   * @param builder   DTO's builder, should extends from {@link BaseBuilder}.
   * @param hql HQL statement.
   * @return DTO list for this query.
   * @throws AppException
   */
  <T extends BaseDTO<Entity>> List<T> findAll(Class<T> clazz,
                                              BaseBuilder<T> builder,
                                              String hql, PageInfo pageInfo) throws AppException;
  /**
   * Get unique DTO entity by unique field.
   *
   * @param clazz   DTO class type.
   * @param builder DTO's builder, should extends from {@link BaseBuilder}.
   * @param field   unique field name.
   * @param value   field's value.
   * @return unique entity for query.
   * @throws AppException
   */
  <T extends BaseDTO<Entity>> T getDTOByUniqueField(Class<T> clazz, BaseBuilder<T> builder,
                                                    String field, Object value) throws AppException;

  /**
   * Increment a number-value field by 1 of all specific records.
   *
   * @param incrementField field want increment.
   * @param field          specific field name
   * @param values         records need to update
   * @throws AppException
   */
  void increment(String incrementField,
                 String field, String values) throws AppException;
}
