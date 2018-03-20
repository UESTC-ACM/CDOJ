package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestUserDto;
import cn.edu.uestc.acmicpc.db.entity.ContestUser;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class ContestUserCriteria extends BaseCriteria<ContestUser, ContestUserDto> {

  public ContestUserCriteria() {
    super(ContestUser.class, ContestUserDto.class);
  }

  public Integer contestId;
  public Integer userId;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (contestId != null) {
      criteria.add(Restrictions.eq("contestId", contestId));
    }
    if (userId != null) {
      criteria.add(Restrictions.eq("userId", userId));
    }
    return criteria;
  }
}
