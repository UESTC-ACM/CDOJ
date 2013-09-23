package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.TagDTO;
import cn.edu.uestc.acmicpc.db.entity.Tag;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * TagDAO AOP interface.
 */
public interface ITagDAO extends IDAO<Tag, Integer, TagDTO> {

  @Override
  public TagDTO persist(TagDTO dto) throws AppException;
}
