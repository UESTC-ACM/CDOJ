package cn.edu.uestc.acmicpc.util.dto;

/**
 * Description
 */
public class ContestRegistryStatusDTO {
  private Integer statusId;
  private String description;

  public ContestRegistryStatusDTO(Integer statusId, String description) {
    this.statusId = statusId;
    this.description = description;
  }

  public Integer getStatusId() {
    return statusId;
  }

  public void setStatusId(Integer statusId) {
    this.statusId = statusId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
