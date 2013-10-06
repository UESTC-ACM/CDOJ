package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.Global;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 * TODO(mzry1992)
 */
@Controller
@RequestMapping("/admin/article")
public class ArticleAdminController extends BaseController{

  private ArticleService articleService;

  @Autowired
  public void setArticleService(ArticleService articleService) {
    this.articleService = articleService;
  }

  @RequestMapping("list")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String list() {
    return "admin/article/articleList";
  }

  @RequestMapping("search")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> search(ArticleCondition articleCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      articleCondition.isTitleEmpty = false;
      Long count = articleService.count(articleCondition);
      PageInfo pageInfo = buildPageInfo(count, articleCondition.currentPage,
          Global.RECORD_PER_PAGE, "", null);
      List<ArticleListDTO> articleList = articleService.getArticleList(articleCondition, pageInfo);
      json.put("pageInfo", pageInfo.getHtmlString());
      json.put("result", "ok");
      json.put("articleList", articleList);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  /**
   * TODO(mzry1992)
   *
   * @param targetId
   * @param field
   * @param value
   * @return
   */
  @RequestMapping("operator/{id}/{field}/{value}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> operator(@PathVariable("id") String targetId,
                               @PathVariable("field") String field,
                               @PathVariable("value") String value) {
    Map<String, Object> json = new HashMap<>();
    try {
      articleService.operator(field, targetId, value);
      json.put("result", "success");
    } catch (Exception e) {
      e.printStackTrace();
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("editor/{articleId}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String editor(@PathVariable("articleId") Integer articleId,
                       ModelMap model) {
    try {
      if (articleId == 0) {
        ArticleCondition articleCondition = new ArticleCondition();
        articleCondition.isTitleEmpty = true;
        Long count = articleService.count(articleCondition);
        if (count == 0) {
          articleId = articleService.createNewArticle();
        } else {
          PageInfo pageInfo = buildPageInfo(count, articleCondition.currentPage,
              Global.RECORD_PER_PAGE, "", null);
          List<ArticleListDTO> articleList = articleService.getArticleList(articleCondition, pageInfo);
          if (articleList == null || articleList.size() == 0)
            throw new AppException("Add new article error.");
          ArticleListDTO articleListDTO = articleList.get(0);
          articleId = articleListDTO.getArticleId();
        }
        return "redirect:/admin/article/editor/" + articleId;
      } else {
        ArticleEditorShowDTO targetArticle = articleService.getArticleEditorShowDTO(articleId);
        if (targetArticle == null)
          throw new AppException("No such article.");
        model.put("targetArticle", targetArticle);
      }
    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "admin/article/articleEditor";
  }

}
