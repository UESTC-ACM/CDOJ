package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestProblemDto;
import cn.edu.uestc.acmicpc.db.entity.ContestProblem;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ContestProblemCriteria extends BaseCriteria<ContestProblem, ContestProblemDto> {

  public ContestProblemCriteria() {
    super(ContestProblem.class, ContestProblemDto.class);
  }

  public Integer contestId;
  public Integer problemId;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (contestId != null) {
      criteria.add(Restrictions.eq("contestId", contestId));
    }
    if (problemId != null) {
      criteria.add(Restrictions.eq("problemId", problemId));
    }
    return criteria;
  }
}
