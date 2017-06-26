package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.CodeDto;
import cn.edu.uestc.acmicpc.db.entity.Code;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Code database criteria entity.
 */
public class CodeCriteria extends BaseCriteria<Code, CodeDto> {

  public CodeCriteria() {
    super(Code.class, CodeDto.class);
  }

  public Integer codeId;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (codeId != null) {
      criteria.add(Restrictions.eq("codeId", codeId));
    }
    return criteria;
  }
}
