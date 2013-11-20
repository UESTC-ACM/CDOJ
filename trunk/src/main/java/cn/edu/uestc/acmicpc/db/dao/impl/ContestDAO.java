package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IContestDAO;
import cn.edu.uestc.acmicpc.db.entity.Contest;

import org.springframework.stereotype.Repository;

/**
 * DAO for contest entity.
 */
@Repository
public class ContestDAO extends DAO<Contest, Integer> implements IContestDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Contest> getReferenceClass() {
    return Contest.class;
  }
}
