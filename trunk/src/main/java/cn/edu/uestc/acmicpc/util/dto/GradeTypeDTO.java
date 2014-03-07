package cn.edu.uestc.acmicpc.util.dto;

/**
 * Description
 */
public class GradeTypeDTO {
  private Integer gradeTypeId;
  private String description;

  public GradeTypeDTO(Integer gradeTypeId, String description) {
    this.gradeTypeId = gradeTypeId;
    this.description = description;
  }

  public Integer getGradeTypeId() {

    return gradeTypeId;
  }

  public void setGradeTypeId(Integer gradeTypeId) {
    this.gradeTypeId = gradeTypeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
