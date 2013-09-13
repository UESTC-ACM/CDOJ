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

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.ioc.dao.ArticleDAOAware;
import cn.edu.uestc.acmicpc.ioc.dto.ArticleDTOAware;
import cn.edu.uestc.acmicpc.oj.action.BaseAction;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import org.springframework.stereotype.Controller;

/**
 * Description
 *
 * @author <a href="mailto:muziriyun@gmail.com">mzry1992</a>
 */
@Controller
public class AdminArticleStatementAction extends BaseAction implements ArticleDAOAware,
    ArticleDTOAware {

  private static final Logger LOGGER = LogManager.getLogger(AdminArticleStatementAction.class);

  /**
	 *
	 */
  private static final long serialVersionUID = -5371516340656472206L;

  @Autowired
  private IArticleDAO articleDAO;

  @Autowired
  private ArticleDTO articleDTO;

  @Override
  public void setArticleDAO(IArticleDAO articleDAO) {
    this.articleDAO = articleDAO;
  }

  @Override
  public void setArticleDTO(ArticleDTO articleDTO) {
    this.articleDTO = articleDTO;
  }

  @Override
  public ArticleDTO getArticleDTO() {
    return articleDTO;
  }

  /**
   * To add or edit user entity.
   * <p/>
   * <strong>JSON output</strong>:
   * <ul>
   * <li>
   * For success: {"result":"ok"}</li>
   * <li>
   * For error: {"result":"error", "error_msg":<strong>error message</strong>}</li>
   * </ul>
   *
   * @return <strong>JSON</strong> signal
   */
  @Validations(requiredStrings = { @RequiredStringValidator(fieldName = "articleDTO.title",
      key = "error.title.validation", trim = true) })
  public String toEdit() {
    try {
      Article article;

      article = articleDAO.get(articleDTO.getArticleId());
      if (article == null)
        throw new AppException("No such article!");

      articleDTO.updateEntity(article);
      article.setUserByUserId(currentUser);

      articleDAO.update(article);
      json.put("result", "ok");
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
}
