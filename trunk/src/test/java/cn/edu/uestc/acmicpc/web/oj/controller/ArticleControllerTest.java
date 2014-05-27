package cn.edu.uestc.acmicpc.web.oj.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import cn.edu.uestc.acmicpc.config.TestContext;
import cn.edu.uestc.acmicpc.config.WebMVCConfig;
import cn.edu.uestc.acmicpc.db.criteria.impl.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.dto.impl.user.UserDTO;
import cn.edu.uestc.acmicpc.util.enums.ArticleType;
import cn.edu.uestc.acmicpc.util.enums.AuthenticationType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import cn.edu.uestc.acmicpc.web.oj.controller.article.ArticleController;

import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Test cases for {@link ArticleController}.
 */
@WebAppConfiguration
@ContextConfiguration(classes = {TestContext.class, WebMVCConfig.class})
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
  public void testFetchArticleWithIncorrectType() throws Exception {
    ArticleDto articleDto = ArticleDto.builder()
        .setType(ArticleType.COMMENT.ordinal())
        .build();
    when(articleService.getArticleDto(100, ArticleFields.ALL_FIELDS))
        .thenReturn(articleDto);
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
    UserDTO currentUserDTO = UserDTO.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(get("/article/data/{articleId}", 100)
        .sessionAttr("currentUser", currentUserDTO))
        .andExpect(request().sessionAttribute("currentUser", currentUserDTO))
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
  public void testCommentSearchSuccessful() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new ArrayList<>(5);
    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class))).thenReturn(result);

    mockMvc.perform(post("/article/commentSearch")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(result.size())));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor.forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertEquals(Boolean.TRUE, articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(ArticleType.COMMENT.ordinal()), articleCriteriaCaptor.getValue().type);
  }

  @Test
  public void testCommentSearchByAdmin() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new ArrayList<>(5);
    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class))).thenReturn(result);

    UserDTO currentUserDTO = UserDTO.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(post("/article/commentSearch")
        .sessionAttr("currentUser", currentUserDTO)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(result.size())));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor.forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertNull(articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(ArticleType.COMMENT.ordinal()), articleCriteriaCaptor.getValue().type);
  }

  @Test
  public void testCommentSearchWithAppException() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();

    when(articleService.count(any(ArticleCriteria.class))).thenThrow(new AppException("error message"));

    mockMvc.perform(post("/article/commentSearch")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("error message")));
  }

  @Test
  public void testSearchSuccessful() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new ArrayList<>(5);
    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class))).thenReturn(result);

    mockMvc.perform(post("/article/search")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(result.size())));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor.forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertEquals(Boolean.TRUE, articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().problemId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().contestId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().parentId);
  }

  @Test
  public void testSearchByAdminSuccessful() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 1;
    articleCriteria.endId = 5;

    List<ArticleDto> result = new ArrayList<>(5);
    when(articleService.count(any(ArticleCriteria.class))).thenReturn(5L);
    when(articleService.getArticleList(any(ArticleCriteria.class), any(PageInfo.class))).thenReturn(result);

    UserDTO currentUserDTO = UserDTO.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(post("/article/search")
        .sessionAttr("currentUser", currentUserDTO)
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")))
        .andExpect(jsonPath("$.list", hasSize(result.size())));

    // Check ArticleCriteria argument in count method
    ArgumentCaptor<ArticleCriteria> articleCriteriaCaptor = ArgumentCaptor.forClass(ArticleCriteria.class);
    verify(articleService).count(articleCriteriaCaptor.capture());
    Assert.assertNull(articleCriteriaCaptor.getValue().isVisible);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().problemId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().contestId);
    Assert.assertEquals(Integer.valueOf(-1), articleCriteriaCaptor.getValue().parentId);
  }

  @Test
  public void testSearchWithAppException() throws Exception {
    ArticleCriteria articleCriteria = new ArticleCriteria();

    when(articleService.count(any(ArticleCriteria.class))).thenThrow(new AppException("error message"));

    mockMvc.perform(post("/article/search")
        .contentType(APPLICATION_JSON_UTF8)
        .content(JSON.toJSONBytes(articleCriteria)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("error message")));
  }

  @Test
  public void testOperationWithoutLogin() throws Exception {
    mockMvc.perform(get("/article/operation/{id}/{field}/{value}", 1, "isVisible", "false"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("Please login first.")));
  }

  @Test
  public void testOperationSuccessful() throws Exception {
    UserDTO currentUserDTO = UserDTO.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(get("/article/operation/{id}/{field}/{value}", 1, "isVisible", "false")
        .sessionAttr("currentUser", currentUserDTO))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("success")));
  }

  @Test
  public void testOperationWithAppException() throws Exception {
    doThrow(new AppException("error message")).when(articleService).operator(anyString(), anyString(), anyString());
    UserDTO currentUserDTO = UserDTO.builder()
        .setType(AuthenticationType.ADMIN.ordinal())
        .build();
    mockMvc.perform(get("/article/operation/{id}/{field}/{value}", 1, "isVisible", "false")
        .sessionAttr("currentUser", currentUserDTO))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.result", is("error")))
        .andExpect(jsonPath("$.error_msg", is("error message")));
  }
}
