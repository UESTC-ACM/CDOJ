package cn.edu.uestc.acmicpc.util.dto;

/**
 * Description
 */
public class OnlineJudgeResultTypeDTO {
  private Integer onlineJudgeResultTypeId;
  private String description;

  public OnlineJudgeResultTypeDTO(Integer onlineJudgeResultTypeId, String description) {
    this.onlineJudgeResultTypeId = onlineJudgeResultTypeId;
    this.description = description;
  }

  public Integer getOnlineJudgeResultTypeId() {
    return onlineJudgeResultTypeId;
  }

  public void setOnlineJudgeResultTypeId(Integer onlineJudgeResultTypeId) {
    this.onlineJudgeResultTypeId = onlineJudgeResultTypeId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
