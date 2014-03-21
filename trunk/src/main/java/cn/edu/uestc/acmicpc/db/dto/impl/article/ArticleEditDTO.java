package cn.edu.uestc.acmicpc.db.dto.impl.article;

import cn.edu.uestc.acmicpc.db.dto.base.BaseDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;

/**
 * DTO post from article editor.
 */
public class ArticleEditDTO implements BaseDTO<Article> {

  public ArticleEditDTO() {
  }

  public ArticleEditDTO(Integer articleId, String action, String title, String content,
                        String userName, Integer type, Integer problemId, Integer contestId,
                        Integer parentId) {
    this.articleId = articleId;
    this.action = action;
    this.title = title;
    this.content = content;
    this.userName = userName;
    this.type = type;
    this.problemId = problemId;
    this.contestId = contestId;
    this.parentId = parentId;
  }

  private Integer articleId;
  private String action;
  private String title;
  private String content;
  private String userName;
  private Integer type;
  private Integer problemId;
  private Integer contestId;
  private Integer parentId;

  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

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

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ArticleEditDTO that = (ArticleEditDTO) o;

    if (action != null ? !action.equals(that.action) : that.action != null) {
      return false;
    }
    if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) {
      return false;
    }
    if (content != null ? !content.equals(that.content) : that.content != null) {
      return false;
    }
    if (title != null ? !title.equals(that.title) : that.title != null) {
      return false;
    }
    if (userName != null ? !userName.equals(that.userName) : that.userName != null) {
      return false;
    }
    if (type != null ? !type.equals(that.type) : that.type != null) {
      return false;
    }
    if (problemId != null ? !problemId.equals(that.problemId) : that.problemId != null) {
      return false;
    }
    if (contestId != null ? !contestId.equals(that.contestId) : that.contestId != null) {
      return false;
    }
    if (articleId != null ? !articleId.equals(that.articleId) : that.articleId != null) {
      return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    int result = articleId != null ? articleId.hashCode() : 0;
    result = 31 * result + (action != null ? action.hashCode() : 0);
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    result = 31 * result + (userName != null ? userName.hashCode() : 0);
    result = 31 * result + (type != null ? type.hashCode() : 0);
    result = 31 * result + (problemId != null ? problemId.hashCode() : 0);
    result = 31 * result + (contestId != null ? contestId.hashCode() : 0);
    result = 31 * result + (articleId != null ? articleId.hashCode() : 0);
    return result;
  }

  public static Builder builder() {
    return new Builder();
  }

  public static class Builder {

    private Builder() {
    }

    public ArticleEditDTO build() {
      return new ArticleEditDTO(articleId, action, title, content, userName, type, problemId,
          contestId, parentId);
    }

    private Integer articleId;
    private String action;
    private String title;
    private String content;
    private String userName;
    private Integer type;
    private Integer problemId;
    private Integer contestId;
    private Integer parentId;

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

    public String getUserName() {
      return userName;
    }

    public Builder setUserName(String userName) {
      this.userName = userName;
      return this;
    }

    public Integer getType() {
      return type;
    }

    public Builder setType(Integer type) {
      this.type = type;
      return this;
    }

    public Integer getProblemId() {
      return problemId;
    }

    public Builder setProblemId(Integer problemId) {
      this.problemId = problemId;
      return this;
    }

    public Integer getContestId() {
      return contestId;
    }

    public Builder setContestId(Integer contestId) {
      this.contestId = contestId;
      return this;
    }

    public Integer getParentId() {
      return parentId;
    }

    public Builder setParentId(Integer parentId) {
      this.parentId = parentId;
      return this;
    }
  }
}
