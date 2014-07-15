package cn.edu.uestc.acmicpc.util.enums;

/**
 * Problem status for author problem flag.
 */
public enum AuthorStatusType implements EnumType {
  NONE("Not tried"), PASS("Passed"), FAIL("Tried but failed");

  private final String description;

  @Override
  public String getDescription() {
    return this.description;
  }

  private AuthorStatusType(String description) {
    this.description = description;
  }
}
