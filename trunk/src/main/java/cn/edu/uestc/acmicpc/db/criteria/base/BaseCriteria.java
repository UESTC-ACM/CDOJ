package cn.edu.uestc.acmicpc.db.criteria.base;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

/**
 * We can use this class to get {@link DetachedCriteria} entity.
 */
public abstract class BaseCriteria<Entity> {

  /**
   * Current page id.
   */
  public Long currentPage;

  /**
   * Number of records per page.
   */
  public Long countPerPage;

  /**
   * Fields for ordering, separated with ','.
   */
  public String orderFields;

  /**
   * Whether ordered by ascending order for each fields, separated with ','.
   * Each value can be {@code true} or {@code false}.
   */
  public String orderAsc;

  /**
   * Reference class
   */
  private Class<Entity> referenceClass;

  protected BaseCriteria(Class<Entity> referenceClass) {
    this.referenceClass = referenceClass;
  }

  /**
   * Get {@link DetachedCriteria} object.
   *
   * @return {@link DetachedCriteria} object we need.
   * @throws AppException
   */
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = DetachedCriteria.forClass(referenceClass);

    if (orderFields != null) {
      String[] fields = orderFields.split(",");
      String[] asc = orderAsc.split(",");
      AppExceptionUtil.assertTrue(fields.length == asc.length);
      for (int i = 0; i < fields.length; i++) {
        if (asc[i].equals("true")) {
          criteria.addOrder(Order.asc(fields[i]));
        } else {
          criteria.addOrder(Order.desc(fields[i]));
        }
      }
    }

    return criteria;
  }

}
