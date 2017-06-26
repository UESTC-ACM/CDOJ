package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.FieldProjection;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import com.google.common.collect.Sets;
import java.util.Set;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;

/**
 * We can use this class to get {@link DetachedCriteria} entity.
 *
 * @param <E> entity type
 * @param <D> data transfer object type
 */
public abstract class BaseCriteria<E, D> {

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
  private final Class<E> referenceClass;

  /**
   * Result class, this class should generate by protocol buffer
   */
  private final Class<D> resultClass;

  private Set<Fields> aliases;

  protected BaseCriteria(
      Class<E> referenceClass, Class<D> resultClass) {
    this.referenceClass = referenceClass;
    this.resultClass = resultClass;
    this.aliases = Sets.newHashSet();
  }

  public Class<D> getResultClass() {
    return resultClass;
  }

  public void addAlias(Fields field) {
    aliases.add(field);
  }

  public Set<Fields> getAliases() {
    return aliases;
  }

  abstract DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException;

  public DetachedCriteria getCriteria() throws AppException {
    return getCriteria(null);
  }

  public <F extends Fields> DetachedCriteria getCriteria(Set<F> fields) throws AppException {
    DetachedCriteria criteria = prepareCriteria();
    updateCriteria(criteria);

    Set<Fields> aliasFields = Sets.newHashSet(getAliases());
    if (fields != null) {
      ProjectionList projectionList = Projections.projectionList();
      for (Fields field : fields) {
        FieldProjection fieldProjection = field.getProjection();
        if (fieldProjection.getType() == FieldProjection.ProjectionType.DB_FIELD) {
          // Set projection
          projectionList.add(Projections.property(fieldProjection.getName()),
              fieldProjection.getField());
          aliasFields.addAll(fieldProjection.getAliases());
        }
      }
      criteria.setProjection(projectionList);
    }
    for (Fields field : aliasFields) {
      FieldProjection fieldProjection = field.getProjection();
      criteria.createAlias(fieldProjection.getName(), fieldProjection.getField());
    }

    // Set result transformer
    // We must set the projection first
    // setProjection() method will change the transformer to PROJECTION
    criteria.setResultTransformer(
        new AliasToBeanResultTransformer(getResultClass()));
    return criteria;
  }

  private DetachedCriteria prepareCriteria() throws AppException {
    DetachedCriteria criteria = DetachedCriteria.forClass(referenceClass);

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

  static String wrapLike(String matchString) {
    return "%" + matchString + "%";
  }
}
