package cn.edu.uestc.acmicpc.util.enums;

/**
 * Type of fields in training contest rank list.
 */
public enum TrainingResultFieldType implements EnumType {
  UNUSED("Unused"), USERNAME("User name"), PROBLEM("Problem"),
  PENALTY("Penalty"), SOLVED("Solved"), SCORE("Score"), SUCCESSFUL_HACK("Successful hack"),
  UNSUCCESSFUL_HACK("Unsuccessful hack"), TYPE("Type"), DEDUCT("Deduct rating");

  private final String description;

  @Override
  public String getDescription() {
    return description;
  }

  TrainingResultFieldType(String description) {
    this.description = description;
  }
}