package cn.edu.uestc.acmicpc.util.type;

/**
 * Contest type for contest entity
 */
public enum ContestType {
  PUBLIC("Public"), PRIVATE("Private"), DIY("DIY"), INVITED("Invited"),
  INHERIT("Inherit");

  private final String description;

  private ContestType(String description) {
    this.description = description;
  }

  /**
   * Get enumerate value's description.
   *
   * @return description string for specific contest type
   */
  public String getDescription() {
    return description;
  }
}
