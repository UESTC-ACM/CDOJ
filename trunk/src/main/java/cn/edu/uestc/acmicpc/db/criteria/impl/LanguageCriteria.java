package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.LanguageDto;
import cn.edu.uestc.acmicpc.db.entity.Language;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Article database criteria entity.
 */
public class LanguageCriteria extends BaseCriteria<Language, LanguageDto> {

  public LanguageCriteria(Fields resultFields) {
    super(Language.class, LanguageDto.class, resultFields);
  }

  public LanguageCriteria() {
    this(null);
  }

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = super.getCriteria();
    return criteria;
  }
}
