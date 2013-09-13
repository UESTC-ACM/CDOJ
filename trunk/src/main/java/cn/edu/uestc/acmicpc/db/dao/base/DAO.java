/*
 *
 *  cdoj, UESTC ACMICPC Online Judge
 *  Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  	mzry1992 <@link muziriyun@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.dao.base;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.base.JoinedProperty;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.DatabaseUtil;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldNotUniqueException;

/**
 * Global DAO implementation.
 * <p/>
 * <strong>WARN</strong>: This class is only a abstract class, please create subclass by overriding
 * {@code getReference} method.
 *
 * @param <Entity> Entity's type
 * @param <PK> Primary key's type
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Repository
public abstract class DAO<Entity extends Serializable, PK extends Serializable> extends BaseDAO
    implements IDAO<Entity, PK> {

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
   */
  private Criteria createCriteria() throws AppException {
    try {
      return getSession().createCriteria(getReferenceClass());
    } catch (Exception e) {
      throw new AppException("Get criteria error");
    }
  }

  /**
   * Update criteria entity according to specific conditions.
   *
   * @param criteria Criteria object to update
   * @param condition conditions for criteria query
   */
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

  @Override
  public List<?> findAll(Condition condition) throws AppException {
    if (condition == null) {
      condition = new Condition();
    }
    try {
      Criteria criteria = createCriteria();
      updateCriteria(criteria, condition);
      return criteria.list();
    } catch (HibernateException e) {
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
      Criteria criteria = createCriteria();
      condition.projections = null;
      condition.addProjection(Projections.count(getKeyFieldName()));
      updateCriteria(criteria, condition);
      return (Long) criteria.uniqueResult();
    } catch (HibernateException e) {
      e.printStackTrace();
      throw new AppException("Invoke count method error.");
    }
  }

  @Override
  public Serializable add(Entity entity) throws AppException {
    try {
      return getSession().save(entity);
    } catch (HibernateException e) {
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
      throw new AppException("Invoke get method error.");
    }
  }

  @Override
  public void update(Entity entity) throws AppException {
    try {
      getSession().update(entity);
    } catch (HibernateException e) {
      e.printStackTrace();
      throw new AppException("Invoke update method error.");
    }
  }

  @Override
  public void delete(Entity entity) throws AppException {
    try {
      if (entity != null) {
        getSession().delete(entity);
      }
    } catch (HibernateException e) {
      throw new AppException("Invoke delete method error.");
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

  /**
   * Get reference entity's id field name.
   *
   * @return id field name
   */
  String getKeyFieldName() {
    Method[] methods = getReferenceClass().getMethods();
    for (Method method : methods) {
      if (method.getAnnotation(Id.class) != null) {
        Column column = method.getAnnotation(Column.class);
        if (column == null) {
          return null;
        }
        return column.name();
      }
    }
    return null;
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
  public String getSQLString(Condition condition) throws AppException {
    if (condition == null) {
      return "";
    }
    List<String> params = new LinkedList<>();
    for (Criterion criterion : condition.getCriterionList()) {
      String field = criterion.toString();
      params.add(field);
    }
    for (String key : condition.getJoinedProperties().keySet()) {
      JoinedProperty joinedProperty = condition.getJoinedProperties().get(key);
      String field;
      if (joinedProperty.getConditionType() == BaseCondition.ConditionType.like) {
        field = key + joinedProperty.getConditionType().getSignal() + "%"
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
    System.out.println(hql);
    getSession().createQuery(hql).executeUpdate();
  }

  @Override
  public void deleteEntitiesByCondition(Condition condition) throws AppException {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("delete ").append(getReferenceClass().getSimpleName());
    stringBuilder.append(" ").append(getSQLString(condition));
    String hql = stringBuilder.toString();
    System.out.println(hql);
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
}
