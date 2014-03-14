package cn.edu.uestc.acmicpc.util.dto;

/**
 * Description
 */
public class GenderTypeDTO {
  private Integer genderTypeId;
  private String description;

  public GenderTypeDTO(Integer genderTypeId, String description) {
    this.genderTypeId = genderTypeId;
    this.description = description;
  }

  public Integer getGenderTypeId() {
    return genderTypeId;
  }

  public void setGenderTypeId(Integer genderTypeId) {
    this.genderTypeId = genderTypeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
