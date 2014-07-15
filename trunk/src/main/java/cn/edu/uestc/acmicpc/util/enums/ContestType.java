package cn.edu.uestc.acmicpc.util.enums;

/**
 * Contest type for contest entity
 */
public enum ContestType implements EnumType {
  PUBLIC("Public"), PRIVATE("Private"), DIY("DIY"), INVITED("Invited"),
  INHERIT("Inherit"), ONSITE("Onsite");

  private final String description;

  private ContestType(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
