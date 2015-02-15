package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.UserSerialKeyDto;
import cn.edu.uestc.acmicpc.db.entity.UserSerialKey;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

public class UserSerialKeyCriteria extends BaseCriteria<UserSerialKey, UserSerialKeyDto> {

  public UserSerialKeyCriteria(Fields resultFields) {
    super(UserSerialKey.class, UserSerialKeyDto.class, resultFields);
  }

  public UserSerialKeyCriteria() {
    this(null);
  }

  public Integer userId;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria detachedCriteria = super.getCriteria();
    if (userId == null) {
      detachedCriteria.add(Restrictions.eq("userId", userId));
    }
    return detachedCriteria;
  }
}
