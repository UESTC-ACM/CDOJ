package cn.edu.uestc.acmicpc.db.dto.impl;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.TrainingContest;

/**
 * Data transfer object for {@link TrainingContest}.
 */
public class TrainingContestDTO implements BaseDTO<TrainingContest> {

  private Integer trainingContestId;
  private Boolean isPersonal;
  private String title;
  private Integer type;

  public TrainingContestDTO() {
  }

  private TrainingContestDTO(Integer trainingContestId, Boolean isPersonal, String title,
      Integer type) {
    this.trainingContestId = trainingContestId;
    this.isPersonal = isPersonal;
    this.title = title;
    this.type = type;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getTrainingContestId() {
    return trainingContestId;
  }

  public void setTrainingContestId(Integer trainingContestId) {
    this.trainingContestId = trainingContestId;
  }

  public Boolean getIsPersonal() {
    return isPersonal;
  }

  public void setIsPersonal(Boolean personal) {
    isPersonal = personal;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link TrainingContestDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer trainingContestId;
    private Boolean isPersonal = false;
    private String title = "";
    private Integer type = 0;

    public Builder setTrainingContestId(Integer trainingContestId) {
      this.trainingContestId = trainingContestId;
      return this;
    }

    public Builder setIsPersonal(Boolean isPersonal) {
      this.isPersonal = isPersonal;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public TrainingContestDTO build() {
      return new TrainingContestDTO(trainingContestId, isPersonal, title, type);
    }
  }
}
