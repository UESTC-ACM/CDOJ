package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IProblemTagDAO;
import cn.edu.uestc.acmicpc.db.entity.ProblemTag;

/**
 * DAO for problemtag entity
 */
@Repository
public class ProblemTagDAO extends DAO<ProblemTag, Integer> implements IProblemTagDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<ProblemTag> getReferenceClass() {
    return ProblemTag.class;
  }
}
