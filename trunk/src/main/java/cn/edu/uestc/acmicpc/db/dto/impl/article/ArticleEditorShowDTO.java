package cn.edu.uestc.acmicpc.db.dto.impl.article;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

import java.util.Map;

/**
 * DTO used in article editor view.
 * <br/>
 * <code>@Fields({ "articleId", "title", "content"})</code>
 */
@Fields({"articleId", "title", "content"})
public class ArticleEditorShowDTO implements BaseDTO<Article> {

  public ArticleEditorShowDTO() {
  }

  private ArticleEditorShowDTO(Integer articleId, String title, String content) {
    this.articleId = articleId;
    this.title = title;
    this.content = content;
  }

  private Integer articleId;
  private String title;
  private String content;

  public Integer getArticleId() {
    return articleId;
  }

  public void setArticleId(Integer articleId) {
    this.articleId = articleId;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ArticleEditorShowDTO> {

    private Builder() {
    }

    @Override
    public ArticleEditorShowDTO build() {
      return new ArticleEditorShowDTO(articleId, title, content);
    }

    @Override
    public ArticleEditorShowDTO build(Map<String, Object> properties) {
      articleId = (Integer) properties.get("articleId");
      title = (String) properties.get("title");
      content = (String) properties.get("content");
      return build();

    }

    private Integer articleId;
    private String title;
    private String content;

    public Integer getArticleId() {
      return articleId;
    }

    public Builder setArticleId(Integer articleId) {
      this.articleId = articleId;
      return this;
    }

    public String getTitle() {
      return title;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public String getContent() {
      return content;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

  }
}
