package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dao.iface.IDAO;

import java.io.Serializable;

/**
 * Online judge global service.
 *
 * @param <E> Entity type
 * @param <K> Key type
 */
public interface DatabaseService<E extends Serializable, K extends Serializable> {

  /**
   * Get entity dao for query.
   *
   * @return enitty dao for query.
   */
  IDAO<E, K> getDAO();
}
