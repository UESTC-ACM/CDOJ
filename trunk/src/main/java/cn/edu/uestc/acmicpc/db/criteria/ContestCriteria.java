package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.entity.Contest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import java.sql.Timestamp;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;

public class ContestCriteria extends BaseCriteria<Contest, ContestDto> {

  public ContestCriteria() {
    super(Contest.class, ContestDto.class);
  }

  public Integer contestId;
  public Integer startId;
  public Integer endId;
  public Boolean isVisible;
  public Byte type;
  public Timestamp startTime;
  public Timestamp endTime;
  public String keyword;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (contestId != null) {
      criteria.add(Restrictions.eq("contestId", contestId));
    }
    if (startId != null) {
      criteria.add(Restrictions.ge("contestId", startId));
    }
    if (endId != null) {
      criteria.add(Restrictions.le("contestId", endId));
    }
    if (isVisible != null) {
      criteria.add(Restrictions.eq("isVisible", isVisible));
    }
    if (type != null) {
      criteria.add(Restrictions.eq("type", type));
    }
    if (startTime != null) {
      criteria.add(Restrictions.ge("time", startTime));
    }
    if (endTime != null) {
      criteria.add(Restrictions.le("time", endTime));
    }
    if (keyword != null) {
      keyword = String.format("%%%s%%", keyword);
      LogicalExpression criterion = Restrictions.or(
          Restrictions.ilike("title", keyword),
          Restrictions.ilike("description", keyword));
      try {
        Integer keywordNumber = Integer.parseInt(keyword);
        criterion = Restrictions.or(criterion, Restrictions.eq("contestId", keywordNumber));
      } catch (Exception ignored) {
        // Just ignore it.
      }
      criteria.add(criterion);
    }
    return criteria;
  }
}
