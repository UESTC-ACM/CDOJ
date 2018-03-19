package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anySet;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.edu.uestc.acmicpc.db.criteria.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.testing.ControllerTest;
import cn.edu.uestc.acmicpc.util.enums.ArticleType;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.StringUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.article.ArticleController;
import com.alibaba.fastjson.JSON;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test cases for {@link ArticleController}.
 */
public class ArticleControllerTest extends ControllerTest {

  @Autowired
  private ArticleController articleController;

  @Override
  @BeforeMethod
  public void init() {
    super.init();
    mockMvc = initControllers(articleController);
    session = new MockHttpSession(context.getServletContext(), UUID.randomUUID().toString());
  }

  @Test
  public void testFetchVisibleArticleDataSuccessful() throws Exception {
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(1)
        .setTitle("Test")
        .setContent("Test Content")
        .setTime(new Timestamp(System.currentTimeMillis()))
        .setClicked(123)
        .setOrder(0)
        .setType(ArticleType.ARTICLE.ordinal())
        .setIsVisible(true)
        .setUserId(1)
        .setOwnerName("administrator")
        .setOwnerEmail("acm@uestc.edu.cn")
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS))
        .thenReturn(articleDto);
    mockMvc.perform(get("/article/data/{articleId}", 1))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.article", notNullValue()))
        .andExpect(jsonPath("$.article.articleId", is(articleDto.getArticleId())))
        .andExpect(jsonPath("$.article.title", is(articleDto.getTitle())))
        .andExpect(jsonPath("$.article.content", is(articleDto.getContent())))
        .andExpect(jsonPath("$.article.time", is(articleDto.getTime().getTime())))
        .andExpect(jsonPath("$.article.clicked", is(articleDto.getClicked())))
        .andExpect(jsonPath("$.article.order", is(articleDto.getOrder())))
        .andExpect(jsonPath("$.article.type", is(articleDto.getType())))
        .andExpect(jsonPath("$.article.isVisible", is(articleDto.getIsVisible())))
        .andExpect(jsonPath("$.article.userId", is(articleDto.getUserId())))
        .andExpect(jsonPath("$.article.ownerName", is(articleDto.getOwnerName())))
        .andExpect(jsonPath("$.article.ownerEmail", is(articleDto.getOwnerEmail())));
  }

  @Test
  public void testFetchArticleFailed() throws Exception {
    when(articleService.getArticleDto(100, ArticleFields.ALL_FIELDS))
        .thenReturn(null);
    mockMvc.perform(get("/article/data/{articleId}", 100))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("No such article.")));
  }

  @Test
  public void testFetchInvisibleArticleDataFailed() throws Exception {
    ArticleDto articleDto = ArticleDto.builder()
        .setIsVisible(false)
        .build();
    when(articleService.getArticleDto(100, ArticleFields.ALL_FIELDS))
        .thenReturn(articleDto);
    mockMvc.perform(get("/article/data/{articleId}", 100))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("Permission denied!")));
  }

  @Test
  public void testFetchInvisibleArticleDataByAdmin() throws Exception {
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(100)
        .setTitle("Test admin")
        .setContent("Secret content")
        .setTime(new Timestamp(System.currentTimeMillis()))
        .setClicked(123)
        .setOrder(0)
        .setType(ArticleType.ARTICLE.ordinal())
        .setIsVisible(false)
        .setUserId(1)
        .setOwnerName("administrator")
        .setOwnerEmail("acm@uestc.edu.cn")
        .build();
    when(articleService.getArticleDto(100, ArticleFields.ALL_FIELDS))
        .thenReturn(articleDto);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(get("/article/data/{articleId}", 100)
        .sessionAttr("currentUser", currentUserDto))
        .andExpect(request().sessionAttribute("currentUser", currentUserDto))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.article", notNullValue()))
        .andExpect(jsonPath("$.article.articleId", is(articleDto.getArticleId())))
        .andExpect(jsonPath("$.article.title", is(articleDto.getTitle())))
        .andExpect(jsonPath("$.article.content", is(articleDto.getContent())))
        .andExpect(jsonPath("$.article.time", is(articleDto.getTime().getTime())))
        .andExpect(jsonPath("$.article.clicked", is(articleDto.getClicked())))
        .andExpect(jsonPath("$.article.order", is(articleDto.getOrder())))
        .andExpect(jsonPath("$.article.type", is(articleDto.getType())))
        .andExpect(jsonPath("$.article.isVisible", is(articleDto.getIsVisible())))
        .andExpect(jsonPath("$.article.userId", is(articleDto.getUserId())))
        .andExpect(jsonPath("$.article.ownerName", is(articleDto.getOwnerName())))
        .andExpect(jsonPath("$.article.ownerEmail", is(articleDto.getOwnerEmail())));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testCommentSearchSuccessful() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new LinkedList<>();
    for (int i = 0; i < 5; i++) {
      result.add(ArticleDto.builder().setArticleId(i + 1).build());
    }
    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class), anySet()))
        .thenReturn(result);

    mockMvc.perform(post("/article/commentSearch")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(5)))
        .andExpect(jsonPath("$.list[0].articleId", is(1)))
        .andExpect(jsonPath("$.list[1].articleId", is(2)))
        .andExpect(jsonPath("$.list[2].articleId", is(3)))
        .andExpect(jsonPath("$.list[3].articleId", is(4)))
        .andExpect(jsonPath("$.list[4].articleId", is(5)));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor
        .forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertEquals(Boolean.TRUE, articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(ArticleType.COMMENT.ordinal()),
        articleCriteriaCaptor.getValue().type);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testCommentSearchByAdmin() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new LinkedList<>();
    for (int i = 0; i < 5; i++) {
      result.add(ArticleDto.builder().setArticleId(i + 1).build());
    }
    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class), anySet()))
        .thenReturn(result);

    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(post("/article/commentSearch")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(5)))
        .andExpect(jsonPath("$.list[0].articleId", is(1)))
        .andExpect(jsonPath("$.list[1].articleId", is(2)))
        .andExpect(jsonPath("$.list[2].articleId", is(3)))
        .andExpect(jsonPath("$.list[3].articleId", is(4)))
        .andExpect(jsonPath("$.list[4].articleId", is(5)));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor
        .forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertNull(articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(ArticleType.COMMENT.ordinal()),
        articleCriteriaCaptor.getValue().type);
  }

  @Test
  public void testCommentSearchWithAppException() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();

    when(articleService.count(any(ArticleCriteria.class))).thenThrow(
        new AppException("error message"));

    mockMvc.perform(post("/article/commentSearch")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("error message")));
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testSearchSuccessful() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new LinkedList<>();
    for (int i = 0; i < 5; i++) {
      result.add(ArticleDto.builder().setArticleId(i + 1).setContent("123").build());
    }

    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class), anySet()))
        .thenReturn(result);

    mockMvc.perform(post("/article/search")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(5)))
        .andExpect(jsonPath("$.list[0].articleId", is(1)))
        .andExpect(jsonPath("$.list[1].articleId", is(2)))
        .andExpect(jsonPath("$.list[2].articleId", is(3)))
        .andExpect(jsonPath("$.list[3].articleId", is(4)))
        .andExpect(jsonPath("$.list[4].articleId", is(5)));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor
        .forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertEquals(Boolean.TRUE, articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().problemId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().contestId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().parentId);
  }

  @SuppressWarnings("unchecked")
  @Test
  public void testSearchByAdminSuccessful() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new LinkedList<>();
    for (int i = 0; i < 5; i++) {
      result.add(ArticleDto.builder().setArticleId(i + 1).setContent("123").build());
    }

    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class), anySet()))
        .thenReturn(result);

    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(post("/article/search")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(5)))
        .andExpect(jsonPath("$.list[0].articleId", is(1)))
        .andExpect(jsonPath("$.list[1].articleId", is(2)))
        .andExpect(jsonPath("$.list[2].articleId", is(3)))
        .andExpect(jsonPath("$.list[3].articleId", is(4)))
        .andExpect(jsonPath("$.list[4].articleId", is(5)));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor
        .forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertNull(articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().problemId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().contestId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().parentId);
  }

  @Test
  public void testSearchWithAppException() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();

    when(articleService.count(any(ArticleCriteria.class))).thenThrow(
        new AppException("error message"));

    mockMvc.perform(post("/article/search")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("error message")));
  }

  @Test
  public void testOperationWithoutLogin() throws Exception {
    mockMvc.perform(get("/article/applyOperation/{id}/{field}/{value}", 1, "isVisible", "false"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("Please login first.")));
  }

  @Test
  public void testOperationSuccessful() throws Exception {
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(get("/article/applyOperation/{id}/{field}/{value}", 1, "isVisible", "false")
        .sessionAttr("currentUser", currentUserDto))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
  }

  @Test
  public void testOperationWithAppException() throws Exception {
    doThrow(new AppException("error message")).when(articleService).applyOperation(anyString(),
        anyString(), anyString());
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(get("/article/applyOperation/{id}/{field}/{value}", 1, "isVisible", "false")
        .sessionAttr("currentUser", currentUserDto))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("error message")));
  }

  @Test
  public void testChangeNoticeOrderSuccessful() throws Exception {
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        ArticleDto.builder()
            .setArticleId(1)
            .setType(ArticleType.ARTICLE.ordinal())
            .build()
    );
    when(articleService.getArticleDto(2, ArticleFields.ALL_FIELDS)).thenReturn(
        ArticleDto.builder()
            .setArticleId(2)
            .build()
    );
    when(articleService.getArticleDto(3, ArticleFields.ALL_FIELDS)).thenReturn(
        ArticleDto.builder()
            .setArticleId(3)
            .build()
    );

    mockMvc.perform(post("/article/changeNoticeOrder")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content("{\"order\":\"3,2,1\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));

    ArgumentCaptor<ArticleDto> updateArticleCaptor = ArgumentCaptor.forClass(ArticleDto.class);
    verify(articleService, times(3)).updateArticle(updateArticleCaptor.capture());
    Assert.assertEquals(updateArticleCaptor.getAllValues().size(), 3);
    Assert.assertEquals(updateArticleCaptor.getAllValues().get(0).getArticleId(),
        Integer.valueOf(3));
    Assert.assertEquals(updateArticleCaptor.getAllValues().get(0).getOrder(), Integer.valueOf(0));
    Assert.assertEquals(updateArticleCaptor.getAllValues().get(1).getArticleId(),
        Integer.valueOf(2));
    Assert.assertEquals(updateArticleCaptor.getAllValues().get(1).getOrder(), Integer.valueOf(1));
    Assert.assertEquals(updateArticleCaptor.getAllValues().get(2).getArticleId(),
        Integer.valueOf(1));
    Assert.assertEquals(updateArticleCaptor.getAllValues().get(2).getOrder(), Integer.valueOf(2));
  }

  @Test
  public void testChangeNoticeOrderWithEmptyString() throws Exception {
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(post("/article/changeNoticeOrder")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content("{\"order\":\"\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
  }

  @Test
  public void testChangeNoticeOrderWithNoneInteger() throws Exception {
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(post("/article/changeNoticeOrder")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content("{\"order\":\"a\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("Article ID format error.")));
  }

  @Test
  public void testChangeNoticeOrderWithInexistentArticle() throws Exception {
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(null);
    mockMvc.perform(post("/article/changeNoticeOrder")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content("{\"order\":\"1\"}"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("No such article.")));
  }

  private final ArticleDto articleDtoInCommentEditTest = ArticleDto.builder()
      .setArticleId(1)
      .setTitle("Comment")
      .setContent("old content")
      .setProblemId(1)
      .setUserId(100)
      .build();

  @Test
  public void testEditCommentSuccessful() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    ArticleDto articleEditDto = ArticleDto.builder()
        .setArticleId(1)
        .setContent("content")
        .setProblemId(1)
        .setType(ArticleType.COMMENT.ordinal())
        .build();
    jsonData.put("articleEditDto", articleEditDto);
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        articleDtoInCommentEditTest
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.articleId", is(1)));

    ArgumentCaptor<ArticleDto> articleDtoArgumentCaptor = ArgumentCaptor.forClass(ArticleDto.class);
    verify(articleService, times(1)).updateArticle(articleDtoArgumentCaptor.capture());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getTitle(), "Comment");
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getContent(),
        articleEditDto.getContent());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getType(),
        Integer.valueOf(ArticleType.COMMENT.ordinal())
    );
    Assert
        .assertEquals(articleDtoArgumentCaptor.getValue().getUserId(), currentUserDto.getUserId());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getProblemId(),
        articleEditDto.getProblemId());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getContestId(),
        articleEditDto.getContestId());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getParentId(),
        articleEditDto.getParentId());
  }

  @Test
  public void testEditCommentWithWrongUser() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    jsonData.put("articleEditDto",
        ArticleDto.builder()
            .setArticleId(1)
            .setContent("content")
            .setProblemId(1)
            .setType(ArticleType.COMMENT.ordinal())
            .build()
    );
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(99)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        articleDtoInCommentEditTest
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("Permission denied")));
  }

  @Test
  public void testEditCommentWithEmptyContent() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    jsonData.put("articleEditDto",
        ArticleDto.builder()
            .setArticleId(1)
            .setContent("")
            .setProblemId(1)
            .setType(ArticleType.COMMENT.ordinal())
            .build()
    );
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        articleDtoInCommentEditTest
    );
    mockMvc
        .perform(post("/article/edit")
            .sessionAttr("currentUser", currentUserDto)
            .contentType(APPLICATION_JSON_UTF8)
            .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(
            jsonPath("$.error_msg", is("Comment should contain no more than 233 characters.")));
  }

  @Test
  public void testEditCommentWithNullContent() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    jsonData.put("articleEditDto",
        ArticleDto.builder()
            .setArticleId(1)
            .setProblemId(1)
            .setType(ArticleType.COMMENT.ordinal())
            .build()
    );
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        articleDtoInCommentEditTest
    );
    mockMvc
        .perform(post("/article/edit")
            .sessionAttr("currentUser", currentUserDto)
            .contentType(APPLICATION_JSON_UTF8)
            .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(
            jsonPath("$.error_msg", is("Comment should contain no more than 233 characters.")));
  }

  @Test
  public void testEditCommentWithLongContent() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    jsonData.put("articleEditDto",
        ArticleDto.builder()
            .setArticleId(1)
            .setContent(StringUtil.repeat("a", 234))
            .setProblemId(1)
            .setType(ArticleType.COMMENT.ordinal())
            .build()
    );
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        articleDtoInCommentEditTest
    );
    mockMvc
        .perform(post("/article/edit")
            .sessionAttr("currentUser", currentUserDto)
            .contentType(APPLICATION_JSON_UTF8)
            .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(
            jsonPath("$.error_msg", is("Comment should contain no more than 233 characters.")));
  }

  @Test
  public void testEditCommentWithInexistentArticle() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    jsonData.put("articleEditDto",
        ArticleDto.builder()
            .setArticleId(1)
            .setContent("content")
            .setProblemId(1)
            .setType(ArticleType.COMMENT.ordinal())
            .build()
    );
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        null
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("No such article.")));
  }

  @Test
  public void testCreateCommentSuccessful() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "new");
    ArticleDto articleEditDto = ArticleDto.builder()
        .setContent("content")
        .setProblemId(1)
        .setType(ArticleType.COMMENT.ordinal())
        .build();
    jsonData.put("articleEditDto", articleEditDto);
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.createNewArticle(currentUserDto.getUserId())).thenReturn(1);
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        articleDtoInCommentEditTest
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.articleId", is(1)));
  }

  @Test
  public void testCreateCommentWithCreateFailed() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "new");
    ArticleDto articleEditDto = ArticleDto.builder()
        .setArticleId(1)
        .setContent("content")
        .setProblemId(1)
        .setType(ArticleType.COMMENT.ordinal())
        .build();
    jsonData.put("articleEditDto", articleEditDto);
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.createNewArticle(currentUserDto.getUserId())).thenReturn(1);
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        null
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("Error while creating article.")));
  }

  @Test
  public void testEditArticleSuccessful() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    ArticleDto articleEditDto = ArticleDto.builder()
        .setArticleId(1)
        .setContent("AAAAAAAAAAAAABBBBBBBBBB")
        .setTitle("Hello world!")
        .setType(ArticleType.ARTICLE.ordinal())
        .setUserId( 100 )
        .build();
    jsonData.put("articleEditDto", articleEditDto);
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        ArticleDto.builder()
            .setArticleId(1)
            .setTitle("Old title")
            .setContent("Old content")
            .setUserId(100)
            .setType(ArticleType.ARTICLE.ordinal())
            .build()
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.articleId", is(1)));

    ArgumentCaptor<ArticleDto> articleDtoArgumentCaptor = ArgumentCaptor.forClass(ArticleDto.class);
    verify(articleService, times(1)).updateArticle(articleDtoArgumentCaptor.capture());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getTitle(), articleEditDto.getTitle());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getContent(),
        articleEditDto.getContent());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getType(),
        Integer.valueOf(ArticleType.ARTICLE.ordinal())
    );
    Assert
        .assertEquals(articleDtoArgumentCaptor.getValue().getUserId(), currentUserDto.getUserId());
  }

  @Test
  public void testEditArticleWithEmptyTitle() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    ArticleDto articleEditDto = ArticleDto.builder()
        .setArticleId(1)
        .setContent("AAAAAAAAAAAAABBBBBBBBBB")
        .setTitle("")
        .setType(ArticleType.ARTICLE.ordinal())
        .build();
    jsonData.put("articleEditDto", articleEditDto);
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        ArticleDto.builder()
            .setArticleId(1)
            .setTitle("Old title")
            .setContent("Old content")
            .setUserId(100)
            .setType(ArticleType.ARTICLE.ordinal())
            .build()
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("field_error")))
        .andExpect(jsonPath("$.field", hasSize(1)))
        .andExpect(jsonPath("$.field[0].field", is("title")))
        .andExpect(jsonPath("$.field[0].defaultMessage",
            is("Please enter a validate title.")));
  }

  @Test
  public void testEditArticleTypeByAdmin() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    ArticleDto articleEditDto = ArticleDto.builder()
        .setArticleId(1)
        .setContent("AAAAAAAAAAAAABBBBBBBBBB")
        .setTitle("Hello world!")
        .setType(ArticleType.NOTICE.ordinal())
        .build();
    jsonData.put("articleEditDto", articleEditDto);
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        ArticleDto.builder()
            .setArticleId(1)
            .setTitle("Old title")
            .setContent("Old content")
            .setUserId(100)
            .setType(ArticleType.ARTICLE.ordinal())
            .build()
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.articleId", is(1)));

    ArgumentCaptor<ArticleDto> articleDtoArgumentCaptor = ArgumentCaptor.forClass(ArticleDto.class);
    verify(articleService, times(1)).updateArticle(articleDtoArgumentCaptor.capture());
    Assert.assertEquals(articleDtoArgumentCaptor.getValue().getType(), articleEditDto.getType());
  }

  @Test
  public void testEditArticleTypeByNoneAdmin() throws Exception {
    Map<String, Object> jsonData = new HashMap<>();
    jsonData.put("action", "edit");
    ArticleDto articleEditDto = ArticleDto.builder()
        .setArticleId(1)
        .setContent("AAAAAAAAAAAAABBBBBBBBBB")
        .setTitle("Hello world!")
        .setType(ArticleType.NOTICE.ordinal())
        .setUserId(100)
        .build();
    jsonData.put("articleEditDto", articleEditDto);
    String jsonDataString = JSON.toJSONString(jsonData);
    UserDto currentUserDto = UserDto.builder()
        .setType(AuthenticationType.NORMAL.ordinal())
        .setUserId(100)
        .build();
    when(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)).thenReturn(
        ArticleDto.builder()
            .setArticleId(1)
            .setTitle("Old title")
            .setContent("Old content")
            .setUserId(100)
            .setType(ArticleType.ARTICLE.ordinal())
            .build()
    );
    mockMvc.perform(post("/article/edit")
        .sessionAttr("currentUser", currentUserDto)
        .contentType(APPLICATION_JSON_UTF8)
        .content(jsonDataString))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.articleId", is(1)));

    ArgumentCaptor<ArticleDto> articleDtoArgumentCaptor = ArgumentCaptor.forClass(ArticleDto.class);
    verify(articleService, times(1)).updateArticle(articleDtoArgumentCaptor.capture());
    Assert.assertNotEquals(articleDtoArgumentCaptor.getValue().getType(), articleEditDto.getType());
  }
}


