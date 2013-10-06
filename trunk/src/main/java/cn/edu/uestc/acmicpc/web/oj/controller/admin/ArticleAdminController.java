package cn.edu.uestc.acmicpc.web.oj.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
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
    return "admin/article/";
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
}
