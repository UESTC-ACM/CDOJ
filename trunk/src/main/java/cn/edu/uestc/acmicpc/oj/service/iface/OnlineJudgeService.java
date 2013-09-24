package cn.edu.uestc.acmicpc.oj.service.iface;

import java.io.Serializable;

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;

/**
 * Online judge global service.
 *
 * @param <E> Entity type
 * @param <K> Key type
 */
public interface OnlineJudgeService<E extends Serializable, K extends Serializable> {

  /**
   * Get entity dao for query.
   *
   * @return enitty dao for query.
   */
  IDAO<E, K> getDAO();
}
