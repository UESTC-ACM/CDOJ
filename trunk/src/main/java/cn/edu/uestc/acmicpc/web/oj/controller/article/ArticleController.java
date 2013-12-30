package cn.edu.uestc.acmicpc.web.oj.controller.article;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleEditorShowDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

  private ArticleService articleService;
  private PictureService pictureService;

  @Autowired
  public void setArticleService(ArticleService articleService) {
    this.articleService = articleService;
  }

  @Autowired
  public void setPictureService(PictureService pictureService) {
    this.pictureService = pictureService;
  }

  @RequestMapping("show/{articleId}")
  @LoginPermit(NeedLogin = false)
  public String show(@PathVariable("articleId") Integer articleId,
      HttpSession session, ModelMap model) {
    try {
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      ArticleDTO articleDTO = articleService.getArticleDTO(articleId);
      if (articleDTO == null)
        throw new AppException("Wrong article ID.");
      if (articleDTO.getIsVisible() == false) {
        if (currentUser == null ||
            currentUser.getType() != Global.AuthenticationType.ADMIN.ordinal()) {
          throw new AppException("You have no permission to view this articl.");
        }
      }
      articleService.incrementClicked(articleDTO.getArticleId());
      model.put("targetArticle", articleDTO);
    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "article/articleShow";
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody
  Map<String, Object> search(HttpSession session,
      @RequestBody ArticleCondition articleCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      UserDTO currentUser = (UserDTO) session.getAttribute("currentUser");
      if (currentUser != null
          && currentUser.getType() == Global.AuthenticationType.ADMIN.ordinal()) {
        // We can put some special condition here
      } else {
        articleCondition.isVisible = true;
      }
      Long count = articleService.count(articleCondition);
      PageInfo pageInfo = buildPageInfo(count, articleCondition.currentPage,
          Global.ARTICLE_PER_PAGE, "", null);

      List<ArticleListDTO> articleListDTOList = articleService.getArticleList(
          articleCondition, pageInfo);

      if (pageInfo.getTotalPages() != 1) {
        json.put("pageInfo", pageInfo.getHtmlString());
      }
      json.put("result", "success");
      json.put("list", articleListDTOList);
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

  @RequestMapping("editor/{articleId}")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public String editor(@PathVariable("articleId") String sArticleId,
      ModelMap model) {
    try {
      if (sArticleId.compareTo("new") == 0) {
        model.put("action", "new");
      } else {
        Integer articleId;
        try {
          articleId = Integer.parseInt(sArticleId);
        } catch (NumberFormatException e) {
          throw new AppException("Parse article id error.");
        }
        ArticleEditorShowDTO targetArticle = articleService
            .getArticleEditorShowDTO(articleId);
        if (targetArticle == null)
          throw new AppException("No such article.");
        model.put("action", "edit");
        model.put("targetArticle", targetArticle);
      }
    } catch (AppException e) {
      model.put("message", e.getMessage());
      return "error/error";
    }
    return "/article/articleEditor";
  }

  @RequestMapping("edit")
  @LoginPermit(Global.AuthenticationType.ADMIN)
  public @ResponseBody
  Map<String, Object> edit(@RequestBody @Valid ArticleEditDTO articleEditDTO,
      BindingResult validateResult,
      HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        if (StringUtil.trimAllSpace(articleEditDTO.getTitle()).equals(""))
          throw new FieldException("title", "Please enter a validate title.");
        ArticleDTO articleDTO;
        if (articleEditDTO.getAction().compareTo("new") == 0) {
          Integer articleId = articleService.createNewArticle();
          articleDTO = articleService.getArticleDTO(articleId);
          if (articleDTO == null
              || !articleDTO.getArticleId().equals(articleId))
            throw new AppException("Error while creating article.");
          // Move pictures
          String oldDirectory = "/images/article/new/";
          String newDirectory = "/images/article/" + articleId + "/";
          articleEditDTO.setContent(pictureService.modifyPictureLocation(
              articleEditDTO.getContent(), oldDirectory, newDirectory));
        } else {
          articleDTO = articleService.getArticleDTO(articleEditDTO
              .getArticleId());
          if (articleDTO == null)
            throw new AppException("No such article.");
        }

        articleDTO.setTitle(articleEditDTO.getTitle());
        articleDTO.setContent(articleEditDTO.getContent());
        articleDTO.setTime(new Timestamp(System.currentTimeMillis()));

        articleService.updateArticle(articleDTO);
        json.put("result", "success");
        json.put("articleId", articleDTO.getArticleId());
      } catch (FieldException e) {
        putFieldErrorsIntoBindingResult(e, validateResult);
        json.put("result", "field_error");
        json.put("field", validateResult.getFieldErrors());
      } catch (AppException e) {
        json.put("result", "error");
        json.put("error_msg", e.getMessage());
      }
    }
    return json;
  }

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

}
