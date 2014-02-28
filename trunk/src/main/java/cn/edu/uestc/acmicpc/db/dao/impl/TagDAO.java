package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITagDAO;
import cn.edu.uestc.acmicpc.db.entity.Tag;

import org.springframework.stereotype.Repository;

/**
 * DAO for tag entity.
 */
@Repository
public class TagDAO extends DAO<Tag, Integer> implements ITagDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Tag> getReferenceClass() {
    return Tag.class;
  }
}
