package cn.edu.uestc.acmicpc.web.oj.controller.article;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleEditDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Global;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.base.BaseController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

  private ArticleService articleService;
  private PictureService pictureService;

  @Autowired
  public ArticleController(ArticleService articleService, PictureService pictureService) {
    this.articleService = articleService;
    this.pictureService = pictureService;
  }

  @RequestMapping("data/{articleId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> data(@PathVariable("articleId") Integer articleId,
                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      ArticleDTO articleDTO = articleService.getArticleDTO(articleId);
      AppExceptionUtil.assertNotNull(articleDTO, "No such article.");
      if (!checkPermission(session, articleDTO.getUserId())) {
        throw new AppException("Permission denied!");
      }
      articleService.incrementClicked(articleDTO.getArticleId());
      json.put("article", articleDTO);
      json.put("result", "success");
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

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody ArticleCondition articleCondition) {
    Map<String, Object> json = new HashMap<>();
    try {
      if (!isAdmin(session)) {
        articleCondition.isVisible = true;
      }
      Long count = articleService.count(articleCondition);
      PageInfo pageInfo = buildPageInfo(count, articleCondition.currentPage,
          Global.ARTICLE_PER_PAGE, null);

      List<ArticleListDTO> articleListDTOList = articleService.getArticleList(
          articleCondition, pageInfo);

      json.put("pageInfo", pageInfo);
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

  @RequestMapping("edit")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> edit(@RequestBody @Valid ArticleEditDTO articleEditDTO,
                           BindingResult validateResult,
                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO currentUser = getCurrentUser(session);
        if (!isAdmin(session) && !currentUser.getUserName().equals(articleEditDTO.getUserName())) {
          throw new AppException("Permission denied");
        }

        if (StringUtil.trimAllSpace(articleEditDTO.getTitle()).equals("")) {
          throw new FieldException("title", "Please enter a validate title.");
        }
        ArticleDTO articleDTO;
        if (articleEditDTO.getAction().equals("new")) {
          Integer articleId = articleService.createNewArticle(currentUser.getUserId());
          articleDTO = articleService.getArticleDTO(articleId);
          if (articleDTO == null || !articleDTO.getArticleId().equals(articleId)) {
            throw new AppException("Error while creating article.");
          }
          // Move pictures
          String oldDirectory = "/images/article/" + articleEditDTO.getUserName() + "/new/";
          String newDirectory = "/images/article/" + articleEditDTO.getUserName() + "/" + articleId + "/";
          articleEditDTO.setContent(pictureService.modifyPictureLocation(
              articleEditDTO.getContent(), oldDirectory, newDirectory));
        } else {
          articleDTO = articleService.getArticleDTO(articleEditDTO
              .getArticleId());
          if (articleDTO == null) {
            throw new AppException("No such article.");
          }
        }

        articleDTO.setTitle(articleEditDTO.getTitle());
        articleDTO.setContent(articleEditDTO.getContent());
        articleDTO.setTime(new Timestamp(System.currentTimeMillis()));
        articleDTO.setType(Global.ArticleType.ARTICLE.ordinal());

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
  public
  @ResponseBody
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
