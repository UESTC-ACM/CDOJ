package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingContestDto;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.util.enums.TrainingContestType;
import cn.edu.uestc.acmicpc.util.enums.TrainingPlatformType;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Training contest database criteria entity.
 */
public class TrainingContestCriteria extends BaseCriteria<TrainingContest, TrainingContestDto> {

  public TrainingContestCriteria(Fields resultFields) {
    super(TrainingContest.class, TrainingContestDto.class, resultFields);
  }

  public TrainingContestCriteria() {
    super(TrainingContest.class, TrainingContestDto.class);
  }

  @Override
  public void setResultFields(Fields resultFields) {
    super.setResultFields(resultFields);
  }

  public Integer startId;
  public Integer endId;
  public String keyword;
  public Integer trainingId;
  public TrainingContestType trainingContestType;
  public TrainingPlatformType trainingPlatformType;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = super.getCriteria();

    if (startId != null) {
      criteria.add(Restrictions.ge("trainingContestId", startId));
    }
    if (endId != null) {
      criteria.add(Restrictions.le("trainingContestId", endId));
    }
    if (keyword != null) {
      keyword = "%" + keyword + "%";
      criteria.add(Restrictions.or(
          Restrictions.ilike("title", keyword),
          Restrictions.ilike("link", keyword)
      ));
    }
    if (trainingId != null) {
      criteria.add(Restrictions.eq("trainingId", trainingId));
    }
    if (trainingContestType != null) {
      criteria.add(Restrictions.eq("type", trainingContestType.ordinal()));
    }
    if (trainingPlatformType != null) {
      criteria.add(Restrictions.eq("platformType", trainingPlatformType.ordinal()));
    }

    return criteria;
  }
}
