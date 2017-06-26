package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.field.TrainingPlatformInfoFields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingPlatformInfoDto;
import cn.edu.uestc.acmicpc.db.entity.TrainingPlatformInfo;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * TrainingPlatform database criteria entity.
 */
public class TrainingPlatformInfoCriteria extends
    BaseCriteria<TrainingPlatformInfo, TrainingPlatformInfoDto> {

  public TrainingPlatformInfoCriteria() {
    super(TrainingPlatformInfo.class, TrainingPlatformInfoDto.class);
  }

  public Integer startId;
  public Integer endId;
  public Integer trainingUserId;
  public String userName;
  public String userId;
  public TrainingPlatformType type;
  public String keyword;
  public Integer trainingId;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (startId != null) {
      criteria.add(Restrictions.ge("trainingPlatformInfoId", startId));
      criteria.add(Restrictions.le("trainingPlatformInfoId", endId));
    }
    if (trainingUserId != null) {
      criteria.add(Restrictions.eq("trainingUserId", trainingUserId));
    }
    if (userName != null) {
      criteria.add(Restrictions.eq("userName", userName));
    }
    if (userId != null) {
      criteria.add(Restrictions.eq("userId", userId));
    }
    if (type != null) {
      criteria.add(Restrictions.eq("type", type.ordinal()));
    }
    if (keyword != null) {
      keyword = "%" + keyword + "%";
      criteria.add(Restrictions.or(
          Restrictions.ilike("userName", keyword),
          Restrictions.ilike("userId", keyword),
          Restrictions.ilike("trainingUser.trainingUserName", keyword)));
      addAlias(TrainingPlatformInfoFields.ALIAS_TRAINING_USER);
    }
    if (trainingId != null) {
      criteria.add(Restrictions.eq("trainingUser.trainingId", trainingId));
      addAlias(TrainingPlatformInfoFields.ALIAS_TRAINING_USER);
    }

    return criteria;
  }
}
