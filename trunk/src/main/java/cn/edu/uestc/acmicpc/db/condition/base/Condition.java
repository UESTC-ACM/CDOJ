package cn.edu.uestc.acmicpc.db.condition.base;

import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Conditions setting for
 * {@link cn.edu.uestc.acmicpc.db.dao.iface.Dao#findAll(String, Condition)} and
 * {@link cn.edu.uestc.acmicpc.db.dao.iface.Dao#count(Condition)}.
 * <p>
 * <strong>For Developers</strong>:
 * <p>
 * If this condition is stored as {@link Entry#getValue()}, we ignore the order
 * of it.
 *
 * @deprecated this class is deprecated, use
 *             {@link cn.edu.uestc.acmicpc.db.criteria.BaseCriteria}
 *             instead.
 */
@Deprecated
public class Condition {

  /**
   * Entries' joined type for DB query.
   */
  public static enum JoinedType {
    AND("and"), OR("or");

    private final String signal;

    private JoinedType(String signal) {
      this.signal = signal;
    }

    public String getSignal() {
      return signal;
    }
  }

  /**
   * Basic condition type of database handler.
   */
  public static enum ConditionType {
    /**
     * A sub condition as {@link Entry} entity.
     */
    CONDITION(""),
    /**
     * Be equal to a specific value.
     */
    EQUALS("="),
    /**
     * Be not equal to a specific value.
     */
    NOT_EQUALS("!="),
    /**
     * Be greater than a specific value.
     */
    GREATER_THAN(">"),
    /**
     * Be less than a specific value.
     */
    LESS_THAN("<"),
    /**
     * Be greater than or equal to a specific value.
     */
    GREATER_OR_EQUALS(">="),
    /**
     * Be less than or equal to a specific value.
     */
    LESS_OR_EQUALS("<="),
    /**
     * String contains.
     */
    LIKE(" like "),
    /**
     * String matches.
     */
    STRING_EQUALS(" like "),
    /**
     * Value is {@code null}.
     */
    IS_NULL(""),
    /**
     * Value is <strong>not</strong> {@code null}.
     */
    IS_NOT_NULL(""),
    /**
     * Value is in some set.
     */
    IN(" in ");

    private final String signal;

    public String getSignal() {
      return signal;
    }

    private ConditionType(String signal) {
      this.signal = signal;

    }
  }

  /**
   * Condition basic entity. Can be converted to a condition string when execute
   * a DB query.
   */
  public static class Entry {
    private final String fieldName;
    private final Object value;
    private final ConditionType conditionType;

    /**
     * Creates an {@link Entry} entity according to {@link Condition} entity.
     *
     * @param condition
     *          DB query condition.
     * @return an {@link Entry} entity for DB query.
     * @throws AppException
     * @see Condition
     */
    public static Entry of(Condition condition) throws AppException {
      AppExceptionUtil.assertNotNull(condition);
      return new Entry(null, ConditionType.CONDITION, condition);
    }

    /**
     * Creates an {@link Entry} entity according to query string.
     *
     * @param fieldName
     *          field name of DB table.
     * @param conditionType
     *          condition type for DB query, see {@link ConditionType} for more
     *          details.
     * @param value
     *          field value as expected.
     * @return an {@link Entry} entity for DB query.
     * @throws AppException
     * @see ConditionType
     */
    public static Entry of(String fieldName, ConditionType conditionType, Object value)
        throws AppException {
      if (conditionType == ConditionType.CONDITION) {
        AppExceptionUtil.assertTrue(value instanceof Condition);
      }
      return new Entry(fieldName, conditionType, value);
    }

    private Entry(String fieldName, ConditionType conditionType, Object value) {
      this.fieldName = fieldName;
      this.value = value;
      if (value instanceof String && conditionType == ConditionType.EQUALS) {
        conditionType = ConditionType.STRING_EQUALS;
      }
      this.conditionType = conditionType;
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

    @Override
    public boolean equals(Object obj) {
      if (obj == this) {
        return true;
      }
      if (!(obj instanceof Entry)) {
        return false;
      }
      Entry entry = (Entry) obj;
      return Objects.equals(fieldName, entry.fieldName)
          && Objects.equals(value, entry.value)
          && Objects.equals(conditionType, entry.conditionType);
    }
  }

  /**
   * DB query entries.
   */
  private final List<Entry> entries = new ArrayList<>();

  public List<Entry> getEntries() {
    return entries;
  }

  /**
   * DB query joined type.
   */
  private final JoinedType joinedType;

  /**
   * Order fields.
   */
  private final List<Order> orders = new ArrayList<>();

  /**
   * Default constructor.
   */
  public Condition() {
    this(JoinedType.AND);
  }

  /**
   * Create a DB query condition object with specific {@link JoinedType}.
   *
   * @param joinedType
   *          DB joined type for query.
   * @see JoinedType
   */
  public Condition(JoinedType joinedType) {
    this.joinedType = joinedType;
  }

  /**
   * Adds new order field into the order list.
   *
   * @param field
   *          new order field name
   * @param asc
   *          whether new order field asc or not
   * @return condition itself.
   * @throws AppException
   */
  public Condition addOrder(String field, boolean asc) throws AppException {
    orders.add(new Order(field, asc));
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
      return field + " " + (asc ? "asc" : "desc");
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

  private String escape(Object value) {
    String result = String.format("%s", value);
    result = result.replaceAll("'", "''");
    return result;
  }

  /**
   * Builds DB query string and append it into builder.
   *
   * @return if this condition's HQL is empty, return {@code false}.
   */
  private String buildHQLString() {
    String flag = " " + joinedType.getSignal() + " ";
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
        if (entry.getConditionType() == ConditionType.IS_NOT_NULL) {
          builder.append("(").append(entry.getFieldName()).append(" is not null)");
        } else if (entry.getConditionType() == ConditionType.IS_NULL) {
          builder.append("(").append(entry.getFieldName()).append(" is null)");
        } else {
          builder.append(entry.getFieldName());
          builder.append(entry.getConditionType().getSignal());
          if (entry.getConditionType() == ConditionType.LIKE) {
            builder.append("'%").append(escape(entry.getValue())).append("%'");
          } else if (entry.getConditionType() == ConditionType.IN) {
            builder.append("(").append(entry.getValue()).append(")");
          } else {
            builder.append("'").append(escape(entry.getValue())).append("'");
          }
        }
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

  private PageInfo pageInfo = null;

  public void setPageInfo(PageInfo pageInfo) {
    this.pageInfo = pageInfo;
  }

  public PageInfo getPageInfo() {
    return pageInfo;
  }

  public String getOrdersString() {
    StringBuilder builder = new StringBuilder();
    if (!orders.isEmpty()) {
      builder.append(" order by");
      boolean first = true;
      for (Order order : orders) {
        if (first) {
          builder.append(" ").append(order.toString());
          first = false;
        } else {
          builder.append(",").append(order.toString());
        }
      }
    }
    return builder.toString();
  }

  /**
   * Gets HQL string with order by clause.
   *
   * @return HQL string we need.
   */
  public String toHQLStringWithOrders() {
    StringBuilder builder = new StringBuilder();
    builder.append(toHQLString());
    builder.append(getOrdersString());
    return builder.toString();
  }

  public JoinedType getJoinedType() {
    return joinedType;
  }
}
