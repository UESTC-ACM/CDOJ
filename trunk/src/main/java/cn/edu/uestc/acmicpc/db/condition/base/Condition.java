package cn.edu.uestc.acmicpc.db.condition.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projection;

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;

/**
 * Conditions setting for DAO findAll method.
 * <p/>
 * <strong>For Developers</strong>:
 * <p/>
 * {@code currentPage} and {@code countPerPage} can be {@code null}, if the fields is {@code null}
 * we ignore this restriction, otherwise we consider the record range by this two fields.
 * <p/>
 * If this condition is stored as {@link Entry#getValue()}, we ignore the order
 * and projection information of it.
 */
public class Condition {

  /** Entries' joined type for DB query. */
  public static enum JoinedType {
    AND("and"), OR("or");

    private final String singal;

    private JoinedType(String singal) {
      this.singal = singal;
    }

    public String getSingal() {
      return singal;
    }
  }

  /** Basic condition type of database handler. */
  public static enum ConditionType {
    CONDITION(""), EQUALS("="), GREATER_THAN(">"), LESS_THAN("<"), GREATER_OR_EQUALS(">="),
    LESS_OR_EQUALS("<="), LIKE(" like "), STRING_EQUALS(" like "), IS_NULL("");

    private final String signal;

    public String getSignal() {
      return signal;
    }

    private ConditionType(String signal) {
      this.signal = signal;

    }
  }

  /** Condition basic entity. */
  public static class Entry {
    private final String fieldName;
    private final Object value;
    private final ConditionType conditionType;

    public static Entry of(Condition condition) throws AppException {
      AppExceptionUtil.assertNotNull(condition);
      return new Entry(null, ConditionType.CONDITION, condition);
    }

    public static Entry of(String fieldName, ConditionType conditionType, Object value)
        throws AppException {
      if (conditionType == ConditionType.CONDITION) {
        AppExceptionUtil.assertTrue(value instanceof Condition);
      }
      return new Entry(fieldName, conditionType, value);
    }

    private Entry(String fieldName, ConditionType conditionType, Object value) {
      this.fieldName = fieldName;
      this.conditionType = conditionType;
      this.value = value;
    }

    public String getFieldName() {
      return fieldName;
    }

    public Object getValue() {
      return value;
    }

    public ConditionType getConditionType() {
      return conditionType;
    }
  }

  /**
   * Current page number.
   */
  private Long currentPage;

  /**
   * Number of records per page.
   */
  private Long countPerPage;

  /**
   * DB query entries.
   */
  private final List<Entry> entries = new ArrayList<>();

  /** DB query joined type. */
  private final JoinedType joinedType;

  @Deprecated
  private List<Criterion> criterionList;

  @Deprecated
  private Map<String, JoinedProperty> joinedProperties;

  @Deprecated
  public Map<String, JoinedProperty> getJoinedProperties() {
    if (joinedProperties == null) {
      joinedProperties = new HashMap<>();
    }
    return joinedProperties;
  }

  @Deprecated
  public void setJoinedProperties(Map<String, JoinedProperty> joinedProperties) {
    this.joinedProperties = joinedProperties;
  }

  /**
   * Order fields.
   */
  private final List<Order> orders = new ArrayList<>();
  /**
   * Select projections.
   *
   * @deprecated this is not supported in new API,
   * please use {@link IDAO#findAll(String, Condition)}.
   */
  @Deprecated
  public List<Projection> projections;

  public Long getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(Long currentPage) {
    this.currentPage = currentPage;
  }

  public Long getCountPerPage() {
    return countPerPage;
  }

  public void setCountPerPage(Long countPerPage) {
    this.countPerPage = countPerPage;
  }

  @Deprecated
  public List<Criterion> getCriterionList() {
    if (criterionList == null) {
      criterionList = new LinkedList<>();
    }
    return criterionList;
  }

  @Deprecated
  public void setCriterionList(List<Criterion> criterionList) {
    this.criterionList = criterionList;
  }

  @Deprecated
  public List<Projection> getProjections() {
    if (projections == null) {
      projections = new LinkedList<>();
    }
    return projections;
  }

  @Deprecated
  public void setProjections(List<Projection> projections) {
    this.projections = projections;
  }

  /**
   * Default constructor.
   */
  public Condition() {
    this(JoinedType.AND);
  }

  public Condition(JoinedType joinedType) {
    this.joinedType = joinedType;
  }

