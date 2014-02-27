package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ICodeDAO;
import cn.edu.uestc.acmicpc.db.entity.Code;

import org.springframework.stereotype.Repository;

/**
 * DAO for code entity.
 */
@Repository
public class CodeDAO extends DAO<Code, Integer> implements ICodeDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Code> getReferenceClass() {
    return Code.class;
  }
}
