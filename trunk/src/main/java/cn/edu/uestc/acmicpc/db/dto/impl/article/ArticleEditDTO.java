package cn.edu.uestc.acmicpc.db.dto.impl.article;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;

/**
 * DTO post from article editor.
 */
public class ArticleEditDTO implements BaseDTO<Article> {

  public ArticleEditDTO() {
  }

  private ArticleEditDTO(Integer articleId, String action, String title, String content) {
    this.articleId = articleId;
    this.action = action;
    this.title = title;
    this.content = content;
  }

  private Integer articleId;
  private String action;
  private String title;
  private String content;

  public Integer getArticleId() {
    return articleId;
  }

  public void setArticleId(Integer articleId) {
    this.articleId = articleId;
  }

  public String getAction() {
    return action;
  }

  public void setAction(String action) {
    this.action = action;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ArticleEditDTO that = (ArticleEditDTO) o;

    if (action != null ? !action.equals(that.action) : that.action != null) return false;
    if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null)
      return false;
    if (content != null ? !content.equals(that.content) : that.content != null) return false;
    if (title != null ? !title.equals(that.title) : that.title != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = articleId != null ? articleId.hashCode() : 0;
    result = 31 * result + (action != null ? action.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public ArticleEditDTO build() {
      return new ArticleEditDTO(articleId, action, title, content);
    }

    private Integer articleId;
    private String action;
    private String title;
    private String content;

    public Integer getArticleId() {
      return articleId;
    }

    public Builder setArticleId(Integer articleId) {
      this.articleId = articleId;
      return this;
    }

    public String getAction() {
      return action;
    }

    public Builder setAction(String action) {
      this.action = action;
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
