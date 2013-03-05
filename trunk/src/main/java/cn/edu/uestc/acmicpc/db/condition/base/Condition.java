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

package cn.edu.uestc.acmicpc.db.condition.base;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;

import java.util.LinkedList;
import java.util.List;

/**
 * Other conditions setting for DAO findAll method.
 * <p/>
 * <strong>For Developers</strong>:
 * <p/>
 * {@code currentPage} and {@code countPerPage} can be {@code null},
 * if the fields is {@code null} we ignore this restriction, otherwise
 * we consider the record range by this two fields.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 * @version 2
 */
public class Condition {
    /**
     * Current page number.
     */
    public Long currentPage;
    /**
     * Number of records per page.
     */
    public Long countPerPage;
    /**
     * Extra criterion list.
     */
    public List<Criterion> criterionList = null;
    /**
     * Order fields.
     */
    public List<Order> orders = null;
    /**
     * Select projections.
     */
    public List<Projection> projections = null;

    /**
     * Default constructor.
     */
    public Condition() {
    }

    /**
     * Constructor for currentPage, countPerPage and single order field.
     *
     * @param currentPage  current page number
     * @param countPerPage number of records per page
     * @param field        order field name
     * @param asc          whether the order field is asc or not
     */
    public Condition(Long currentPage, Long countPerPage, String field, Boolean asc) {
        this.currentPage = currentPage;
        this.countPerPage = countPerPage;
        if (field != null && asc != null)
            addOrder(field, asc);

    }

    /**
     * Add new order field into the order list.
     *
     * @param field new order field name
     * @param asc   whether new order field asc or not
     */
    public void addOrder(String field, boolean asc) {
        if (orders == null)
            orders = new LinkedList<Order>();
        orders.add(new Order(field, asc));
    }

    /**
     * Add new projection into the projection list.
     *
     * @param projection new projection object
     */
    public void addProjection(Projection projection) {
        if (projections == null)
            projections = new LinkedList<Projection>();
        projections.add(projection);
    }

    /**
     * Add new criterion into the criterion list.
     *
     * @param criterion new criterion object
     */
    public void addCriterion(Criterion criterion) {
        if (criterionList == null)
            criterionList = new LinkedList<Criterion>();
        criterionList.add(criterion);
    }

    /**
     * Order conditions.
     *
     * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
     * @version 1
     */
    public class Order {
        public Order(String field, boolean asc) {
            this.field = field;
            this.asc = asc;
        }

        /**
         * Order field name.
         */
        public String field;
        /**
         * Whether order field asc or not.
         */
        public boolean asc;
    }
}
