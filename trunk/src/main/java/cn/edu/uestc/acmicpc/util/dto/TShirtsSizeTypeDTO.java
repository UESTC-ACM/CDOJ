package cn.edu.uestc.acmicpc.util.dto;

/**
 * Description
 */
public class TShirtsSizeTypeDTO {
  private Integer sizeTypeId;
  private String description;

  public TShirtsSizeTypeDTO(Integer sizeTypeId, String description) {
    this.sizeTypeId = sizeTypeId;
    this.description = description;
  }

  public Integer getSizeTypeId() {

    return sizeTypeId;
  }

  public void setSizeTypeId(Integer sizeTypeId) {
    this.sizeTypeId = sizeTypeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
