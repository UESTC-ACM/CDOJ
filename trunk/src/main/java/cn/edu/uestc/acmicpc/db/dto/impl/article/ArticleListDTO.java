package cn.edu.uestc.acmicpc.db.dto.impl.article;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.settings.Global;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO used in article list.
 * <br/>
 * <code>@Fields({ "articleId", "title", "clicked", "time", "isVisible", "userByUserId.userName",
 * "userByUserId.email" })</code>
 */
@Fields({"articleId", "title", "clicked", "time", "isVisible", "userByUserId.userName",
    "userByUserId.email", "content"})
public class ArticleListDTO implements BaseDTO<Article> {

  public ArticleListDTO() {
  }

  private ArticleListDTO(Integer articleId, String title,
                         Integer clicked, Timestamp time, Boolean isVisible, String ownerName,
                         String ownerEmail, String content, Boolean hasMore) {
    this.articleId = articleId;
    this.title = title;
    this.clicked = clicked;
    this.time = time;
    this.isVisible = isVisible;
    this.ownerName = ownerName;
    this.ownerEmail = ownerEmail;
    this.content = content;
    this.hasMore = hasMore;
  }

  private Integer articleId;
  private String title;
  private Integer clicked;
  private Timestamp time;
  private Boolean isVisible;
  private String ownerName;
  private String ownerEmail;
  private String content;
  private Boolean hasMore;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Boolean getHasMore() {
    return this.hasMore;
  }

  public void setHasMore(Boolean hasMore) {
    this.hasMore = hasMore;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ArticleListDTO that = (ArticleListDTO) o;

    if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) {
      return false;
    }
    if (content != null ? !content.equals(that.content) : that.content != null) {
      return false;
    }
    if (isVisible != null ? !isVisible.equals(that.isVisible) : that.isVisible != null) {
      return false;
    }
    if (ownerEmail != null ? !ownerEmail.equals(that.ownerEmail) : that.ownerEmail != null) {
      return false;
    }
    if (ownerName != null ? !ownerName.equals(that.ownerName) : that.ownerName != null) {
      return false;
    }
    if (time != null ? !time.equals(that.time) : that.time != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = articleId != null ? articleId.hashCode() : 0;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + (isVisible != null ? isVisible.hashCode() : 0);
    result = 31 * result + (ownerName != null ? ownerName.hashCode() : 0);
    result = 31 * result + (ownerEmail != null ? ownerEmail.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    return result;
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
          ownerName, ownerEmail, content, hasMore);
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
      content = (String) properties.get("content");

      // Get summary info
      int pos = content.indexOf(Global.ARTICLE_MORE_TAG);
      if (pos != -1) {
        content = content.substring(0, pos);
        hasMore = true;
      } else {
        hasMore = false;
      }

      return build();
    }

    private Integer articleId;
    private String title;
    private Integer clicked;
    private Timestamp time;
    private Boolean isVisible;
    private String ownerName;
    private String ownerEmail;
    private String content;
    private Boolean hasMore;

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

    public String getContent() {
      return content;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    public Boolean getHasMore() {
      return this.hasMore;
    }

    public Builder setHasMore(Boolean hasMore) {
      this.hasMore = hasMore;
      return this;
    }
  }
}
