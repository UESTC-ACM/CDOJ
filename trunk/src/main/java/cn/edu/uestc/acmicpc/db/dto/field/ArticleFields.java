package cn.edu.uestc.acmicpc.db.dto.field;

/**
 * Field projection settings for ArticleDto.
 */
public enum ArticleFields implements Fields {
  ALL(FieldProjection.Property("articleId"),
      FieldProjection.Property("title"),
      FieldProjection.Property("content"),
      FieldProjection.Property("time"),
      FieldProjection.Property("clicked"),
      FieldProjection.Property("order"),
      FieldProjection.Property("type"),
      FieldProjection.Property("isVisible"),
      FieldProjection.Property("parentId"),
      FieldProjection.Property("problemId"),
      FieldProjection.Property("contestId"),
      FieldProjection.Property("userId"),
      FieldProjection.Alias("userByUserId", "owner"),
      FieldProjection.Property("owner.userName", "ownerName"),
      FieldProjection.Property("owner.email", "ownerEmail")),
  LIST(FieldProjection.Property("articleId"),
      FieldProjection.Property("title"),
      FieldProjection.Property("content"),
      FieldProjection.Property("time"),
      FieldProjection.Property("clicked"),
      FieldProjection.Property("isVisible"),
      FieldProjection.Alias("userByUserId", "owner"),
      FieldProjection.Property("owner.userName", "ownerName"),
      FieldProjection.Property("owner.email", "ownerEmail"));

  private final FieldProjection[] projections;

  public FieldProjection[] getProjections() {
    return projections;
  }

  ArticleFields(FieldProjection... projections) {
    this.projections = projections;
  }
}
