package cn.edu.uestc.acmicpc.db.dto.impl.article;

import java.sql.Timestamp;
import java.util.Map;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.annotation.Fields;

/**
 * DTO used in article list.
 * <br/>
 * <code>@Fields({ "articleId", "title", "clicked", "time", "isVisible", "userByUserId.userName",
 * "userByUserId.email" })</code>
 */
@Fields({ "articleId", "title", "clicked", "time", "isVisible", "userByUserId.userName",
  "userByUserId.email" })
public class ArticleListDTO implements BaseDTO<Article> {

  public ArticleListDTO() {
  }

  private ArticleListDTO(Integer articleId, String title,
      Integer clicked, Timestamp time, Boolean isVisible, String ownerName,
      String ownerEmail) {
    this.articleId = articleId;
    this.title = title;
    this.clicked = clicked;
    this.time = time;
    this.isVisible = isVisible;
    this.ownerName = ownerName;
    this.ownerEmail = ownerEmail;
  }

  private Integer articleId;
  private String title;
  private Integer clicked;
  private Timestamp time;
  private Boolean isVisible;
  private String ownerName;
  private String ownerEmail;

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

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
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

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ArticleListDTO> {

    private Builder() {
    }

    @Override
    public ArticleListDTO build() {
      return new ArticleListDTO(articleId, title, clicked, time, isVisible,
          ownerName, ownerEmail);
    }

    @Override
    public ArticleListDTO build(Map<String, Object> properties) {
      articleId = (Integer) properties.get("articleId");
      title = (String) properties.get("title");
      clicked = (Integer) properties.get("clicked");
      time = (Timestamp) properties.get("time");
      isVisible = (Boolean) properties.get("isVisible");
      ownerName = (String) properties.get("userByUserId.userName");
      ownerEmail = (String) properties.get("userByUserId.email");
      return build();

    }

    private Integer articleId;
    private String title;
    private Integer clicked;
    private Timestamp time;
    private Boolean isVisible;
    private String ownerName;
    private String ownerEmail;

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

    public Integer getClicked() {
      return clicked;
    }

    public Builder setClicked(Integer clicked) {
      this.clicked = clicked;
      return this;
    }

    public Timestamp getTime() {
      return time;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public Boolean getIsVisible() {
      return isVisible;
    }

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }

    public String getOwnerName() {
      return ownerName;
    }

    public Builder setOwnerName(String ownerName) {
      this.ownerName = ownerName;
      return this;
    }

    public String getOwnerEmail() {
      return ownerEmail;
    }

    public Builder setOwnerEmail(String ownerEmail) {
      this.ownerEmail = ownerEmail;
      return this;
    }
  }
}
