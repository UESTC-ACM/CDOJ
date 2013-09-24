package cn.edu.uestc.acmicpc.oj.service;

import java.io.Serializable;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.oj.service.iface.OnlineJudgeService;
import cn.edu.uestc.acmicpc.service.impl.AbstractService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Abstract implementation for {@link OnlineJudgeService}.
 *
 * @param <E> Entity type
 * @param <K> Key type
 * @param <C> Condition type
 */
public abstract class AbstractOnlineJudgeService<E extends Serializable, K extends Serializable, C extends BaseCondition>
    extends AbstractService implements OnlineJudgeService<E, K, C> {

  @Override
  public Condition getCondition(C entityCondition) throws AppException {
    Condition condition = new Condition();
    String orderFields = entityCondition.getOrderFields();
    String orderAsc = entityCondition.getOrderAsc();
    if (orderFields != null) {
      String[] fields = orderFields.split(",");
      String[] asc = orderAsc.split(",");
      if (fields.length == asc.length) {
        for (int i = 0; i < fields.length; i++) {
          condition.addOrder(fields[i], asc[i].equals("true"));
        }
      }
    }
    return condition;
  }
}
