package cn.edu.uestc.acmicpc.util.enums;

/**
 * Article type
 */
public enum ArticleType implements EnumType {
  NOTICE("Notice"), ARTICLE("Article"), COMMENT("Comment");
  private final String description;

  @Override
  public String getDescription() {
    return description;
  }

  ArticleType(String description) {
    this.description = description;
  }
}
