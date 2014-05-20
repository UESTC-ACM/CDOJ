package cn.edu.uestc.acmicpc.db.criteria.base;

import cn.edu.uestc.acmicpc.db.dto.field.FieldProjection;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * We can use this class to get {@link DetachedCriteria} entity.
 */
public abstract class BaseCriteria<Entity, Dto> {

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

  /**
   * Result class, this class should generate by protocol buffer
   */
  private Class<Dto> resultClass;

  /**
   * Specify the fields we need when build query criteria
   */
  private Fields resultFields;

  protected BaseCriteria(Class<Entity> referenceClass,
                         Class<Dto> resultClass,
                         Fields resultFields) {
    this.referenceClass = referenceClass;
    this.resultClass = resultClass;
    this.resultFields = resultFields;
  }

  protected BaseCriteria(Class<Entity> referenceClass,
                         Class<Dto> resultClass) {
    this.referenceClass = referenceClass;
    this.resultClass = resultClass;
  }

  public void setResultFields(Fields resultFields) {
    this.resultFields = resultFields;
  }

  /**
   * Get {@link DetachedCriteria} object.
   *
   * @return {@link DetachedCriteria} object we need.
   * @throws AppException
   */
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = DetachedCriteria.forClass(referenceClass);

    if (resultFields != null) {
      // Get field projection list
      FieldProjection[] fieldProjectionList = resultFields.getProjections();

      ProjectionList projectionList = Projections.projectionList();
      for (FieldProjection fieldProjection : fieldProjectionList) {
        if (fieldProjection.getType().equals("alias")) {
          // Set alias
          criteria.createAlias(fieldProjection.getField(), fieldProjection.getAlias());
        } else {
          // Set projection
          projectionList = projectionList.add(
              Projections.property(fieldProjection.getField()),
              fieldProjection.getAlias()
          );
        }
      }
      criteria.setProjection(projectionList);
    }

    // Set result transformer
    // We must set the projection first
    // setProjection() method will change the transformer to PROJECTION
    criteria = criteria.setResultTransformer(new AliasToBeanResultTransformer(resultClass));

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
