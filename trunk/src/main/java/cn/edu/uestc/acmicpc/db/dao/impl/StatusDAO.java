package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IStatusDAO;
import cn.edu.uestc.acmicpc.db.entity.Status;

import org.springframework.stereotype.Repository;

/**
 * DAO for status.
 */
@Repository
public class StatusDAO extends DAO<Status, Integer> implements IStatusDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Status> getReferenceClass() {
    return Status.class;
  }
}
