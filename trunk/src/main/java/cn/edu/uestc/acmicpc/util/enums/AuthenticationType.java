package cn.edu.uestc.acmicpc.util.enums;

/**
 * User's authentication type(`type` column in user entity).
 */
public enum AuthenticationType {
  NORMAL("Normal user"), ADMIN("Administrator"), CONSTANT("Constant user");

  private final String description;

  private AuthenticationType(String description) {
    this.description = description;
  }

  /**
   * Get enumerate value's description.
   *
   * @return description string for specific authentication type
   */
  public String getDescription() {
    return description;
  }
}
