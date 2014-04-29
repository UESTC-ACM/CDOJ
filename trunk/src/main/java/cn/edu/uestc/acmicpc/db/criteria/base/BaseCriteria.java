package cn.edu.uestc.acmicpc.db.criteria.base;

import cn.edu.uestc.acmicpc.db.criteria.transformer.AliasToProtocolBufferBuilderTransformer;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;

import com.google.protobuf.GeneratedMessage;

/**
 * We can use this class to get {@link DetachedCriteria} entity.
 */
public abstract class BaseCriteria<Entity, Dto extends GeneratedMessage> {

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

  private Class<Dto> resultClass;

  protected BaseCriteria(Class<Entity> referenceClass, Class<Dto> resultClass) {
    this.referenceClass = referenceClass;
    this.resultClass = resultClass;
  }

  /**
   * Get {@link DetachedCriteria} object.
   *
   * @return {@link DetachedCriteria} object we need.
   * @throws AppException
   */
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = DetachedCriteria.forClass(referenceClass);

    // Set result transformer
    criteria.setResultTransformer(new AliasToProtocolBufferBuilderTransformer(resultClass));

    // Set order condition
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
