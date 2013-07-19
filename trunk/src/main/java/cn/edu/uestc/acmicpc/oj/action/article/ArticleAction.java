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

package cn.edu.uestc.acmicpc.oj.action.article;

import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.db.view.impl.ArticleView;
import cn.edu.uestc.acmicpc.ioc.dao.ArticleDAOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Description
 * 
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
public class ArticleAction extends BaseAction implements ArticleDAOAware {

  /**
	 * 
	 */
  private static final long serialVersionUID = -8704573553875022341L;
  private Integer targetArticleId;
  private ArticleView targetArticle;

  public Integer getTargetArticleId() {
    return targetArticleId;
  }

  public void setTargetArticleId(Integer targetArticleId) {
    this.targetArticleId = targetArticleId;
  }

  public ArticleView getTargetArticle() {
    return targetArticle;
  }

  public void setTargetArticle(ArticleView targetArticle) {
    this.targetArticle = targetArticle;
  }

  public String toArticle() {
    try {
      if (targetArticleId == null)
        throw new AppException("Article Id is empty!");

      Article article = articleDAO.get(targetArticleId);
      if (article == null)
        throw new AppException("Wrong article ID!");

      if (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal())
        if (!article.getIsVisible())
          throw new AppException("Article doesn't exist");
      targetArticle = new ArticleView(article);
    } catch (AppException e) {
      System.out.println(e.getMessage());
      return redirect(getActionURL("/", "index"), e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      return redirect(getActionURL("/", "index"), "Unknown exception occurred.");
    }
    return SUCCESS;
  }

  @Autowired
  private IArticleDAO articleDAO;

  @Override
  public void setArticleDAO(IArticleDAO articleDAO) {
    this.articleDAO = articleDAO;
  }
}
