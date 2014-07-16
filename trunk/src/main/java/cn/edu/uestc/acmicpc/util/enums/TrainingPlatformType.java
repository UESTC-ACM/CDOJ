package cn.edu.uestc.acmicpc.util.enums;

/**
 * Type of training platform
 */
public enum TrainingPlatformType implements EnumType {
  TC("TopCoder"), CF("Codeforces"), HDOJ("HDU Online Judge"),
  CDOJ("UESTC Online Judge"), VJ("Virtual Judge");

  private final String description;

  @Override
  public String getDescription() {
    return description;
  }

  TrainingPlatformType(String description) {
    this.description = description;
  }
}
