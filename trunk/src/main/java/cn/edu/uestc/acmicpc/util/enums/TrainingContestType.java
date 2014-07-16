package cn.edu.uestc.acmicpc.util.enums;

/**
 * Type of training contest.
 */
public enum TrainingContestType implements EnumType {
  CONTEST("Contest"), ADJUST("Rating adjust");

  private final String description;

  @Override
  public String getDescription() {
    return description;
  }

  private TrainingContestType(String description) {
    this.description = description;
  }
}
