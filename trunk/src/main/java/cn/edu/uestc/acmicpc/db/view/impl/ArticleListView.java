package cn.edu.uestc.acmicpc.db.view.impl;

import java.sql.Timestamp;

import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.db.view.base.View;

/**
 * Description
 */
public class ArticleListView extends View<Article> {

  private Integer articleId;
  private String title;
  private String author;
  private Integer clicked;
  private Timestamp time;
  private Boolean isVisible;
  private String ownerName;
  private String ownerEmail;

  public Boolean getVisible() {
    return isVisible;
  }

  public void setVisible(Boolean visible) {
    isVisible = visible;
  }

  public String getOwnerName() {
    return ownerName;
  }

  public void setOwnerName(String ownerName) {
    this.ownerName = ownerName;
  }

  public String getOwnerEmail() {
    return ownerEmail;
  }

  public void setOwnerEmail(String ownerEmail) {
    this.ownerEmail = ownerEmail;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    isVisible = visible;
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

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Integer getClicked() {
    return clicked;
  }

  public void setClicked(Integer clicked) {
    this.clicked = clicked;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  @Deprecated
  public ArticleListView(Article article) {
    super(article);
    // TODO(mzry1992): use dto transfer.
//    setOwnerName(article.getUserByUserId().getUserName());
//    setOwnerEmail(article.getUserByUserId().getEmail());
  }
}
