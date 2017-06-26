package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDto;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Language database criteria entity.
 */
public class LanguageCriteria extends BaseCriteria<Language, LanguageDto> {

  public LanguageCriteria() {
    super(Language.class, LanguageDto.class);
  }

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    return criteria;
  }
}
