package cn.edu.uestc.acmicpc.oj.service.iface;

import java.io.Serializable;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Online judge global service.
 *
 * @param <E> Entity type
 * @param <K> Key type
 * @param <C> Condition type
 */
public interface OnlineJudgeService<E extends Serializable, K extends Serializable, C extends BaseCondition> {

  /**
   * Get entity dao for query.
   *
   * @return enitty dao for query.
   */
  IDAO<E, K> getDAO();

  /**
   * Create {@link Condition} entity from {@link BaseCondition} entity.
   *
   * @param condition entity's condition.
   * @return {@link Condition} entity for DB query.
   * @throws AppException
   */
  Condition getCondition(C condition) throws AppException;
}
