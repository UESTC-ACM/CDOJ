package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDto;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class UserSerialKeyCriteria extends BaseCriteria<UserSerialKey, UserSerialKeyDto> {

  public UserSerialKeyCriteria() {
    super(UserSerialKey.class, UserSerialKeyDto.class);
  }

  public Integer userId;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (userId == null) {
      criteria.add(Restrictions.eq("userId", userId));
    }
    return criteria;
  }
}
