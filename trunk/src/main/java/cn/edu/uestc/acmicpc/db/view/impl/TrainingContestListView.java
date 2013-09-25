package cn.edu.uestc.acmicpc.db.view.impl;

import cn.edu.uestc.acmicpc.db.entity.TrainingContest;
import cn.edu.uestc.acmicpc.db.view.base.View;
import cn.edu.uestc.acmicpc.util.Global;

/**
 * Use for return training contest list view with json type.
 *
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
public class TrainingContestListView extends View<TrainingContest> {

  private Integer trainingContestId;
  private String title;
  private Boolean isPersonal;
  private Integer type;
  private String typeName;

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Boolean getIsPersonal() {
    return isPersonal;
  }

  public void setIsPersonal(Boolean personal) {
    isPersonal = personal;
  }

  @Deprecated
  public TrainingContestListView(TrainingContest trainingContest) {
    super(trainingContest);
    setTypeName(Global.TrainingContestType.values()[trainingContest.getType()].getDescription());
  }
}
