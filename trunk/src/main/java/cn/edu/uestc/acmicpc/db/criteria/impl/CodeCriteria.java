package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import java.util.Set;

/**
 * Code database criteria entity.
 */
public class CodeCriteria extends BaseCriteria<Code, CodeDto> {

  public CodeCriteria(Set<Fields> resultFields) {
    super(Code.class, CodeDto.class, resultFields);
  }

  public CodeCriteria() {
    this(null);
  }

  public Integer codeId;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria detachedCriteria = super.getCriteria();
    if (codeId == null) {
      detachedCriteria.add(Restrictions.eq("codeId", codeId));
    }
    return detachedCriteria;
  }
}
