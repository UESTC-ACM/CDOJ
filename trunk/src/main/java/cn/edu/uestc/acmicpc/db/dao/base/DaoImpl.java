package cn.edu.uestc.acmicpc.db.dao.base;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.criteria.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.Dao;
import cn.edu.uestc.acmicpc.db.dto.BaseDto;
import cn.edu.uestc.acmicpc.db.dto.BaseDtoBuilder;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.util.helper.DatabaseUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Repository;

/**
 * Global DAO implementation.
 * <p>
 * <strong>WARN</strong>: This class is only a abstract class, please create
 * subclass by overriding {@code getReference} method.
 *
 * @param <E> entity type
 */
@SuppressWarnings("deprecation")
@Repository
public abstract class DaoImpl<E extends Serializable> extends BaseDao implements Dao<E> {

  private static final Logger logger = LogManager.getLogger(DaoImpl.class);

  @Override
  public void addOrUpdate(E entity) throws AppException {
    try {
      if (DatabaseUtil.getKeyValue(entity) == null) {
        add(entity);
      } else {
        update(entity);
      }
    } catch (HibernateException e) {
      throw new AppException("Invoke addOrUpdate method error.");
    }
  }

  /**
   * Build HQL with class name.
   *
   * @param condition DB condition entity.
   * @return HQL with class name.
   */
  @Deprecated
  private String buildHQLString(Condition condition) {
    return "from " + getReferenceClass().getSimpleName() + " "
        + condition.toHQLString();
  }

  /**
   * Build HQL with class name, and the condition's order is considered..
   *
   * @param condition DB condition entity.
   * @return HQL with class name.
   */
  @Deprecated
  private String buildHQLStringWithOrders(Condition condition) {
    return "from " + getReferenceClass().getSimpleName() + " "
        + condition.toHQLStringWithOrders();
  }

  private Query getQuery(String hql, PageInfo pageInfo) {
    Query query = getSession().createQuery(hql);
    if (pageInfo != null) {
      query.setFirstResult((int) ((pageInfo.getCurrentPage() - 1) * pageInfo
          .getCountPerPage()));
      query.setMaxResults(pageInfo.getCountPerPage().intValue());
    }
    return query;
  }

  @Override
  @Deprecated
  public Long customCount(String fieldName, Condition condition)
      throws AppException {
    if (condition == null) {
      condition = new Condition();
    }
    return customCount(fieldName, buildHQLString(condition));
  }

  @Override
  @Deprecated
  public Long customCount(String fieldName, String hqlCondition) throws AppException {
    try {
      String hql = "select count(" + fieldName + ") "
          + hqlCondition;
      return (Long) getQuery(hql, null).uniqueResult();
    } catch (HibernateException e) {
      logger.error(e);
      throw new AppException("Invoke count method error.");
    }
  }

  @Override
  @Deprecated
  public Long count(String hqlCondition) throws AppException {
    return customCount("*", hqlCondition);
  }

  @Override
  @Deprecated
  public Long count(Condition condition) throws AppException {
    return customCount("*", condition);
  }

