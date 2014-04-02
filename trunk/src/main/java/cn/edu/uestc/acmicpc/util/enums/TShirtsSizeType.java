package cn.edu.uestc.acmicpc.util.enums;

/**
 * User's t-shirts size type
 */
public enum TShirtsSizeType {
  XS("XS"), S("S"), M("M"), L("L"), XL("XL"), XXL("XXL");

  private final String description;

  private TShirtsSizeType(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }
}
