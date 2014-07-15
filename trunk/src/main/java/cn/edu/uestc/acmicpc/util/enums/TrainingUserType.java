package cn.edu.uestc.acmicpc.util.enums;

/**
 * Type of training user
 */
public enum TrainingUserType implements EnumType {
  PERSONAL("Personal"), TEAM("team");

  private final String description;

  @Override
  public String getDescription() {
    return description;
  }

  TrainingUserType(String description) {
    this.description = description;
  }
}