  private void add(E entity) throws AppException {
    try {
      getSession().save(entity);
    } catch (HibernateException e) {
      logger.error(e.getCause());
      throw new AppException("Invoke add method error.");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public E get(Integer key) throws AppException {
    try {
      if (key == null) {
        return null;
      }
      return (E) getSession().get(getReferenceClass(), key);
    } catch (HibernateException e) {
      logger.error(e);
      throw new AppException("Invoke get method error.");
    }
  }

  private void update(E entity) throws AppException {
    try {
      getSession().update(entity);
    } catch (HibernateException e) {
      logger.error(e);
      throw new AppException("Invoke update method error.");
    }
  }
  

  @Override
  @Deprecated
  public List<?> findAll(String hql) throws AppException {
    try {
      return getQuery(hql, null).list();
    } catch (HibernateException e) {
      logger.error(e);
      throw new AppException("Invoke findAll method error.");
    }
  }

  @Override
  @Deprecated
  public List<?> findAll(String fields, String hqlCondition, PageInfo pageInfo) throws AppException {
    try {
      String hql;
      if (fields == null) {
        hql = hqlCondition;
      } else {
        // TODO wrap the field by ``
        hql = "select " + fields + " " + hqlCondition;
      }
      return getQuery(hql, pageInfo).list();
    } catch (HibernateException e) {
      logger.error(e);
      throw new AppException("Invoke findAll method error.");
    }
  }

  @Override
  @Deprecated
  public List<?> findAll(String fields, Condition condition)
      throws AppException {
    String hql = "";
    try {
      if (fields == null) {
        hql = buildHQLStringWithOrders(condition);
      } else {
        // TODO wrap the field by ``
        hql = "select " + fields + " " + buildHQLStringWithOrders(condition);
      }
      return getQuery(hql, condition.getPageInfo()).list();
    } catch (HibernateException e) {
      logger.error("HQL = " + hql, e);
      throw new AppException("Invoke findAll method error.");
    }
  }

  @Override
  @Deprecated
  public Long count() throws AppException {
    return count((Condition) null);
  }

  @Override
  @Deprecated
  public Object getEntityByUniqueField(String fieldName, Object value)
      throws AppException {
    return getEntityByUniqueField(fieldName, value, null, false);
  }

  @Override
  @Deprecated
  public Object getEntityByUniqueField(
      String fieldName, Object value,
      String propertyName,
      boolean forceUnique) throws AppException {
    Condition condition = new Condition();
    condition.addEntry(fieldName, ConditionType.EQUALS, value);
    List<?> results = findAll(propertyName, condition);
    if (forceUnique) {
      return results.isEmpty() ? null : results.get(0);
    } else {
      if (results.isEmpty()) {
        return null;
      } else if (results.size() == 1) {
        return results.get(0);
      } else {
        throw new AppException("the value is not unique.");
      }
    }
  }

  /**
   * Return the specific Object class that will be used for class-specific
   * implementation of this DAO.
   *
   * @return the reference Class
   */
  protected abstract Class<E> getReferenceClass();

  @Override
  @Deprecated
  public void updateEntitiesByCondition(Map<String, Object> properties,
                                        Condition condition)
      throws AppException {
    if (properties.isEmpty()) {
      return;
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("update ").append(getReferenceClass().getSimpleName())
        .append(" set");
    Boolean first = true;
    for (String key : properties.keySet()) {
      if (!first) {
        stringBuilder.append(",");
      }
      first = false;
      stringBuilder.append(" ").append(key).append("=")
          .append(properties.get(key));
    }
    stringBuilder.append(" ").append(condition.toHQLString());
    String hql = stringBuilder.toString();
    try {
      getQuery(hql, null).executeUpdate();
    } catch (Exception e) {
      logger.error(e);
      throw new AppException("Error while execute database query.");
    }
  }

  /**
   * TODO(fish): need it test.
   */
  @Override
  @Deprecated
  public void deleteEntitiesByField(String field, String values) throws AppException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("delete ").append(getReferenceClass().getSimpleName());
    // TODO wrap the field by ``
    stringBuilder.append(" where ").append(field).append(" in (")
        .append(values).append(")");
    String hql = stringBuilder.toString();
    try {
      getQuery(hql, null).executeUpdate();
    } catch (Exception e) {
      logger.error(e);
      throw new AppException("Error while execute database query.");
    }
  }

  /**
   * TODO(fish): need it test.
   */
  @Override
  @Deprecated
  public void updateEntitiesByField(
      Map<String, Object> properties,
      String field, String values) throws AppException {
    if (properties.isEmpty()) {
      return;
    }
    StringBuilder stringBuilder = new StringBuilder();
    // TODO wrap the field by ``
    stringBuilder.append("update ").append(getReferenceClass().getSimpleName())
        .append(" set");
    Boolean first = true;
    for (String key : properties.keySet()) {
      if (!first) {
        stringBuilder.append(",");
      }
      first = false;
      Object value = properties.get(key);
      if (value instanceof String) {
        value = ((String) value).replaceAll("'", "''");
        stringBuilder.append(" ").append(key).append("='")
            .append(value).append("'");
      } else {
        stringBuilder.append(" ").append(key).append("=")
            .append(value);
      }
    }
    // TODO wrap the field by ``
    stringBuilder.append(" where ").append(field).append(" in (")
        .append(values).append(")");
    String hql = stringBuilder.toString();
    try {
      getQuery(hql, null).executeUpdate();
    } catch (Exception e) {
      logger.error("HQL: " + hql, e);
      throw new AppException("Error while execute database query.");
    }
  }

  @Override
  @Deprecated
  public void updateEntitiesByField(
      String propertyField, Object propertyValue,
      String field, String values) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put(propertyField, propertyValue);
    updateEntitiesByField(properties, field, values);
  }

  @Override
  @Deprecated
  public void updateEntitiesByCondition(
      String propertyField, Object propertyValue,
      Condition condition) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put(propertyField, propertyValue);
    updateEntitiesByCondition(properties, condition);
  }

  @Override
  public void flush() {
    getSession().flush();
  }

  @Override
  public int executeHQL(String hql) {
    return getQuery(hql, null).executeUpdate();
  }

  @Override
  public int executeSQL(String sql) {
    return getSession().createSQLQuery(sql).executeUpdate();
  }

