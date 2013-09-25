package cn.edu.uestc.acmicpc.db.condition.base;

import org.hibernate.criterion.Criterion;

import cn.edu.uestc.acmicpc.db.condition.base.Condition.ConditionType;
import cn.edu.uestc.acmicpc.db.condition.base.Condition.Entry;

/**
 * Entity for joined properties of database update.
 *
 * @deprecated this class is not supported in new API, please use {@link Entry}.
 */
@Deprecated
public class JoinedProperty {

  private Criterion criterion;
  private Object keyValue;
  private ConditionType conditionType;

  public JoinedProperty(Criterion criterion, Object keyValue, ConditionType conditionType) {
    this.criterion = criterion;
    this.keyValue = keyValue;
    this.conditionType = conditionType;
  }

  public ConditionType getConditionType() {
    return conditionType;
  }

  public void setConditionType(ConditionType conditionType) {
    this.conditionType = conditionType;
  }

  public Criterion getCriterion() {
    return criterion;
  }

  public void setCriterion(Criterion criterion) {
    this.criterion = criterion;
  }

  public Object getKeyValue() {
    return keyValue;
  }

  public void setKeyValue(Object keyValue) {
    this.keyValue = keyValue;
  }
}
