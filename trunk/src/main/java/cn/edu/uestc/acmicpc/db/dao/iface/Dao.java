package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.criteria.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Global DAO interface.
 *
 * @param <E>
 *          entity type
 */
public interface Dao<E extends Serializable> {

  /**
   * Add entity or update entity, according to key value of the entity.
   *
   * @param entity
   *          entity to be added or updated
   * @return the saved entity
   * @throws AppException
   */
  E addOrUpdate(E entity) throws AppException;

  /**
   * Get entity by key value.
   *
   * @param key
   *          key value
   * @return entity which key value matches
   * @throws AppException
   */
  E get(Integer key) throws AppException;

  /**
   * List all entities in tables by HQL.
   *
   * @param hql
   *          HQL string for query.
   * @return expected entity list
   * @throws AppException
   */
  List<?> findAll(String hql) throws AppException;

  /**
   * List all entities in tables by fields name and condition entity.
   * <p>
   * <strong>For developers:</strong> The return list's element type is
   * {@link Object}[], every element of the array is the field value.
   *
   * @param fields
   *          fields name for query.
   * @param condition
   *          condition entity for DB query.
   * @return result list.
   * @throws AppException
   */
  List<?> findAll(String fields, Condition condition) throws AppException;

  /**
   * List all entities in tables by fields name and condition statement.
   * <p>
   * <strong>For developers:</strong> The return list's element type is
   * {@link Object}[], every element of the array is the field value.
   *
   * @param fields
   *          fields name for query.
   * @param condition
   *          condition entity for DB query.
   * @param pageInfo
   *          page constraint.
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
   * @param hqlCondition
   *          hql statement
   * @return number of records we query
   * @throws AppException
   */
  Long count(String hqlCondition) throws AppException;

  /**
   * Count the number of records in the table by conditions.
   *
   * @param condition
   *          condition object
   * @return number of records we query
   * @throws AppException
   */
  Long count(Condition condition) throws AppException;

  /**
   * Get unique entity by the field name, if the field is not unique field,
   * throw {@code AppException}.
   *
   * @param fieldName
   *          the unique field name
   * @param value
   *          field's value
   * @return unique result, null if not exist
   * @throws AppException
   */
  Object getEntityByUniqueField(String fieldName, Object value)
      throws AppException;

  /**
   * Get unique entity by the field name, if the field is not unique field,
   * throw {@code AppException}.
   *
   * @param fieldName
   *          the unique field name
   * @param value
   *          field's value
   * @param propertyName
   *          property's name for JoinColumn
   * @param forceUnique
   *          force the field's unique property
   * @return unique result, null if not exist
   * @throws AppException
   */
  Object getEntityByUniqueField(String fieldName, Object value, String propertyName,
      boolean forceUnique) throws AppException;

  /**
   * Count number of entities for custom counting.
   *
   * @param fieldName
   *          count field's name
   * @param condition
   *          user custom condition entity
   * @return number of records for database query result
   * @throws AppException
   */
  Long customCount(String fieldName, Condition condition) throws AppException;

  /**
   * Count number of entities for custom counting.
   *
   * @param fieldName
   *          count field's name
   * @param hqlCondition
   *          user custom hql statement
   * @return number of records for database query result
   * @throws AppException
   */
  Long customCount(String fieldName, String hqlCondition) throws AppException;

  /**
   * Update all records according condition entity.
   *
   * @param properties
   *          properties for setting
   * @param condition
   *          specific condition entity
   * @throws AppException
   */
  void updateEntitiesByCondition(Map<String, Object> properties, Condition condition)
      throws AppException;

  /**
   * Update all records according field value.
   *
   * @param properties
   *          properties for setting
   * @param field
   *          specific field name
   * @param values
   *          records need to update
   * @throws AppException
   */
  void updateEntitiesByField(Map<String, Object> properties, String field, String values)
      throws AppException;

  /**
   * Delete all records according field value.
   *
   * @param field
   *          specific field name
   * @param value
   *          records need to delete
   * @throws AppException
   */
  void deleteEntitiesByField(String field, String value) throws AppException;

