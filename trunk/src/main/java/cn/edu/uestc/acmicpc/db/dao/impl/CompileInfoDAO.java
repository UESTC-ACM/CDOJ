package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ICompileInfoDAO;
import cn.edu.uestc.acmicpc.db.entity.CompileInfo;

/**
 * DAO for compileinfo entity.
 */
@Repository
public class CompileInfoDAO extends DAO<CompileInfo, Integer> implements ICompileInfoDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<CompileInfo> getReferenceClass() {
    return CompileInfo.class;
  }
}
