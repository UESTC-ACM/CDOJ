package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.ILanguageDAO;
import cn.edu.uestc.acmicpc.db.entity.Language;

import org.springframework.stereotype.Repository;

/**
 * DAO for language entity.
 */
@Repository
public class LanguageDAO extends DAO<Language, Integer> implements ILanguageDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Language> getReferenceClass() {
    return Language.class;
  }
}
