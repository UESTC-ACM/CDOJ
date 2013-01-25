/*
 *
 *  * cdoj, UESTC ACMICPC Online Judge
 *  * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  * 	mzry1992 <@link muziriyun@gmail.com>
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.oj.db.dao.base;

import cn.edu.uestc.acmicpc.oj.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.oj.util.exception.AppException;
import cn.edu.uestc.acmicpc.oj.util.exception.FieldNotUniqueException;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Global DAO implementation.
 *
 * @param <Entity> Entity's type
 * @param <PK>     Primary key's type
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 5
 */
public abstract class DAO<Entity extends Serializable, PK extends Serializable>
        extends BaseDAO implements IDAO<Entity, PK> {
    @Override
    public Serializable add(Entity entity) throws AppException {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        Serializable result = 0;
        try {
            result = session.save(entity);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new AppException("Invoke add method error.");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Entity get(PK key) throws AppException {
        Session session = getSession();
        Entity result = null;
        try {
            result = (Entity) session.get(getReferenceClass(), key);
        } catch (HibernateException e) {
            throw new AppException(e.getMessage());
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    @Override
    public void update(Entity entity) throws AppException {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(entity);
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            transaction.rollback();
            throw new AppException("Invoke update method error.");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @Override
    public void delete(Entity entity) throws AppException {
        Session session = getSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(entity);
            transaction.commit();
        } catch (HibernateException e) {
            transaction.rollback();
            throw new AppException("Invoke delete method error.");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Entity> findAll() throws AppException {
        List<Entity> list;
        Session session = getSession();
        try {
            list = session.createCriteria(getReferenceClass()).list();
        } catch (HibernateException e) {
            throw new AppException("Invoke findAll method error.");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return list;
    }

    public Long count() throws AppException {
        Session session = getSession();
        Long result = 0L;
        try {
            Criteria criteria = session.createCriteria(getReferenceClass());
            criteria.setProjection(Projections.count(getKeyFieldName()));
            result = (Long) criteria.uniqueResult();
        } catch (HibernateException e) {
            throw new AppException("Invoke count method error.");
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    /**
     * Get reference entity's id field name.
     *
     * @return id field name
     */
    protected String getKeyFieldName() {
        Method[] methods = getReferenceClass().getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(Id.class) != null) {
                Column column = method.getAnnotation(Column.class);
                if (column == null)
                    return null;
                return column.name();
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Entity getEntityByUniqueField(String fieldName, Object value) throws FieldNotUniqueException {
        Entity result = null;
        Method[] methods = getReferenceClass().getMethods();
        Session session = getSession();
        try {
            for (Method method : methods) {
                Column column = method.getAnnotation(Column.class);
                if (column != null && column.name().equals(fieldName)) {
                    if (column.unique()) {
                        if (value == null)
                            return null;
                        Criteria criteria = session.createCriteria(getReferenceClass());
                        criteria.add(Restrictions.eq(fieldName, value));
                        List list = criteria.list();
                        if (list == null || list.isEmpty())
                            return null;
                        result = (Entity) list.get(0);
                        break;
                    } else {
                        throw new FieldNotUniqueException("Field '" + fieldName + "' is not unique.");
                    }
                }
            }
        } finally {
            if (session != null && session.isOpen())
                session.close();
        }
        return result;
    }

    /**
     * Return the specific Object class that will be used for class-specific
     * implementation of this DAO.
     *
     * @return the reference Class
     */
    protected abstract Class<Entity> getReferenceClass();
}
