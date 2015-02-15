package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.db.entity.Training;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Training database criteria entity.
 */
public class TrainingCriteria extends BaseCriteria<Training, TrainingDto> {

  public TrainingCriteria(Fields resultFields) {
    super(Training.class, TrainingDto.class, resultFields);
  }

  public TrainingCriteria() {
    this(null);
  }

  public Integer startId;
  public Integer endId;
  public String keyword;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = super.getCriteria();

    if (startId != null) {
      criteria.add(Restrictions.ge("trainingId", startId));
    }
    if (endId != null) {
      criteria.add(Restrictions.le("trainingId", endId));
    }
    if (keyword != null) {
      keyword = "%" + keyword + "%";
      criteria.add(Restrictions.ilike("title", keyword));
    }

    return criteria;
  }
}