  /**
   * Constructor for currentPage, countPerPage and single order field.
   *
   * @param currentPage current page number
   * @param countPerPage number of records per page
   * @param field order field name
   * @param asc whether the order field is asc or not
   * @throws AppException
   */
  public Condition(Long currentPage, Long countPerPage, String field, Boolean asc)
      throws AppException {
    this.currentPage = currentPage;
    this.countPerPage = countPerPage;
    if (field != null && asc != null) {
      addOrder(field, asc);
    }
    this.joinedType = JoinedType.AND;
  }

  /**
   * Adds new order field into the order list.
   *
   * @param field new order field name
   * @param asc whether new order field asc or not
   * @return condition itself.
   * @throws AppException
   */
  public Condition addOrder(String field, boolean asc) throws AppException {
    orders.add(new Order(field, asc));
    return this;
  }

  /**
   * Adds new projection into the projection list.
   *
   * @param projection new projection object
   * @return condition itself.
   * @deprecated it's not supported in new API, please use {@link Entry}.
   */
  @Deprecated
  public Condition addProjection(Projection projection) {
    getProjections().add(projection);
    return this;
  }

  /**
   * Adds new criterion into the criterion list.
   *
   * @param criterion new criterion object
   * @return condition itself.
   * @deprecated it's not supported in new API, please use {@link Entry}.
   */
  @Deprecated
  public Condition addCriterion(Criterion criterion) {
    getCriterionList().add(criterion);
    return this;
  }

  /**
   * Order conditions.
   */
  public static class Order {

    public Order(String field, boolean asc) throws AppException {
      AppExceptionUtil.assertNotNull(field);
      this.field = field;
      this.asc = asc;
    }

    @Override
    public String toString() {
      return field + " " + (asc ? " asc" : "desc");
    }

    /**
     * Order field name.
     */
    private final String field;
    /**
     * Whether order field asc or not.
     */
    private final boolean asc;
  }

  /**
   * Adds joined property for query.
   *
   * @param key property's key
   * @param value property's value
   * @return condition itself.
   * @deprecated this method is not supported in new API, please use.
   */
  @Deprecated
  public Condition addJoinedProperty(String key, JoinedProperty value) {
    getJoinedProperties().put(key, value);
    return this;
  }

  public void addEntry(Condition condition) throws AppException {
    addEntry(Entry.of(condition));
  }

  public void addEntry(String fieldName, ConditionType conditionType, Object value)
      throws AppException {
    addEntry(Entry.of(fieldName, conditionType, value));
  }

  public void addEntry(Entry entry) {
    entries.add(entry);
  }

  /**
   * Builds DB query string and append it into builder.
   * TODO IS_NULL
   *
   * @return if this condition's HQL is empty, return {@code false}.
   */
  private String buildHQLString() {
    String flag = " " + joinedType.getSingal() + " ";
    StringBuilder builder = new StringBuilder();
    boolean first = true;

    builder.append("(");
    for (Entry entry : entries) {
      if (entry.getConditionType() == ConditionType.CONDITION) {
        String hql = ((Condition) entry.getValue()).buildHQLString();
        if (hql != null) {
          if (first) {
            first = false;
          } else {
            builder.append(flag);
          }
          builder.append(hql);
        }
      } else {
        if (first) {
          first = false;
        } else {
          builder.append(flag);
        }
        builder.append(entry.getFieldName());
        builder.append(entry.getConditionType().getSignal());
        builder.append("'");
        if (entry.getConditionType() == ConditionType.LIKE) {
          builder.append("%").append(entry.getValue()).append("%");
        } else {
          builder.append(entry.getValue());
        }
        builder.append("'");
      }
    }
    if (first) {
      return null;
    }
    builder.append(")");
    return builder.toString();
  }

  /**
   * Gets DB query where clause by condition.
   *
   * @return DB query where clause.
   */
  public String toHQLString() {
    String hql = buildHQLString();
    if (hql == null) {
      return "";
    } else {
      return "where " + hql;
    }
  }

  /**
   * Gets HQL string with order by clause.
   *
   * @return HQL string we need.
   */
  public String toHQLStringWithOrders() {
    StringBuilder builder = new StringBuilder();
    builder.append(toHQLString());
    if (!orders.isEmpty()) {
      builder.append("order by");
      boolean first = true;
      for (Order order : orders) {
        if (first) {
          builder.append(" ").append(order.toString());
        } else {
          builder.append(",").append(order.toString());
        }
      }
    }
    return builder.toString();
  }

  public JoinedType getJoinedType() {
    return joinedType;
  }
}
