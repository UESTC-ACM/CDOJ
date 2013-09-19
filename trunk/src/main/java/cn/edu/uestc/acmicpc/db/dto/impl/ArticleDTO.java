package cn.edu.uestc.acmicpc.db.dto.impl;

import java.sql.Timestamp;
import java.util.Date;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Data transfer object for {@link Article}.
 */
public class ArticleDTO extends BaseDTO<Article> {

  private Integer articleId;
  private String title;
  private String content;
  private String author;

  public ArticleDTO() {
  }

  private ArticleDTO(Integer articleId, String title, String content, String author) {
    this.articleId = articleId;
    this.title = title;
    this.content = content;
    this.author = author;
  }

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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  @Override
  public Article getEntity() throws AppException {
    Article article = super.getEntity();

    article.setClicked(0);
    article.setIsVisible(false);
    article.setIsNotice(false);
    article.setOrder(0);
    article.setArticleId(null);
    article.setTime(new Timestamp(new Date().getTime()));

    return article;
  }

  @Override
  public void updateEntity(Article article) throws AppException {
    super.updateEntity(article);
    article.setTime(new Timestamp(new Date().getTime()));
  }

  @Override
  protected Class<Article> getReferenceClass() {
    return Article.class;
  }

  public static Builder builder() {
    return new Builder();
  }

  /** Builder for {@link ArticleDTO}. */
  public static class Builder {

    private Builder() {
    }

    private Integer articleId;
    private String title = "";
    private String content = "";
    private String author = "";

    public Builder setArticleId(Integer articleId) {
      this.articleId = articleId;
      return this;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    public Builder setAuthor(String author) {
      this.author = author;
      return this;
    }

    public ArticleDTO build() {
      return new ArticleDTO(articleId, title, content, author);
    }
  }
}
