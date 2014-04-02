package cn.edu.uestc.acmicpc.util.enums;

/**
 * User's gender type
 */
public enum GenderType {
  MALE("Male"), FEMALE("Female");

  private final String description;

  private GenderType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
