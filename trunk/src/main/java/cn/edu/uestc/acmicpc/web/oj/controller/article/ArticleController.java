package cn.edu.uestc.acmicpc.web.oj.controller.article;

import cn.edu.uestc.acmicpc.db.criteria.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
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
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/article")
public class ArticleController extends BaseController {
  private static final Logger logger = Logger.getLogger(ArticleController.class);
  private final ArticleService articleService;
  private final PictureService pictureService;
  private final Settings settings;

  @Autowired
  public ArticleController(ArticleService articleService,
      PictureService pictureService, Settings settings) {
    this.articleService = articleService;
    this.pictureService = pictureService;
    this.settings = settings;
  }

  @RequestMapping("data/{articleId}")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> data(@PathVariable("articleId") Integer articleId,
      HttpSession session) throws AppException {
    Map<String, Object> json = new HashMap<>();
    ArticleDto articleDto = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
    AppExceptionUtil.assertNotNull(articleDto, "No such article.");
    if (!articleDto.getIsVisible() && !isAdmin(session)) {
      throw new AppException("Permission denied!");
    }
    articleService.incrementClicked(articleDto.getArticleId());
    json.put("article", articleDto);
    json.put("result", "success");
    return json;
  }

  @RequestMapping("changeNoticeOrder")
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> changeNoticeOrder(@JsonMap("order") String orderList)
      throws AppException {
    Map<String, Object> json = new HashMap<>();
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
    return json;
  }

  @RequestMapping("commentSearch")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> commentSearch(HttpSession session,
      @RequestBody ArticleCriteria articleCriteria) throws AppException {
    Map<String, Object> json = new HashMap<>();
    if (!isAdmin(session)) {
      articleCriteria.isVisible = true;
    }
    articleCriteria.type = ArticleType.COMMENT.ordinal();

    Long count = articleService.count(articleCriteria);
    PageInfo pageInfo = buildPageInfo(count, articleCriteria.currentPage,
        settings.RECORD_PER_PAGE, null);

    List<ArticleDto> articleListDtoList = articleService.getArticleList(
        articleCriteria, pageInfo, ArticleFields.FIELDS_FOR_LIST_PAGE);

    json.put("pageInfo", pageInfo);
    json.put("result", "success");
    json.put("list", articleListDtoList);
    return json;
  }

  @RequestMapping("search")
  @LoginPermit(NeedLogin = false)
  public @ResponseBody Map<String, Object> search(HttpSession session,
      @RequestBody ArticleCriteria articleCriteria) throws AppException {
    Map<String, Object> json = new HashMap<>();
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
        articleCriteria, pageInfo, ArticleFields.FIELDS_FOR_LIST_PAGE);
    for (ArticleDto articleDto : result) {
      String content = articleDto.getContent();
      if (content.contains("!!!more!!!")) {
        articleDto.setHasMore(true);
        articleDto.setContent(content.substring(0, content.indexOf("!!!more!!!")));
      } else {
        articleDto.setHasMore(false);
      }
    }

    json.put("pageInfo", pageInfo);
    json.put("result", "success");
    json.put("list", result);
    return json;
  }

  @RequestMapping("edit")
  @LoginPermit()
  public @ResponseBody Map<String, Object> edit(
      @JsonMap("articleEditDto") ArticleDto articleEditDto,
      @JsonMap("action") String action,
      HttpSession session) throws AppException {
    Map<String, Object> json = new HashMap<>();
    UserDto currentUser = getCurrentUser(session);

    if (articleEditDto.getType() == ArticleType.COMMENT.ordinal()) {
      // Check comment content length
      articleEditDto.setContent(StringUtil.trimAllSpace(articleEditDto.getContent()));
      if (articleEditDto.getContent() == null ||
          articleEditDto.getContent().length() <= 0 ||
          articleEditDto.getContent().length() > 233) {
        throw new AppException("Comment should contain no more than 233 characters.");
      }
    } else {
      // Check title
      if (StringUtil.trimAllSpace(articleEditDto.getTitle()).equals("")) {
        throw new FieldException("title", "Please enter a validate title.");
      }
      // Check userId
      logger.info( "UserId is " + currentUser.getUserId() );
      if (currentUser.getUserId().equals(articleEditDto.getUserId()) == false && currentUser.getType() != 1 && action.equals("new") == false  ) {
        throw new AppException("Permission denied");
      }
    }
    ArticleDto articleDto;
    if (action.equals("new")) {
      Integer articleId = articleService.createNewArticle(currentUser.getUserId());
      articleDto = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
      if (articleDto == null) {
        throw new AppException("Error while creating article.");
      }
      String userName = getCurrentUser(session).getUserName();
      // Move pictures
      String oldDirectory = "article/" + userName + "/new/";
      String newDirectory = "article/" + userName + "/" + articleId + "/";
      articleEditDto.setContent(pictureService.modifyPictureLocation(
          articleEditDto.getContent(), oldDirectory, newDirectory));
      articleDto.setType(ArticleType.ARTICLE.ordinal());
      articleDto.setUserId(currentUser.getUserId());
    } else {
      articleDto = articleService.getArticleDto(articleEditDto.getArticleId(),
          ArticleFields.ALL_FIELDS);
      if (articleDto == null) {
        throw new AppException("No such article.");
      }
      if (!checkPermission(session, articleDto.getUserId())) {
        throw new AppException("Permission denied");
      }
    }

    if (articleEditDto.getType() == ArticleType.COMMENT.ordinal()) {
      articleDto.setTitle("Comment");
    } else {
      articleDto.setTitle(articleEditDto.getTitle());
    }

    articleDto.setContent(articleEditDto.getContent());
    articleDto.setTime(new Timestamp(System.currentTimeMillis()));
    if (isAdmin(session)) {
      articleDto.setType(articleEditDto.getType());
    } else {
      if (articleEditDto.getType() == ArticleType.COMMENT.ordinal()) {
        articleDto.setType(ArticleType.COMMENT.ordinal());
      }
    }

    if (articleEditDto.getType() == ArticleType.COMMENT.ordinal()) {
      articleDto.setProblemId(articleEditDto.getProblemId());
      articleDto.setContestId(articleEditDto.getContestId());
      articleDto.setParentId(articleEditDto.getParentId());
    }

    articleService.updateArticle(articleDto);
    json.put("result", "success");
    json.put("articleId", articleDto.getArticleId());
    return json;
  }

  @RequestMapping("applyOperation/{id}/{field}/{value}")
  @LoginPermit(AuthenticationType.ADMIN)
  public @ResponseBody Map<String, Object> applyOperation(@PathVariable("id") String targetId,
      @PathVariable("field") String field,
      @PathVariable("value") String value) throws AppException {
    Map<String, Object> json = new HashMap<>();
    articleService.applyOperation(field, targetId, value);
    json.put("result", "success");
    return json;
  }

}
