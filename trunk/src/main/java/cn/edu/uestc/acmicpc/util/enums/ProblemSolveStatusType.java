package cn.edu.uestc.acmicpc.util.enums;

/**
 * Problem status for author problem flag.
 */
public enum ProblemSolveStatusType implements EnumType {
  NONE("Not tried"), PASS("Passed"), FAIL("Tried but failed"), FB("First blood");

  private final String description;

  @Override
  public String getDescription() {
    return this.description;
  }

  private ProblemSolveStatusType(String description) {
    this.description = description;
  }
}
