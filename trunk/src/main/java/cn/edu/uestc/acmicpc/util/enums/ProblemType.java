package cn.edu.uestc.acmicpc.util.enums;
public enum ProblemType implements EnumType {
  NORMAL("Normal problem"),
  INTERNAL("Internal problem");
  private final String description;
  private ProblemType(String description) {
    this.description = description;
  }
  @Override
  public String getDescription() {
    return description;
  }
}