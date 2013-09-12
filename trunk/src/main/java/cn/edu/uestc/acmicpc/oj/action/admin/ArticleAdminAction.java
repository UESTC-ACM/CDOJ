/*
 * cdoj, UESTC ACMICPC Online Judge
 *
 * Copyright (c) 2013 fish <@link lyhypacm@gmail.com>,
 * mzry1992 <@link muziriyun@gmail.com>
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package cn.edu.uestc.acmicpc.oj.action.admin;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.db.view.impl.ArticleListView;
import cn.edu.uestc.acmicpc.db.view.impl.ArticleView;
import cn.edu.uestc.acmicpc.ioc.condition.ArticleConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ArticleDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.ArticleDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.oj.view.PageInfo;
import cn.edu.uestc.acmicpc.util.ArrayUtil;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.ReflectionUtil;
import cn.edu.uestc.acmicpc.util.StringUtil;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * action for list, search, edit, add article.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class ArticleAdminAction extends BaseAction implements ArticleDAOAware, ArticleDTOAware,
    ArticleConditionAware {

  private static final Logger LOGGER = LogManager.getLogger(ArticleAdminAction.class);
  /**
	 *
	 */
  private static final long serialVersionUID = 499943521076814016L;
  @Autowired
  private IArticleDAO articleDAO;

  @Override
  public void setArticleDAO(IArticleDAO articleDAO) {
    this.articleDAO = articleDAO;
  }

  private Integer targetArticleId;

  public Integer getTargetArticleId() {
    return targetArticleId;
  }

  public void setTargetArticleId(Integer targetArticleId) {
    this.targetArticleId = targetArticleId;
  }

  private ArticleView targetArticle;

  public ArticleView getTargetArticle() {
    return targetArticle;
  }

  public void setTargetArticle(ArticleView targetArticle) {
    this.targetArticle = targetArticle;
  }

  /**
   * Go to article editor view!
   *
   * @return <strong>SUCCESS</strong> signal
   */
  @SuppressWarnings("unchecked")
  public String toArticleEditor() {
    try {
      if (targetArticleId == null) {
        articleCondition.clear();
        articleCondition.setIsTitleEmpty(true);
        Condition condition = articleCondition.getCondition();
        Long count = articleDAO.count(condition);

        if (count == 0) {
          Article article = articleDTO.getEntity();
          articleDAO.add(article);
          targetArticleId = article.getArticleId();
        } else {
          List<Article> result =
              (List<Article>) articleDAO.findAll(articleCondition.getCondition());
          if (result == null || result.size() == 0)
            throw new AppException("Add new article error!");
          Article article = result.get(0);
          targetArticleId = article.getArticleId();
        }

        if (targetArticleId == null)
          throw new AppException("Add new article error!");

        return redirect(getActionURL("/admin", "article/editor/" + targetArticleId));
      } else {
        targetArticle = new ArticleView(articleDAO.get(targetArticleId));
        if (targetArticle.getArticleId() == null)
          throw new AppException("Wrong article ID!");
      }
    } catch (AppException e) {
      return redirect(getActionURL("/admin", "index"), e.getMessage());
    } catch (Exception e) {
      LOGGER.warn(e);
      return redirect(getActionURL("/admin", "index"), "Unknown exception occurred.");
    }
    return SUCCESS;
  }

  @Autowired
  private ArticleDTO articleDTO;

  @Override
  public void setArticleDTO(ArticleDTO articleDTO) {
    this.articleDTO = articleDTO;
  }

  @Override
  public ArticleDTO getArticleDTO() {
    return articleDTO;
  }

  @Autowired
  private ArticleCondition articleCondition;

  @Override
  public void setArticleCondition(ArticleCondition articleCondition) {
    this.articleCondition = articleCondition;
  }

  @Override
  public ArticleCondition getArticleCondition() {
    return articleCondition;
  }

  /**
   * return the article.jsp for base view
   *
   * @return <strong>SUCCESS</strong> signal
   */
  public String toArticleList() {
    return SUCCESS;
  }

  /**
   * Search action.
   * <p/>
   * Find all records by conditions and return them as a list in JSON, and the condition set will
   * set in JSON named "condition".
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok", "pageInfo":<strong>PageInfo object</strong>,
   * "articleList":<strong>query result</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   *
   * @return <strong>JSON</strong> signal
   */
  @SuppressWarnings("unchecked")
  public String toSearch() {
    try {
      articleCondition.setIsTitleEmpty(false);
      Condition condition = articleCondition.getCondition();
      Long count = articleDAO.count(articleCondition.getCondition());
      PageInfo pageInfo = buildPageInfo(count, RECORD_PER_PAGE, "", null);
      condition.setCurrentPage(pageInfo.getCurrentPage());
      condition.setCountPerPage(RECORD_PER_PAGE);
      List<Article> articleList = (List<Article>) articleDAO.findAll(condition);
      List<ArticleListView> articleListViewList = new ArrayList<>();
      for (Article article : articleList)
        articleListViewList.add(new ArticleListView(article));
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "ok");
      json.put("articleList", articleListViewList);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      LOGGER.warn(e);
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return JSON;
  }

  /**
   * Action to operate multiple articles.
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok", "msg":<strong>successful message</strong>}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   *
   * @return <strong>JSON</strong> signal.
   */
  public String toOperatorArticle() {
    try {
      int count = 0, total = 0;
      Integer[] ids = ArrayUtil.parseIntArray(get("id"));
      String method = get("method");
      for (Integer id : ids)
        if (id != null) {
          ++total;
          try {
            Article article = articleDAO.get(id);
            if ("delete".equals(method)) {
              articleDAO.delete(article);
            } else if ("edit".equals(method)) {
              String field = get("field");
              String value = get("value");
              Method[] methods = article.getClass().getMethods();
              for (Method getter : methods) {
                Column column = getter.getAnnotation(Column.class);
                if (column != null && column.name().equals(field)) {
                  String setterName =
                      StringUtil.getGetterOrSetter(StringUtil.MethodType.SETTER, getter.getName()
                          .substring(3));
                  Method setter = article.getClass().getMethod(setterName, getter.getReturnType());
                  setter.invoke(article, ReflectionUtil.valueOf(value, getter.getReturnType()));
                }
              }
              articleDAO.update(article);
            }
            ++count;
          } catch (AppException ignored) {
          }
        }
      json.put("result", "ok");
      String message = "";
      if ("delete".equals(method))
        message = String.format("%d total, %d deleted.", total, count);
      else if ("edit".equals(method))
        message = String.format("%d total, %d changed.", total, count);
      json.put("msg", message);
    } catch (Exception e) {
      LOGGER.warn(e);
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return JSON;
  }
}
