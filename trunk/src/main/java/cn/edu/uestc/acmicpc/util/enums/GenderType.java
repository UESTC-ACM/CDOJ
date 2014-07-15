package cn.edu.uestc.acmicpc.util.enums;

/**
 * User's gender type
 */
public enum GenderType implements EnumType {
  MALE("Male"), FEMALE("Female");

  private final String description;

  private GenderType(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
