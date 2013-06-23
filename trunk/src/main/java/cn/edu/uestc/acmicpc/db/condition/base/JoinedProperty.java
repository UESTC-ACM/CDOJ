package cn.edu.uestc.acmicpc.db.condition.base;

import org.hibernate.criterion.Criterion;

import static cn.edu.uestc.acmicpc.db.condition.base.BaseCondition.ConditionType;

/**
 * Entity for joined properties of database update.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@SuppressWarnings("UnusedDeclaration")
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
