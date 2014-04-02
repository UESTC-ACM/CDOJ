package cn.edu.uestc.acmicpc.util.enums;

/**
 * Contest register request status type
 */
public enum ContestRegistryStatusType {
  PENDING("Pending"), ACCEPTED("Accepted"), REFUSED("Refused");

  private final String description;

  private ContestRegistryStatusType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
