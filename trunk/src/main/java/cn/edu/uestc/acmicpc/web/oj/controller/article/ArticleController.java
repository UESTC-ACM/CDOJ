package cn.edu.uestc.acmicpc.web.oj.controller.article;

import cn.edu.uestc.acmicpc.db.criteria.impl.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.service.iface.PictureService;
import cn.edu.uestc.acmicpc.util.annotation.JsonMap;
import cn.edu.uestc.acmicpc.util.annotation.LoginPermit;
import cn.edu.uestc.acmicpc.util.enums.ArticleType;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.util.exception.FieldException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.util.settings.Settings;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {

  private ArticleService articleService;
  private PictureService pictureService;
  private Settings settings;

  @Autowired
  public ArticleController(ArticleService articleService,
                           PictureService pictureService, Settings settings) {
    this.articleService = articleService;
    this.pictureService = pictureService;
    this.settings = settings;
  }

  @RequestMapping("data/{articleId}")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> data(@PathVariable("articleId") Integer articleId,
                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    try {
      ArticleDto articleDto = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
      AppExceptionUtil.assertNotNull(articleDto, "No such article.");
      if (articleDto.getType() != ArticleType.ARTICLE.ordinal() &&
          articleDto.getType() != ArticleType.NOTICE.ordinal()) {
        throw new AppException("No such article.");
      }
      if (!articleDto.getIsVisible() && !isAdmin(session)) {
        throw new AppException("Permission denied!");
      }
      articleService.incrementClicked(articleDto.getArticleId());
      json.put("article", articleDto);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("changeNoticeOrder")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> changeNoticeOrder(@JsonMap("order") String orderList) {
    Map<String, Object> json = new HashMap<>();
    try {
      String[] articleIdList = orderList.split(",");
      List<ArticleDto> articleList = new LinkedList<>();
      for (String articleIdString : articleIdList) {
        if (articleIdString.length() == 0) {
          continue;
        }
        Integer articleId;
        try {
          articleId = Integer.parseInt(articleIdString);
        } catch (NumberFormatException e) {
          throw new AppException("Article ID format error.");
        }

        ArticleDto articleDto = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
        AppExceptionUtil.assertNotNull(articleDto, "No such article.");
        if (articleDto.getType() != ArticleType.ARTICLE.ordinal() &&
            articleDto.getType() != ArticleType.NOTICE.ordinal()) {
          throw new AppException("No such article.");
        }

        articleList.add(articleDto);
      }
      // Set order
      for (Integer order = 0; order < articleList.size(); order++) {
        ArticleDto articleDto = articleList.get(order);
        // Update
        ArticleDto articleDtoForUpdate =
            ArticleDto.builder()
                .setArticleId(articleDto.getArticleId())
                .setOrder(order)
                .build();
        articleService.updateArticle(articleDtoForUpdate);
      }
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    } catch (Exception e) {
      json.put("result", "error");
      json.put("error_msg", "Unknown exception occurred.");
    }
    return json;
  }

  @RequestMapping("commentSearch")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> commentSearch(HttpSession session,
                                    @RequestBody ArticleCriteria articleCriteria) {
    Map<String, Object> json = new HashMap<>();
    try {
      articleCriteria.setResultFields(ArticleFields.FIELDS_FOR_LIST_PAGE);
      if (!isAdmin(session)) {
        articleCriteria.isVisible = true;
      }
      articleCriteria.type = ArticleType.COMMENT.ordinal();

      Long count = articleService.count(articleCriteria);
      PageInfo pageInfo = buildPageInfo(count, articleCriteria.currentPage,
          settings.RECORD_PER_PAGE, null);

      List<ArticleDto> articleListDTOList = articleService.getArticleList(
          articleCriteria, pageInfo);

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", articleListDTOList);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("editComment")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> editComment(@JsonMap("articleEditDto") ArticleDto articleEditDto,
                                  @JsonMap("action") String action,
                                  BindingResult validateResult,
                                  HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO currentUser = getCurrentUser(session);
        if (checkPermission(session, articleEditDto.getUserId())) {
          throw new AppException("Permission denied");
        }
        articleEditDto.setContent(StringUtil.trimAllSpace(articleEditDto.getContent()));
        if (articleEditDto.getContent().length() <= 0 || articleEditDto.getContent().length() > 233) {
          throw new AppException("Comment should contain no more than 233 characters.");
        }
        ArticleDto articleDto;
        if (action.equals("new")) {
          Integer articleId = articleService.createNewArticle(currentUser.getUserId());
          articleDto = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
          if (articleDto == null || !articleDto.getArticleId().equals(articleId)) {
            throw new AppException("Error while creating comment.");
          }
          String userName = getCurrentUser(session).getUserName();
          // Move pictures
          String oldDirectory = "article/" + userName + "/newComment/";
          String newDirectory = "article/" + userName + "/" + articleId + "/";
          articleEditDto.setContent(pictureService.modifyPictureLocation(
              articleEditDto.getContent(), oldDirectory, newDirectory));
        } else {
          articleDto = articleService.getArticleDto(articleEditDto.getArticleId(), ArticleFields.ALL_FIELDS);
          if (articleDto == null) {
            throw new AppException("No such comment.");
          }
        }

        articleDto.setTitle(articleEditDto.getTitle());
        articleDto.setContent(articleEditDto.getContent());
        articleDto.setTime(new Timestamp(System.currentTimeMillis()));
        articleDto.setType(ArticleType.COMMENT.ordinal());
        articleDto.setProblemId(articleEditDto.getProblemId());
        articleDto.setContestId(articleEditDto.getContestId());
        articleDto.setParentId(articleEditDto.getParentId());

        articleService.updateArticle(articleDto);
        json.put("result", "success");
        json.put("articleId", articleDto.getArticleId());
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

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public
  @ResponseBody
  Map<String, Object> search(HttpSession session,
                             @RequestBody ArticleCriteria articleCriteria) {
    Map<String, Object> json = new HashMap<>();
    try {
      articleCriteria.setResultFields(ArticleFields.FIELDS_FOR_LIST_PAGE);
      if (!isAdmin(session)) {
        articleCriteria.isVisible = true;
      }
      articleCriteria.problemId = -1;
      articleCriteria.contestId = -1;
      articleCriteria.parentId = -1;
      Long count = articleService.count(articleCriteria);
      PageInfo pageInfo = buildPageInfo(count, articleCriteria.currentPage,
          settings.RECORD_PER_PAGE, null);

      List<ArticleDto> result = articleService.getArticleList(
          articleCriteria, pageInfo);

      json.put("pageInfo", pageInfo);
      json.put("result", "success");
      json.put("list", result);
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

  @RequestMapping("edit")
  @LoginPermit(NeedLogin = true)
  public
  @ResponseBody
  Map<String, Object> edit(@JsonMap("articleEditDto") ArticleDto articleEditDto,
                           @JsonMap("action") String action,
                           BindingResult validateResult,
                           HttpSession session) {
    Map<String, Object> json = new HashMap<>();
    if (validateResult.hasErrors()) {
      json.put("result", "field_error");
      json.put("field", validateResult.getFieldErrors());
    } else {
      try {
        UserDTO currentUser = getCurrentUser(session);
        if (checkPermission(session, articleEditDto.getUserId())) {
          throw new AppException("Permission denied");
        }

        if (StringUtil.trimAllSpace(articleEditDto.getTitle()).equals("")) {
          throw new FieldException("title", "Please enter a validate title.");
        }
        ArticleDto articleDto;
        if (action.equals("new")) {
          Integer articleId = articleService.createNewArticle(currentUser.getUserId());
          articleDto = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
          if (articleDto == null || !articleDto.getArticleId().equals(articleId)) {
            throw new AppException("Error while creating article.");
          }
          String userName = getCurrentUser(session).getUserName();
          // Move pictures
          String oldDirectory = "article/" + userName + "/new/";
          String newDirectory = "article/" + userName + "/" + articleId + "/";
          articleEditDto.setContent(pictureService.modifyPictureLocation(
              articleEditDto.getContent(), oldDirectory, newDirectory));
        } else {
          articleDto = articleService.getArticleDto(articleEditDto.getArticleId(), ArticleFields.ALL_FIELDS);
          if (articleDto == null) {
            throw new AppException("No such article.");
          }
        }

        articleDto.setTitle(articleEditDto.getTitle());
        articleDto.setContent(articleEditDto.getContent());
        articleDto.setTime(new Timestamp(System.currentTimeMillis()));
        if (isAdmin(session)) {
          articleDto.setType(articleEditDto.getType());
        } else {
          articleDto.setType(ArticleType.ARTICLE.ordinal());
        }

        articleService.updateArticle(articleDto);
        json.put("result", "success");
        json.put("articleId", articleDto.getArticleId());
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

  @RequestMapping("operation/{id}/{field}/{value}")
  @LoginPermit(AuthenticationType.ADMIN)
  public
  @ResponseBody
  Map<String, Object> operation(@PathVariable("id") String targetId,
                                @PathVariable("field") String field,
                                @PathVariable("value") String value) {
    Map<String, Object> json = new HashMap<>();
    try {
      articleService.operator(field, targetId, value);
      json.put("result", "success");
    } catch (AppException e) {
      json.put("result", "error");
      json.put("error_msg", e.getMessage());
    }
    return json;
  }

}
