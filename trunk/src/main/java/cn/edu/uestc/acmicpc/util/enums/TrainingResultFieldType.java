package cn.edu.uestc.acmicpc.util.enums;

/**
 * Type of fields in training contest rank list.
 */
public enum TrainingResultFieldType implements EnumType {
  UNUSED("Unused"), USERNAME("User name"), PROBLEM("Problem"), PENALTY("Penalty"), SOLVED("Solved");

  private final String description;

  @Override
  public String getDescription() {
    return description;
  }

  TrainingResultFieldType(String description) {
    this.description = description;
  }
}