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

import cn.edu.uestc.acmicpc.db.condition.base.Condition;
import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.db.view.impl.ArticleView;
import cn.edu.uestc.acmicpc.ioc.condition.ArticleConditionAware;
import cn.edu.uestc.acmicpc.ioc.dao.ArticleDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.ArticleDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * action for list, search, edit, add article.
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@LoginPermit(value = Global.AuthenticationType.ADMIN)
public class ArticleAdminAction extends BaseAction implements ArticleDAOAware, ArticleDTOAware, ArticleConditionAware {

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
                    List<Article> result = (List<Article>) articleDAO.findAll(articleCondition.getCondition());
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
            e.printStackTrace();
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
}
