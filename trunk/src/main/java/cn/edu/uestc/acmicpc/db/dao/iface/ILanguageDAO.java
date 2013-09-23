package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDTO;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * LanguageDAO AOP interface.
 */
public interface ILanguageDAO extends IDAO<Language, Integer, LanguageDTO> {

  @Override
  public LanguageDTO persist(LanguageDTO dto) throws AppException;
}
