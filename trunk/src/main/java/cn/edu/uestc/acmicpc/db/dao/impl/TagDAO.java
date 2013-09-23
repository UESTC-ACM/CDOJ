package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ITagDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.TagDTO;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for tag entity.
 */
@Repository
public class TagDAO extends DAO<Tag, Integer, TagDTO> implements ITagDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Tag> getReferenceClass() {
    return Tag.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public TagDTO persist(TagDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
