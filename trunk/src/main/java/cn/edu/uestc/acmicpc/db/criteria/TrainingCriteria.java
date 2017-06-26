package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.TrainingDto;
import cn.edu.uestc.acmicpc.db.entity.Training;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Training database criteria entity.
 */
public class TrainingCriteria extends BaseCriteria<Training, TrainingDto> {

  public TrainingCriteria() {
    super(Training.class, TrainingDto.class);
  }

  public Integer startId;
  public Integer endId;
  public String keyword;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
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
