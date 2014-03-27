package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.List;

/**
 * Integration test cases for {@link cn.edu.uestc.acmicpc.service.iface.ArticleService}.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class ArticleServiceITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  private ArticleService articleService;

  @Test
  public void testGetArticleDTO() throws AppException {
    Integer articleId = 2;
    Assert.assertEquals(articleService.getArticleDTO(articleId).getArticleId(), Integer.valueOf(2));
  }

  @Test
  public void testGetArticleDTO_noSuchArticle() throws AppException {
    Integer articleId = 10;
    Assert.assertNull(articleService.getArticleDTO(articleId));
  }

  @Test
  public void testCheckArticleExists_true() throws AppException {
    Integer articleId = 1;
    Assert.assertTrue(articleService.checkArticleExists(articleId));
  }

  @Test
  public void testCheckArticleExists_false() throws AppException {
    Integer articleId = 10;
    Assert.assertFalse(articleService.checkArticleExists(articleId));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.startId = 2;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(3L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.endId = 2;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.startId = 2;
    articleCondition.endId = 3;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.startId = 5;
    articleCondition.endId = 4;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(0L));
  }

  @Test
  public void testCount_byTitle() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.title = "About";
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(1L));
  }

  @Test
  public void testCount_byTitle_emptyResult() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.title = "About 1";
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(0L));
  }

  @Test
  public void testOperator_title() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.title = "new title";
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(0L));
    articleService.operator("title", "1, 2", "new title");
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(2L));
    articleService.operator("title", "1", "Frequently Asked Questions");
    articleService.operator("title", "2", "Markdown syntax cheatsheet");
  }

  @Test
  public void testOperator_contestId() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.contestId = 1;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(0L));
    articleService.operator("contestId", "1, 2", "1");
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(2L));
    articleService.operator("contestId", "1, 2", null);
  }

  @Test
  public void testOperator_contestId_noSuchContest() throws AppException {
    try {
      articleService.operator("contestId", "3", "5");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_problemId() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.problemId = 1;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(0L));
    articleService.operator("problemId", "1, 2", "1");
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(2L));
    articleService.operator("problemId", "1, 2", null);
  }

  @Test
  public void testOperator_contestId_noSuchProblem() throws AppException {
    try {
      articleService.operator("problemId", "3", "10");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_parentId() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.parentId = 1;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(0L));
    articleService.operator("parentId", "3, 4", "1");
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(2L));
    articleService.operator("parentId", "3, 4", null);
  }

  @Test
  public void testOperator_parentId_noSuchParent() throws AppException {
    try {
      articleService.operator("parentId", "3", "5");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_userId() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.userId = 2;
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(0L));
    articleService.operator("userId", "3, 4", "2");
    Assert.assertEquals(articleService.count(articleCondition), Long.valueOf(2L));
    articleService.operator("userId", "3, 4", "1");
  }

  @Test
  public void testOperator_userId_noSuchUser() throws AppException {
    try {
      articleService.operator("userId", "3", "10");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testIncrementClicked() throws AppException {
    Integer articleId = 1;
    Integer beforeIncrement = articleService.getArticleDTO(articleId).getClicked();
    articleService.incrementClicked(articleId);
    Integer afterIncrement = articleService.getArticleDTO(articleId).getClicked();
    Assert.assertEquals(Integer.valueOf(beforeIncrement + 1), afterIncrement);
  }

  @Test
  public void testUpdateArticle_title() throws AppException {
    String titleToUpdate = "Hello world!";
    String titleOrigin = articleService.getArticleDTO(1).getTitle();
    Assert.assertNotEquals(titleOrigin, titleToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(1);
    articleDTO.setTitle(titleToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(1).getTitle(), titleToUpdate);
    articleDTO.setTitle(titleOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testUpdateArticle_parentId() throws AppException {
    Integer parentIdToUpdate = 1;
    Integer parentIdOrigin = articleService.getArticleDTO(3).getParentId();
    Assert.assertNotEquals(parentIdOrigin, parentIdToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(3);
    articleDTO.setParentId(parentIdToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(3).getParentId(), parentIdToUpdate);
    articleService.operator("parentId", "3", null);
  }

  @Test
  public void testUpdateArticle_type() throws AppException {
    Integer typeToUpdate = 1;
    Integer typeOrigin = articleService.getArticleDTO(1).getType();
    Assert.assertNotEquals(typeOrigin, typeToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(1);
    articleDTO.setType(typeToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(1).getType(), typeToUpdate);
    articleDTO.setType(typeOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testUpdateArticle_content() throws AppException {
    String contentToUpdate = "content";
    String contentOrigin = articleService.getArticleDTO(1).getContent();
    Assert.assertNotEquals(contentOrigin, contentToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(1);
    articleDTO.setContent(contentToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(1).getContent(), contentToUpdate);
    articleDTO.setContent(contentOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testUpdateArticle_clicked() throws AppException {
    Integer clickedToUpdate = 15;
    Integer clickedOrigin = articleService.getArticleDTO(1).getClicked();
    Assert.assertNotEquals(clickedOrigin, clickedToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(1);
    articleDTO.setClicked(clickedToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(1).getClicked(), clickedToUpdate);
    articleDTO.setClicked(clickedOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testUpdateArticle_isVisible() throws AppException {
    Boolean isVisibleToUpdate = false;
    Boolean isVisibleOrigin = articleService.getArticleDTO(1).getIsVisible();
    Assert.assertNotEquals(isVisibleOrigin, isVisibleToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(1);
    articleDTO.setIsVisible(isVisibleToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(1).getIsVisible(), isVisibleToUpdate);
    articleDTO.setIsVisible(isVisibleOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testUpdateArticle_problemId() throws AppException {
    Integer problemIdToUpdate = 1;
    Integer problemIdOrigin = articleService.getArticleDTO(3).getProblemId();
    Assert.assertNotEquals(problemIdOrigin, problemIdToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(3);
    articleDTO.setProblemId(problemIdToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(3).getProblemId(), problemIdToUpdate);
    articleService.operator("problemId", "3", null);
  }

  @Test
  public void testUpdateArticle_userId() throws AppException {
    Integer userIdToUpdate = 2;
    Integer userIdOrigin = articleService.getArticleDTO(3).getUserId();
    Assert.assertNotEquals(userIdOrigin, userIdToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(3);
    articleDTO.setUserId(userIdToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(3).getUserId(), userIdToUpdate);
    articleDTO.setUserId(userIdOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testUpdateArticle_contestId() throws AppException {
    Integer contestIdToUpdate = 1;
    Integer contestIdOrigin = articleService.getArticleDTO(3).getContestId();
    Assert.assertNotEquals(contestIdOrigin, contestIdToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(3);
    articleDTO.setContestId(contestIdToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(3).getContestId(), contestIdToUpdate);
    articleService.operator("contestId", "3", null);
  }

  @Test
  public void testUpdateArticle_order() throws AppException {
    Integer orderToUpdate = 1;
    Integer orderOrigin = articleService.getArticleDTO(3).getOrder();
    Assert.assertNotEquals(orderOrigin, orderToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(3);
    articleDTO.setOrder(orderToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(3).getOrder(), orderToUpdate);
    articleDTO.setOrder(orderOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testUpdateArticle_time() throws AppException {
    Timestamp timeToUpdate = Timestamp.valueOf("2014-03-27 00:00:00");
    Timestamp timeOrigin = articleService.getArticleDTO(3).getTime();
    Assert.assertNotEquals(timeOrigin, timeToUpdate);
    ArticleDTO articleDTO = new ArticleDTO();
    articleDTO.setArticleId(3);
    articleDTO.setTime(timeToUpdate);
    articleService.updateArticle(articleDTO);
    Assert.assertEquals(articleService.getArticleDTO(3).getTime(), timeToUpdate);
    articleDTO.setTime(timeOrigin);
    articleService.updateArticle(articleDTO);
  }

  @Test
  public void testGetDAO() throws AppException {
    Assert.assertTrue(articleService.getDAO() instanceof IArticleDAO);
  }

  @Test
  public void testGetArticleList_withPageInfo() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.userName = "administrator";
    PageInfo pageInfo = PageInfo.create(300L, 3L, 10, 2L);
    List<ArticleListDTO> articleListDTOs = articleService.getArticleList(articleCondition, pageInfo);
    Assert.assertEquals(articleListDTOs.size(), 1);
  }

  @Test
  public void testGetArticleList_withPageInfo_emptyResult() throws AppException {
    ArticleCondition articleCondition = new ArticleCondition();
    articleCondition.userName = "administrator";
    PageInfo pageInfo = PageInfo.create(300L, 3L, 10, 3L);
    List<ArticleListDTO> articleListDTOs = articleService.getArticleList(articleCondition, pageInfo);
    Assert.assertEquals(articleListDTOs.size(), 0);
  }
}