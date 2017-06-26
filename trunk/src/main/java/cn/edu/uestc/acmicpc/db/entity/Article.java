package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * Article information.
 */
@Table(name = "article")
@Entity
@KeyField("articleId")
public class Article implements Serializable {

  private static final long serialVersionUID = 8886825769658373290L;
  private Integer articleId;

  private Integer version = 0;

  @Version
  @Column(name = "OPTLOCK")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "articleId", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0, unique = true)
  @Id
  @GeneratedValue
  public Integer getArticleId() {
    return articleId;
  }

  public void setArticleId(Integer articleId) {
    this.articleId = articleId;
  }

  private String title;

  @Column(name = "title", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  private String content;

  @Column(name = "content", nullable = false, insertable = true, updatable = true, length = 65535,
      precision = 0)
  @Basic
  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  private Timestamp time;

  @Column(name = "time", nullable = false, insertable = true, updatable = true, length = 19,
      precision = 0)
  @Basic
  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  private Integer clicked = 0;

  @Column(name = "clicked", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getClicked() {
    return clicked;
  }

  public void setClicked(Integer clicked) {
    this.clicked = clicked;
  }

  private Integer order = 0;

  @Column(name = "`order`", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  private Integer type = 0;

  @Column(name = "type", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  private Boolean isVisible = false;

  @Column(name = "isVisible", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    this.isVisible = visible;
  }

  private Integer userId;

  @Column(name = "userId", nullable = true, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  private Integer parentId;

  @Column(name = "parentId", nullable = true, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getParentId() {
    return parentId;
  }

  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  private Integer problemId;

  @Column(name = "problemId", nullable = true, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getProblemId() {
    return problemId;
  }

  public void setProblemId(Integer problemId) {
    this.problemId = problemId;
  }

  private Integer contestId;

  @Column(name = "contestId", nullable = true, insertable = true, updatable = true, length = 10,
      precision = 0)
  public Integer getContestId() {
    return contestId;
  }

  public void setContestId(Integer contestId) {
    this.contestId = contestId;
  }

  private User userByUserId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = true,
      insertable = false, updatable = false)
  public User getUserByUserId() {
    return userByUserId;
  }

  public void setUserByUserId(User userByUserId) {
    this.userByUserId = userByUserId;
  }

  private Article articleByParentId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "parentId", referencedColumnName = "articleId", nullable = true,
      insertable = false, updatable = false)
  public Article getArticleByParentId() {
    return articleByParentId;
  }

  public void setArticleByParentId(Article articleByParentId) {
    this.articleByParentId = articleByParentId;
  }

  private Problem problemByProblemId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "problemId", referencedColumnName = "problemId", nullable = true,
      insertable = false, updatable = false)
  public Problem getProblemByProblemId() {
    return problemByProblemId;
  }

  public void setProblemByProblemId(Problem problemByProblemId) {
    this.problemByProblemId = problemByProblemId;
  }

  private Contest contestByContestId;

  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "contestId", referencedColumnName = "contestId", nullable = true,
      insertable = false, updatable = false)
  public Contest getContestByContestId() {
    return contestByContestId;
  }

  public void setContestByContestId(Contest contestByContestId) {
    this.contestByContestId = contestByContestId;
  }

  private Collection<Article> articlesByParentId;

  @OneToMany(mappedBy = "articleByParentId", cascade = CascadeType.ALL)
  public Collection<Article> getArticlesByParentId() {
    return articlesByParentId;
  }

  public void setArticlesByParentId(Collection<Article> articlesByParentId) {
    this.articlesByParentId = articlesByParentId;
  }
}
