package cn.edu.uestc.acmicpc.util.dto;

/**
 * Description
 */
public class TrainingUserTypeDto {

  private Integer trainingUserTypeId;
  private String description;

  public Integer getTrainingUserTypeId() {
    return trainingUserTypeId;
  }

  public void setTrainingUserTypeId(Integer trainingUserTypeId) {
    this.trainingUserTypeId = trainingUserTypeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public TrainingUserTypeDto(Integer trainingUserTypeId, String description) {

    this.trainingUserTypeId = trainingUserTypeId;
    this.description = description;
  }
}
