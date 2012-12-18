package edu.uestc.acmicpc.db.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import java.util.LinkedList;
import java.util.List;


/**
 * some special method for database query
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 1
 */
@SuppressWarnings("UnusedDeclaration")
public abstract class RootDAO extends BaseRootDAO {
    RootDAO() {
    }

    RootDAO(Session session) {
        super(session);
    }

    /**
     * get all entities by Criterion list
     *
     * @param list Criterion list for query
     * @return entities in a list
     */
    public List<?> getEntityListByConditions(List<Criterion> list) {
        return getEntityListByConditions(list, null, null, null, null);
    }

    /**
     * get entities by Criteria object
     *
     * @param criteria     Criteria object for query
     * @param page         the page number we want to get, if null, get all records
     * @param countPerPage number of records in each page
     * @param orderField   the field name to be ordered
     * @param asc          whether we order the field by ascreasing or not
     * @return entities in a list
     */
    public List<?> getEntityListByCriteria(Criteria criteria, Integer page, Integer countPerPage, String orderField, Boolean asc) {
        try {
            if (orderField != null) {
                criteria.addOrder(asc ? Order.asc(orderField) : Order.desc(orderField));
            }

            criteria.setFirstResult(page == null ? 0 : (page - 1) * countPerPage);
            if (countPerPage != null)
                criteria.setMaxResults(countPerPage);

            List<?> result = criteria.list();
            closeCurrentSession();
            return result;
        } catch (Exception e) {
            return new LinkedList();
        }
    }

    /**
     * get entities by Criterion list
     *
     * @param list         Criterion list for query
     * @param page         the page number we want to get, if null, get all records
     * @param countPerPage number of records in each page
     * @param orderField   the field name to be ordered
     * @param asc          whether we order the field by ascreasing or not
     * @return entities in a list
     */
    public List<?> getEntityListByConditions(List<Criterion> list, Integer page, Integer countPerPage, String orderField, Boolean asc) {
        try {
            Session session = getSession();
            Criteria criteria = session.createCriteria(getReferenceClass());

            if (list != null) {
                for (Criterion criterion : list)
                    criteria.add(criterion);
            }

            return getEntityListByCriteria(criteria, page, countPerPage, orderField, asc);
        } catch (Exception e) {
            return new LinkedList();
        }
    }

    /**
     * count the number of entities with special conditions
     *
     * @param list Criterion list for query
     * @return number of records
     */
    public int countEntitiesByConditions(List<Criterion> list) {
        try {
            Session session = getSession();
            Criteria criteria = session.createCriteria(getReferenceClass());

            for (Criterion criterion : list)
                criteria.add(criterion);

            criteria.setProjection(Projections.rowCount());
            int result = Integer.valueOf(criteria.uniqueResult().toString());
            closeCurrentSession();
            return result;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * get enities from id list
     *
     * @param ids id list
     * @return all the entities query in id list
     */
    public List<?> getEntityListByIdList(Object[] ids) {
        try {
            List<?> result = getSession().createCriteria(getReferenceClass()).add(Restrictions.in("Id", ids)).list();
            closeCurrentSession();
            return result;
        } catch (Exception e) {
            return new LinkedList();
        }
    }
}
