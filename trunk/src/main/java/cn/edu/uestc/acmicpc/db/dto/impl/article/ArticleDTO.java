package cn.edu.uestc.acmicpc.db.dto.impl.article;

import cn.edu.uestc.acmicpc.db.dto.base.BaseBuilder;
import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.annotation.Fields;
import cn.edu.uestc.acmicpc.util.settings.Global;

import java.sql.Timestamp;
import java.util.Map;

/**
 * DTO for article entity.
 * <br/>
 * <code>@Fields({ "articleId", "parentId", "clicked", "content", "contestId", "type",
 * "isVisible", "order", "problemId", "time", "title", "userId" })</code>
 */
@Fields({"articleId", "parentId", "clicked", "content", "contestId", "type",
    "isVisible", "order", "problemId", "time", "title", "userId"})
public class ArticleDTO implements BaseDTO<Article> {

  public ArticleDTO() {
  }

  private ArticleDTO(Integer articleId, Integer parentId, Integer clicked,
                     String content, Integer contestId, Integer type, Boolean isVisible, Integer order,
                     Integer problemId, Timestamp time, String title, Integer userId) {
    this.articleId = articleId;
    this.parentId = parentId;
    this.clicked = clicked;
    this.content = content;
    this.contestId = contestId;
    this.type = type;
    this.isVisible = isVisible;
    this.order = order;
    this.problemId = problemId;
    this.time = time;
    this.title = title;
    this.userId = userId;
  }

  private Integer articleId;
  private Integer parentId;
  private Integer clicked;
  private String content;
  private Integer contestId;
  private Integer type;
  private Boolean isVisible;
  private Integer order;
  private Integer problemId;
  private Timestamp time;
  private String title;
  private Integer userId;

  public Integer getArticleId() {
    return articleId;
  }

  public void setArticleId(Integer articleId) {
    this.articleId = articleId;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  public Integer getClicked() {
    return clicked;
  }

  public void setClicked(Integer clicked) {
    this.clicked = clicked;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean isVisible) {
    this.isVisible = isVisible;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder implements BaseBuilder<ArticleDTO> {

    private Builder() {
    }

    @Override
    public ArticleDTO build() {
      return new ArticleDTO(articleId, parentId, clicked, content, contestId,
          type, isVisible, order, problemId, time, title, userId);
    }

    @Override
    public ArticleDTO build(Map<String, Object> properties) {
      articleId = (Integer) properties.get("articleId");
      parentId = (Integer) properties.get("parentId");
      clicked = (Integer) properties.get("clicked");
      content = (String) properties.get("content");
      contestId = (Integer) properties.get("contestId");
      type = (Integer) properties.get("type");
      isVisible = (Boolean) properties.get("isVisible");
      order = (Integer) properties.get("order");
      problemId = (Integer) properties.get("problemId");
      time = (Timestamp) properties.get("time");
      title = (String) properties.get("title");
      userId = (Integer) properties.get("userId");

      // Remove more tag
      content = content.replace(Global.ARTICLE_MORE_TAG, "");

      return build();

    }

    private Integer articleId;
    private Integer parentId;
    private Integer clicked;
    private String content;
    private Integer contestId;
    private Integer type;
    private Boolean isVisible;
    private Integer order;
    private Integer problemId;
    private Timestamp time;
    private String title;
    private Integer userId;

    public Integer getArticleId() {
      return articleId;
    }

    public Builder setArticleId(Integer articleId) {
      this.articleId = articleId;
      return this;
    }

    public Integer getParentId() {
      return parentId;
    }

    public Builder setParentId(Integer parentId) {
      this.parentId = parentId;
      return this;
    }

    public Integer getClicked() {
      return clicked;
    }

    public Builder setClicked(Integer clicked) {
      this.clicked = clicked;
      return this;
    }

    public String getContent() {
      return content;
    }

    public Builder setContent(String content) {
      this.content = content;
      return this;
    }

    public Integer getContestId() {
      return contestId;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Integer getType() {
      return type;
    }

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public Boolean getIsVisible() {
      return isVisible;
    }

    public Builder setIsVisible(Boolean isVisible) {
      this.isVisible = isVisible;
      return this;
    }

    public Integer getOrder() {
      return order;
    }

    public Builder setOrder(Integer order) {
      this.order = order;
      return this;
    }

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Timestamp getTime() {
      return time;
    }

    public Builder setTime(Timestamp time) {
      this.time = time;
      return this;
    }

    public String getTitle() {
      return title;
    }

    public Builder setTitle(String title) {
      this.title = title;
      return this;
    }

    public Integer getUserId() {
      return userId;
    }

    public Builder setUserId(Integer userId) {
      this.userId = userId;
      return this;
    }
  }
}
