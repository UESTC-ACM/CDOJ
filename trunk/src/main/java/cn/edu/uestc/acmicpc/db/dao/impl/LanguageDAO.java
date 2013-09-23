package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDTO;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for language entity.
 */
@Repository
public class LanguageDAO extends DAO<Language, Integer, LanguageDTO> implements ILanguageDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Language> getReferenceClass() {
    return Language.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public LanguageDTO persist(LanguageDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
