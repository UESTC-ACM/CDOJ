package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingUserDto;
import cn.edu.uestc.acmicpc.db.entity.TrainingUser;
import cn.edu.uestc.acmicpc.util.enums.TrainingUserType;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * TrainingUser database criteria entity.
 */
public class TrainingUserCriteria extends BaseCriteria<TrainingUser, TrainingUserDto> {

  public TrainingUserCriteria(Fields resultFields) {
    super(TrainingUser.class, TrainingUserDto.class, resultFields);
  }


  public TrainingUserCriteria() {
    this(null);
  }

  public Integer trainingId;
  public Integer startId;
  public Integer endId;
  public String keyword;
  public TrainingUserType type;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = super.getCriteria();

    if (trainingId != null) {
      criteria.add(Restrictions.eq("trainingId", trainingId));
    }
    if (startId != null) {
      criteria.add(Restrictions.ge("trainingUserId", startId));
    }
    if (endId != null) {
      criteria.add(Restrictions.le("trainingUserId", endId));
    }
    if (keyword != null) {
      keyword = "%" + keyword + "%";
      criteria.add(Restrictions.or(
          Restrictions.ilike("user.userName", keyword),
          Restrictions.ilike("trainingUserName", keyword)));
    }
    if (type != null) {
      criteria.add(Restrictions.eq("type", type));
    }
    return criteria;
  }
}
