/*
 *
 *  cdoj, UESTC ACMICPC Online Judge
 *  Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 *  	mzry1992 <@link muziriyun@gmail.com>
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *
 */

package cn.edu.uestc.acmicpc.db.entity;

import cn.edu.uestc.acmicpc.util.annotation.KeyField;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * Article information.
 * 
 * @author <a href="mailto:lyhypacm@gmail.com">fish</a>
 */
@Table(name = "article", schema = "", catalog = "uestcoj")
@Entity
@KeyField("articleId")
public class Article implements Serializable {

  private static final long serialVersionUID = 8886825769658373290L;
  private Integer articleId;

  private Integer version;

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

  private String author;

  @Column(name = "author", nullable = false, insertable = true, updatable = true, length = 50,
      precision = 0)
  @Basic
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
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

  private Integer clicked;

  @Column(name = "clicked", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getClicked() {
    return clicked;
  }

  public void setClicked(Integer clicked) {
    this.clicked = clicked;
  }

  private Integer order;

  @Column(name = "`order`", nullable = false, insertable = true, updatable = true, length = 10,
      precision = 0)
  @Basic
  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  private Boolean isNotice;

  @Column(name = "isNotice", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsNotice() {
    return isNotice;
  }

  public void setIsNotice(Boolean notice) {
    isNotice = notice;
  }

  private Boolean isVisible;

  @Column(name = "isVisible", nullable = false, insertable = true, updatable = true, length = 0,
      precision = 0)
  @Basic
  public Boolean getIsVisible() {
    return isVisible;
  }

  public void setIsVisible(Boolean visible) {
    this.isVisible = visible;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Article article = (Article) o;

    if (!articleId.equals(article.articleId)) {
      return false;
    }
    if (!clicked.equals(article.clicked)) {
      return false;
    }
    if (isNotice != article.isNotice) {
      return false;
    }
    if (!order.equals(article.order)) {
      return false;
    }
    if (isVisible != article.isVisible) {
      return false;
    }
    if (author != null ? !author.equals(article.author) : article.author != null) {
      return false;
    }
    if (content != null ? !content.equals(article.content) : article.content != null) {
      return false;
    }
    if (time != null ? !time.equals(article.time) : article.time != null) {
      return false;
    }
    if (title != null ? !title.equals(article.title) : article.title != null) {
      return false;
    }
    
    return true;
  }

  @Override
  public int hashCode() {
    int result = articleId;
    result = 31 * result + (title != null ? title.hashCode() : 0);
    result = 31 * result + (content != null ? content.hashCode() : 0);
    result = 31 * result + (author != null ? author.hashCode() : 0);
    result = 31 * result + (time != null ? time.hashCode() : 0);
    result = 31 * result + clicked;
    result = 31 * result + order;
    result = 31 * result + (isNotice ? 1 : 0);
    result = 31 * result + (isVisible ? 1 : 0);
    return result;
  }

  private User userByUserId;

  @ManyToOne
  @JoinColumn(name = "userId", referencedColumnName = "userId", nullable = true)
  public User getUserByUserId() {
    return userByUserId;
  }

  public void setUserByUserId(User userByUserId) {
    this.userByUserId = userByUserId;
  }

  private Article articleByParentId;

  @ManyToOne
  @JoinColumn(name = "parentId", referencedColumnName = "articleId", nullable = true)
  public Article getArticleByParentId() {
    return articleByParentId;
  }

  public void setArticleByParentId(Article articleByParentId) {
    this.articleByParentId = articleByParentId;
  }

  private Problem problemByProblemId;

  @ManyToOne
  @JoinColumn(name = "problemId", referencedColumnName = "problemId", nullable = true)
  public Problem getProblemByProblemId() {
    return problemByProblemId;
  }

  public void setProblemByProblemId(Problem problemByProblemId) {
    this.problemByProblemId = problemByProblemId;
  }

  private Contest contestByContestId;

  @ManyToOne
  @JoinColumn(name = "contestId", referencedColumnName = "contestId", nullable = true)
  public Contest getContestByContestId() {
    return contestByContestId;
  }

  public void setContestByContestId(Contest contestByContestId) {
    this.contestByContestId = contestByContestId;
  }

  private Collection<Article> articlesByParentId;

  @OneToMany(mappedBy = "articleByParentId")
  public Collection<Article> getArticlesByParentId() {
    return articlesByParentId;
  }

  public void setArticlesByParentId(Collection<Article> articlesByParentId) {
    this.articlesByParentId = articlesByParentId;
  }
}
