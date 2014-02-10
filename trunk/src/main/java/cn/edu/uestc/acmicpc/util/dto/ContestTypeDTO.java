package cn.edu.uestc.acmicpc.util.dto;

/**
 * DTO for {@link cn.edu.uestc.acmicpc.util.settings.Global.ContestType}
 */
public class ContestTypeDTO {
  private Integer contestTypeId;
  private String description;

  public ContestTypeDTO(Integer contestTypeId, String description) {
    this.contestTypeId = contestTypeId;
    this.description = description;
  }

  public Integer getContestTypeId() {
    return contestTypeId;
  }

  public void setContestTypeId(Integer contestTypeId) {
    this.contestTypeId = contestTypeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
