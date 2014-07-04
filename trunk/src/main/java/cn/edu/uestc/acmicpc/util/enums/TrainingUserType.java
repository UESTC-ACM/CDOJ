package cn.edu.uestc.acmicpc.util.enums;

/**
 * Type of training user
 */
public enum TrainingUserType {
  PERSONAL("Personal"), TEAM("team");

  private final String description;

  public String getDescription() {
    return description;
  }

  TrainingUserType(String description) {
    this.description = description;
  }
}
