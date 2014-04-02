package cn.edu.uestc.acmicpc.util.enums;

/**
 * Article type
 */
public enum ArticleType {
  NOTICE("Notice"), ARTICLE("Article"), COMMENT("Comment");
  private final String description;

  public String getDescription() {
    return description;
  }

  ArticleType(String description) {
    this.description = description;
  }
}
