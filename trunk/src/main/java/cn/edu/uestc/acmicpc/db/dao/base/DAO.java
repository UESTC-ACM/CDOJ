package cn.edu.uestc.acmicpc.db.dao.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.JoinedProperty;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.DatabaseUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Global DAO implementation.
 * <p/>
 * <strong>WARN</strong>: This class is only a abstract class, please create subclass by overriding
 * {@code getReference} method.
 *
 * @param <Entity> Entity's type
 * @param <PK> Primary key's type
 */
@Repository
public abstract class DAO<Entity extends Serializable, PK extends Serializable>
    extends BaseDAO implements IDAO<Entity, PK> {

  private static final Logger LOGGER = LogManager.getLogger(DAO.class);

  @Override
  public void addOrUpdate(Entity entity) throws AppException {
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
   * Get primary key's type.
   *
   * @return primary key's type
   */
  protected abstract Class<PK> getPKClass();

  /**
   * Create a criteria object from session.
   *
   * @return expected criteria entity
   * @throws AppException
   */
  private Criteria createCriteria() throws AppException {
    try {
      return getSession().createCriteria(getReferenceClass());
    } catch (Exception e) {
      e.printStackTrace();
      throw new AppException("Get criteria error");
    }
  }

  /**
   * Update criteria entity according to specific conditions.
   *
   * @param criteria Criteria object to update
   * @param condition conditions for criteria query
   * @deprecated criteria is deprecated, use {@link Condition}.
   */
  @Deprecated
  private void updateCriteria(Criteria criteria, Condition condition) {
    if (condition.orders != null) {
      for (Condition.Order order : condition.orders) {
        criteria.addOrder(order.asc ? Order.asc(order.field) : Order.desc(order.field));
      }
    }
    criteria.setFirstResult(condition.getCurrentPage() == null ? 0 : (int) ((condition
        .getCurrentPage() - 1) * condition.getCountPerPage()));
    if (condition.getCountPerPage() != null) {
      criteria.setMaxResults((int) condition.getCountPerPage().longValue());
    }
    for (Criterion criterion : condition.getCriterionList()) {
      criteria.add(criterion);
    }
    for (JoinedProperty joinedProperty : condition.getJoinedProperties().values()) {
      criteria.add(joinedProperty.getCriterion());
    }
    if (condition.projections != null) {
      ProjectionList projectionList = Projections.projectionList();
      for (Projection projection : condition.projections) {
        projectionList.add(projection);
      }
      criteria.setProjection(projectionList);
    }
  }

  /**
   * Build hql with class name.
   *
   * @param condition DB condition entity.
   * @return hql with class name.
   */
  private String buildHQLString(Condition condition) {
    return "from " + getReferenceClass().getSimpleName() + " " + condition.toHQLString();
  }

  @Override
  public List<?> findAll(Condition condition) throws AppException {
    if (condition == null) {
      condition = new Condition();
    }
    try {
      String hql = buildHQLString(condition);
      return getSession().createQuery(hql).list();
    } catch (HibernateException e) {
      LOGGER.error(e);
      e.printStackTrace();
      throw new AppException("Invoke findAll method error.");
    }
  }

  @Override
  public Long customCount(Condition condition) throws AppException {
    if (condition == null) {
      return count();
    }
    try {
      Criteria criteria = createCriteria();
      updateCriteria(criteria, condition);
      return (Long) criteria.uniqueResult();
    } catch (HibernateException e) {
      throw new AppException("Invoke customCount method error.");
    }
  }

  @Override
  public Long count(Condition condition) throws AppException {
    if (condition == null) {
      condition = new Condition();
    }
    try {
      String hql = "select count(*) " + buildHQLString(condition);
      return (Long)getSession().createQuery(hql).uniqueResult();
    } catch (HibernateException e) {
      LOGGER.error(e);
      throw new AppException("Invoke count method error.");
    }
  }

  @Override
  public Serializable add(Entity entity) throws AppException {
    try {
      return getSession().save(entity);
    } catch (HibernateException e) {
      LOGGER.error(e);
      throw new AppException("Invoke add method error.");
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public Entity get(PK key) throws AppException {
    try {
      if (key == null) {
        return null;
      }
      return (Entity) getSession().get(getReferenceClass(), key);
    } catch (HibernateException e) {
      LOGGER.error(e);
      e.printStackTrace();
      throw new AppException("Invoke get method error.");
    }
  }

  @Override
  public void update(Entity entity) throws AppException {
    try {
      getSession().update(entity);
    } catch (HibernateException e) {
      LOGGER.error(e);
      throw new AppException("Invoke update method error.");
    }
  }

  @Override
  public List<?> findAll() throws AppException {
    return findAll(null);
  }

  @Override
  public Long count() throws AppException {
    return count(null);
  }

  @Override
  public Entity getEntityByUniqueField(String fieldName, Object value)
      throws FieldNotUniqueException, AppException {
    return getEntityByUniqueField(fieldName, value, fieldName, false);
  }

  @SuppressWarnings("unchecked")
  @Override
  public Entity getEntityByUniqueField(String fieldName, Object value, String propertyName,
      boolean forceUnique) throws FieldNotUniqueException, AppException {
    Entity result = null;
    Method[] methods = getReferenceClass().getMethods();
    try {
      for (Method method : methods) {
        String columnName = null;
        boolean isUnique = false;
        if (method.getAnnotation(Column.class) != null) {
          columnName = method.getAnnotation(Column.class).name();
          isUnique = method.getAnnotation(Column.class).unique();
        } else if (method.getAnnotation(JoinColumn.class) != null) {
          columnName = method.getAnnotation(JoinColumn.class).name();
          isUnique = method.getAnnotation(JoinColumn.class).unique();
        }
        if (forceUnique) {
          isUnique = true;
        }
        if (columnName != null && columnName.equals(fieldName)) {
          if (isUnique) {
            if (value == null) {
              return null;
            }
            Criteria criteria = getSession().createCriteria(getReferenceClass());
            criteria.add(Restrictions.eq(propertyName, value));
            List<?> list = criteria.list();
            if (list == null || list.isEmpty()) {
              return null;
            }
            result = (Entity) list.get(0);
            break;
          } else {
            throw new FieldNotUniqueException("Field '" + propertyName + "' is not unique.");
          }
        }
      }
    } catch (HibernateException e) {
      throw new AppException("Invoke getEntityByUniqueField method error.");
    }
    return result;
  }

  /**
   * Return the specific Object class that will be used for class-specific implementation of this
   * DAO.
   *
   * @return the reference Class
   */
  protected abstract Class<Entity> getReferenceClass();

  @Override
  @Deprecated
  public String getSQLString(Condition condition) throws AppException {
    if (condition == null) {
      return "";
    }
    List<String> params = new LinkedList<>();
    for (Criterion criterion : condition.getCriterionList()) {
      String field = criterion.toString();
      params.add(field);
    }
    for (Map.Entry<String, JoinedProperty> entry : condition.getJoinedProperties().entrySet()) {
      String key = entry.getKey();
      JoinedProperty joinedProperty = entry.getValue();
      String field;
      if (joinedProperty.getConditionType() == ConditionType.LIKE) {
        field =
            key + joinedProperty.getConditionType().getSignal() + "%"
                + joinedProperty.getKeyValue() + "%";
      } else {
        field = key + joinedProperty.getConditionType().getSignal() + joinedProperty.getKeyValue();
      }
      params.add(field);
    }

    if (params.isEmpty()) {
      return "";
    }

    return "where " + ArrayUtil.join(params.toArray(), " and ");
  }

  @Override
  public void updateEntitiesByCondition(Map<String, Object> properties, Condition condition)
      throws AppException {
    if (properties.isEmpty()) {
      return;
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("update ").append(getReferenceClass().getSimpleName()).append(" set");
    Boolean first = true;
    for (String key : properties.keySet()) {
      if (!first) {
        stringBuilder.append(",");
      }
      first = false;
      stringBuilder.append(" ").append(key).append("=").append(properties.get(key));
    }
    stringBuilder.append(" ").append(getSQLString(condition));
    String hql = stringBuilder.toString();
    LOGGER.info(hql);
    getSession().createQuery(hql).executeUpdate();
  }

  @Override
  public void deleteEntitiesByCondition(Condition condition) throws AppException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("delete ").append(getReferenceClass().getSimpleName());
    stringBuilder.append(" ").append(getSQLString(condition));
    String hql = stringBuilder.toString();
    LOGGER.info(hql);
    getSession().createQuery(hql).executeUpdate();
  }

  @Override
  public void flush() {
    getSession().flush();
  }

  @Override
  public int executeHQL(String hql) {
    return getSession().createQuery(hql).executeUpdate();
  }

  @Override
  public int executeSQL(String sql) {
    return getSession().createSQLQuery(sql).executeUpdate();
  }

  @Override
  public void delete(PK key) throws AppException {
    AppExceptionUtil.assertNotNull(key);
    Entity entity = get(key);
    AppExceptionUtil.assertNotNull(entity);
    try {
      getSession().delete(entity);
    } catch (HibernateException e) {
      LOGGER.error(e);
      throw new AppException("Invoke delete method error.");
    }
  }
}
