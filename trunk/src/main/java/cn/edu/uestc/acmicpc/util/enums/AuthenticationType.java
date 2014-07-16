package cn.edu.uestc.acmicpc.util.enums;

/**
 * User's authentication type(`type` column in user entity).
 */
public enum AuthenticationType implements EnumType {
  NORMAL("Normal user"), ADMIN("Administrator"), CONSTANT("Constant user");

  private final String description;

  private AuthenticationType(String description) {
    this.description = description;
  }

  @Override
  public String getDescription() {
    return description;
  }
}
