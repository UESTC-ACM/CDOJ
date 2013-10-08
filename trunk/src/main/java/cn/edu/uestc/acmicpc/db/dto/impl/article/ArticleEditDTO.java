package cn.edu.uestc.acmicpc.db.dto.impl.article;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;

public class ArticleEditDTO implements BaseDTO<Article> {

  public ArticleEditDTO() {
  }

  private ArticleEditDTO(Integer articleId, String action, String title, String content, String author) {
    this.articleId = articleId;
    this.action = action;
    this.title = title;
    this.content = content;
    this.author = author;
  }

  private Integer articleId;
  private String action;
  private String title;
  private String content;
  private String author;

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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public ArticleEditDTO build() {
      return new ArticleEditDTO(articleId, action, title, content, author);
    }

    private Integer articleId;
    private String action;
    private String title;
    private String content;
    private String author;

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

    public String getAuthor() {
      return author;
    }

    public Builder setAuthor(String author) {
      this.author = author;
      return this;
    }
  }
}
