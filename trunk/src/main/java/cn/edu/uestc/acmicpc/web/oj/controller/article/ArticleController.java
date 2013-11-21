package cn.edu.uestc.acmicpc.web.oj.controller.article;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

  private ArticleService articleService;

  @Autowired
  public void setArticleService(ArticleService articleService) {
    this.articleService = articleService;
  }

  @RequestMapping("show/{articleId}")
  @LoginPermit(NeedLogin = false)
  public String show(@PathVariable("articleId") Integer articleId,
      HttpSession session, ModelMap model) {
    try {
      UserDTO currentUser = (UserDTO)session.getAttribute("currentUser");
      ArticleDTO articleDTO = articleService.getArticleDTO(articleId);
      if (articleDTO == null)
        throw new AppException("Wrong article ID.");
      if (articleDTO.getIsVisible() == false &&
          (currentUser == null || currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()))
        throw new AppException("You have no permission to view this articl.");
      model.put("targetArticle", articleDTO);
    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "article/articleShow";
  }
}