  /**
   * Update all records according field value.
   *
   * @param propertyField
   *          field for setting
   * @param propertyValue
   *          field value
   * @param field
   *          specific field name
   * @param values
   *          records need to update
   * @throws AppException
   */
  void updateEntitiesByField(String propertyField, Object propertyValue, String field, String values)
      throws AppException;

  /**
   * Update all records according condition entity.
   *
   * @param propertyField
   *          field for setting
   * @param propertyValue
   *          field value
   * @param condition
   *          specific condition entity
   * @throws AppException
   */
  @Deprecated
  void updateEntitiesByCondition(String propertyField, Object propertyValue, Condition condition)
      throws AppException;

  /**
   * Execute SQL immediately.
   */
  void flush();

  /**
   * Crate a hibernate query.
   *
   * @param hql
   *          hibernate query string
   * @return number of rows effected
   */
  int executeHQL(String hql);

  /**
   * Create a basic database query.
   *
   * @param sql
   *          SQL string for query
   * @return number of rows effected
   */
  int executeSQL(String sql);

  /**
   * List all entity in condition for specific Dto type.
   *
   * @param clazz
   *          Dto class type.
   * @param builder
   *          Dto's builder, should extends from {@link BaseDtoBuilder}.
   * @param condition
   *          DB query condition.
   * @return Dto list for this query.
   * @throws AppException
   */
  @Deprecated
  <T extends BaseDto<E>> List<T> findAll(Class<T> clazz, BaseDtoBuilder<T> builder,
      Condition condition) throws AppException;

  /**
   * List all entity for specific Dto type by HQL.
   *
   * @param clazz
   *          Dto class type.
   * @param builder
   *          Dto's builder, should extends from {@link BaseDtoBuilder}.
   * @param hql
   *          HQL statement.
   * @param pageInfo
   *          page constraint.
   * @return Dto list for this query.
   * @throws AppException
   */
  <T extends BaseDto<E>> List<T> findAll(Class<T> clazz,
      BaseDtoBuilder<T> builder,
      String hql, PageInfo pageInfo) throws AppException;

  /**
   * Get unique Dto entity by unique field.
   *
   * @param clazz
   *          Dto class type.
   * @param builder
   *          Dto's builder, should extends from {@link BaseDtoBuilder}.
   * @param field
   *          unique field name.
   * @param value
   *          field's value.
   * @return unique entity for query.
   * @throws AppException
   */
  @Deprecated
  <T extends BaseDto<E>> T getDtoByUniqueField(Class<T> clazz, BaseDtoBuilder<T> builder,
      String field, Object value) throws AppException;

  /**
   * Increment a number-value field by 1 of all specific records.
   *
   * @param incrementField
   *          field want increment.
   * @param field
   *          specific field name
   * @param values
   *          records need to update
   * @throws AppException
   */
  void increment(String incrementField,
      String field, String values) throws AppException;

  /**
   * List all entity by criteria.
   *
   * @param criteria
   *          Hibernate criteria entity.
   * @param pageInfo
   *          page constraint.
   * @param <T>
   *          result type
   * @return List of results
   * @throws AppException
   */
  <T extends BaseDto<E>, F extends Fields> List<T> findAll(BaseCriteria<E, T> criteria,
      PageInfo pageInfo, Set<F> fields) throws AppException;

  <F extends Fields> List customFindAll(DetachedCriteria criteria,
      PageInfo pageInfo, Set<F> fields) throws AppException;
  /**
   * Get unique Dto entity by unique field.
   *
   * @param criteria
   *          Hibernate criteria entity.
   * @param <T>
   *          result type
   * @return unique entity for query.
   * @throws AppException
   */
  <T extends BaseDto<E>, F extends Fields> T getDtoByUniqueField(
      BaseCriteria<E, T> criteria, Set<F> fields) throws AppException;

  /**
   * Count the number of records in the table by criteria.
   *
   * @param criteria
   *          criteria object
   * @return number of records we query
   * @throws AppException
   */
  Long count(BaseCriteria criteria) throws AppException;

  /**
   * Count the number of records in the table by criteria.
   *
   * @param criteria
   *          criteria object
   * @return number of records we query
   * @throws AppException
   */
  Long customCount(DetachedCriteria criteria) throws AppException;
}
