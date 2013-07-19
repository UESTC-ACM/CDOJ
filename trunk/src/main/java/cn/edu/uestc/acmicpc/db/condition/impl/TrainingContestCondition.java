package cn.edu.uestc.acmicpc.db.condition.impl;

import cn.edu.uestc.acmicpc.db.condition.base.BaseCondition;
import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import org.hibernate.criterion.Restrictions;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class TrainingContestCondition extends BaseCondition {

  private Integer startId;
  private Integer endId;
  private String title;
  private Boolean isPersonal;
  private Integer type;

  @Exp(MapField = "type", Type = ConditionType.eq)
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Boolean isTitleEmpty;

  public Boolean getIsTitleEmpty() {
    return isTitleEmpty;
  }

  public void setIsTitleEmpty(Boolean isTitleEmpty) {
    this.isTitleEmpty = isTitleEmpty;
  }

  @Exp(MapField = "trainingContestId", Type = ConditionType.ge)
  public Integer getStartId() {
    return startId;
  }

  public void setStartId(Integer startId) {
    this.startId = startId;
  }

  @Exp(MapField = "trainingContestId", Type = ConditionType.le)
  public Integer getEndId() {
    return endId;
  }

  public void setEndId(Integer endId) {
    this.endId = endId;
  }

  @Exp(Type = ConditionType.like)
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Exp(Type = ConditionType.eq)
  public Boolean getIsPersonal() {
    return isPersonal;
  }

  public void setIsPersonal(Boolean personal) {
    isPersonal = personal;
  }

  @Override
  public void invoke(Condition condition) {
    super.invoke(condition);
    if (isTitleEmpty != null) {
      if (isTitleEmpty) {
        condition.addCriterion(Restrictions.like("title", ""));
      } else {
        condition.addCriterion(Restrictions.like("title", "_%"));
      }
    }
  }
}