  @Override
  @Deprecated
  public <T extends BaseDto<E>> List<T> findAll(
      Class<T> clazz,
      BaseDtoBuilder<T> builder,
      String hql,
      PageInfo pageInfo) throws AppException {
    AppExceptionUtil.assertTrue(
        clazz.isAnnotationPresent(Fields.class),
        "The class " + clazz.getCanonicalName() + "is not annotated with @Fields");
    String[] fields = clazz.getAnnotation(Fields.class).value();
    // TODO wrap the field by ``
    String queryField = ArrayUtil.join(fields, ",");
    List<?> result = findAll(queryField, hql, pageInfo);
    return aggregateResults(result, fields, builder);
  }

  private <T extends BaseDto<E>> List<T> aggregateResults(
      List<?> result, String[] fields, BaseDtoBuilder<T> builder) {
    List<T> list = new ArrayList<>();
    for (Iterator<?> iterator = result.iterator(); iterator.hasNext(); ) {
      Object[] entity = (Object[]) iterator.next();
      Map<String, Object> properties = new HashMap<>();
      for (int i = 0; i < fields.length; i++) {
        properties.put(fields[i], entity[i]);
      }
      list.add(builder.build(properties));
    }
    return list;
  }

  @Override
  @Deprecated
  public <T extends BaseDto<E>> List<T> findAll(
      Class<T> clazz, BaseDtoBuilder<T> builder,
      Condition condition) throws AppException {
    AppExceptionUtil.assertTrue(clazz.isAnnotationPresent(Fields.class),
                                "the class " + clazz + " is not annotated with Fields.");
    String[] fields = clazz.getAnnotation(Fields.class).value();
    // TODO wrap the field by ``
    String queryField = ArrayUtil.join(fields, ",");
    List<?> result = findAll(queryField, condition);
    return aggregateResults(result, fields, builder);
  }

  @Override
  @Deprecated
  public <T extends BaseDto<E>> T getDtoByUniqueField(
      Class<T> clazz,
      BaseDtoBuilder<T> builder,
      String field,
      Object value) throws AppException {
    Condition condition = new Condition();
    if (value instanceof String) {
      condition.addEntry(field, ConditionType.STRING_EQUALS, value);
    } else {
      condition.addEntry(field, ConditionType.EQUALS, value);
    }
    List<T> results = findAll(clazz, builder, condition);
    if (results.isEmpty()) {
      return null;
    } else if (results.size() == 1) {
      return results.get(0);
    } else {
      throw new AppException("the value is not unique.");
    }
  }

  @Override
  public void increment(String incrementField,
                        String field, String values) throws AppException {
    StringBuilder stringBuilder = new StringBuilder();
    // TODO wrap the field by ``
    stringBuilder.append("update ").append(getReferenceClass().getSimpleName())
        .append(" set ").append(incrementField).append(" = ").append(incrementField).append("+1")
        .append(" where ").append(field).append(" in (")
        .append(values).append(")");
    String hql = stringBuilder.toString();
    getQuery(hql, null).executeUpdate();
  }


  @SuppressWarnings("unchecked")
  @Override
  public <T extends BaseDto<E>, F extends cn.edu.uestc.acmicpc.db.dto.Fields> List<T> findAll(
      BaseCriteria<E, T> criteria, PageInfo pageInfo,
      Set<F> fields) throws AppException {
    DetachedCriteria detachedCriteria = criteria.getCriteria(fields);
    return (List<T>) customFindAll(detachedCriteria, pageInfo, fields);
  }

  @Override
  public <F extends cn.edu.uestc.acmicpc.db.dto.Fields> List<?> customFindAll(
      DetachedCriteria criteria, PageInfo pageInfo, Set<F> fields) throws AppException {
    Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
    if (pageInfo != null) {
      executableCriteria = executableCriteria
          .setFirstResult((int) ((pageInfo.getCurrentPage() - 1) * pageInfo.getCountPerPage()))
          .setMaxResults(pageInfo.getCountPerPage().intValue());
    }
    return executableCriteria.list();
  }

  @Override
  public <T extends BaseDto<E>, F extends cn.edu.uestc.acmicpc.db.dto.Fields> T getUniqueDto(
      BaseCriteria<E, T> criteria, Set<F> fields) throws AppException {
    List<T> result = findAll(criteria, null, fields);
    if (result.size() == 0) {
      return null;
    } else if (result.size() == 1) {
      return result.get(0);
    } else {
      throw new AppException("the value is not unique.");
    }
  }

  @Override
  public Long count(BaseCriteria<?, ?> criteria) throws AppException {
    DetachedCriteria detachedCriteria = criteria.getCriteria();
    detachedCriteria.setProjection(Projections.rowCount());
    return customCount(detachedCriteria);
  }

  @Override
  public Long customCount(DetachedCriteria criteria) throws AppException {
    Criteria executableCriteria = criteria.getExecutableCriteria(getSession());
    //logger.info( "[customCount]: " + criteria.toString());
    return (Long) executableCriteria.uniqueResult();
  }
}