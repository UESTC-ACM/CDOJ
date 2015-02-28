package cn.edu.uestc.acmicpc.service;

import cn.edu.uestc.acmicpc.db.criteria.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.ArticleType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.sql.Timestamp;
import java.util.List;

/**
 * Integration test cases for
 * {@link cn.edu.uestc.acmicpc.service.iface.ArticleService}.
 */
public class ArticleServiceITTest extends PersistenceITTest {

  @Autowired
  private ArticleService articleService;

  @Test
  public void testGetArticleDto() throws AppException {
    Integer articleId = 2;
    Assert.assertEquals(articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS)
        .getArticleId(), Integer.valueOf(2));
  }

  @Test
  public void testGetArticleDto_noSuchArticle() throws AppException {
    Integer articleId = 10;
    Assert.assertNull(articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS));
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
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 2;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(3L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.endId = 2;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 2;
    articleCriteria.endId = 3;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = 5;
    articleCriteria.endId = 4;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byTitle() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "About";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(1L));
  }

  @Test
  public void testCount_byTitle_emptyResult() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "About 1";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byType() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.type = ArticleType.NOTICE.ordinal();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(4L));
  }

  @Test
  public void testCount_byType_emptyResult() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.type = ArticleType.COMMENT.ordinal();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testOperator_title() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "new title";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation("title", "1, 2", "new title");
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
    articleService.applyOperation("title", "1", "Frequently Asked Questions");
    articleService.applyOperation("title", "2", "Markdown syntax cheatsheet");
  }

  @Test
  public void testOperator_contestId() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.contestId = 1;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation("contestId", "1, 2", "1");
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
    articleService.applyOperation("contestId", "1, 2", null);
  }

  @Test
  public void testOperator_contestId_noSuchContest() throws AppException {
    try {
      articleService.applyOperation("contestId", "3", "5");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_problemId() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.problemId = 1;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation("problemId", "1, 2", "1");
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
    articleService.applyOperation("problemId", "1, 2", null);
  }

  @Test
  public void testOperator_contestId_noSuchProblem() throws AppException {
    try {
      articleService.applyOperation("problemId", "3", "10");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_parentId() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.parentId = 1;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation("parentId", "3, 4", "1");
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
    articleService.applyOperation("parentId", "3, 4", null);
  }

  @Test
  public void testOperator_parentId_noSuchParent() throws AppException {
    try {
      articleService.applyOperation("parentId", "3", "5");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_userId() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.userId = 2;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation("userId", "3, 4", "2");
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
    articleService.applyOperation("userId", "3, 4", "1");
  }

  @Test
  public void testOperator_userId_noSuchUser() throws AppException {
    try {
      articleService.applyOperation("userId", "3", "10");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testIncrementClicked() throws AppException {
    Integer articleId = 1;
    Integer beforeIncrement = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS)
        .getClicked();
    articleService.incrementClicked(articleId);
    Integer afterIncrement = articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS)
        .getClicked();
    Assert.assertEquals(Integer.valueOf(beforeIncrement + 1), afterIncrement);
  }

  @Test
  public void testUpdateArticle_title() throws AppException {
    String titleToUpdate = "Hello world!";
    String titleOrigin = articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getTitle();
    Assert.assertNotEquals(titleOrigin, titleToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(1)
        .setTitle(titleToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getTitle(),
        titleToUpdate);
    articleDto.setTitle(titleOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testUpdateArticle_parentId() throws AppException {
    Integer parentIdToUpdate = 1;
    Integer parentIdOrigin = articleService.getArticleDto(3, ArticleFields.ALL_FIELDS)
        .getParentId();
    Assert.assertNotEquals(parentIdOrigin, parentIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(3)
        .setParentId(parentIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getParentId(),
        parentIdToUpdate);
    articleService.applyOperation("parentId", "3", null);
  }

  @Test
  public void testUpdateArticle_type() throws AppException {
    Integer typeToUpdate = 1;
    Integer typeOrigin = articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getType();
    Assert.assertNotEquals(typeOrigin, typeToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(1)
        .setType(typeToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getType(),
        typeToUpdate);
    articleDto.setType(typeOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testUpdateArticle_content() throws AppException {
    String contentToUpdate = "content";
    String contentOrigin = articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getContent();
    Assert.assertNotEquals(contentOrigin, contentToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(1)
        .setContent(contentToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getContent(),
        contentToUpdate);
    articleDto.setContent(contentOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testUpdateArticle_clicked() throws AppException {
    Integer clickedToUpdate = 15;
    Integer clickedOrigin = articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getClicked();
    Assert.assertNotEquals(clickedOrigin, clickedToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(1)
        .setClicked(clickedToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getClicked(),
        clickedToUpdate);
    articleDto.setClicked(clickedOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testUpdateArticle_isVisible() throws AppException {
    Boolean isVisibleToUpdate = false;
    Boolean isVisibleOrigin = articleService.getArticleDto(1, ArticleFields.ALL_FIELDS)
        .getIsVisible();
    Assert.assertNotEquals(isVisibleOrigin, isVisibleToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(1)
        .setIsVisible(isVisibleToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getIsVisible(),
        isVisibleToUpdate);
    articleDto.setIsVisible(isVisibleOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testUpdateArticle_problemId() throws AppException {
    Integer problemIdToUpdate = 1;
    Integer problemIdOrigin = articleService.getArticleDto(3, ArticleFields.ALL_FIELDS)
        .getProblemId();
    Assert.assertNotEquals(problemIdOrigin, problemIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(3)
        .setProblemId(problemIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getProblemId(),
        problemIdToUpdate);
    articleService.applyOperation("problemId", "3", null);
  }

  @Test
  public void testUpdateArticle_userId() throws AppException {
    Integer userIdToUpdate = 2;
    Integer userIdOrigin = articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getUserId();
    Assert.assertNotEquals(userIdOrigin, userIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(3)
        .setUserId(userIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getUserId(),
        userIdToUpdate);
    articleDto.setUserId(userIdOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testUpdateArticle_contestId() throws AppException {
    Integer contestIdToUpdate = 1;
    Integer contestIdOrigin = articleService.getArticleDto(3, ArticleFields.ALL_FIELDS)
        .getContestId();
    Assert.assertNotEquals(contestIdOrigin, contestIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(3)
        .setContestId(contestIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getContestId(),
        contestIdToUpdate);
    articleService.applyOperation("contestId", "3", null);
  }

  @Test
  public void testUpdateArticle_order() throws AppException {
    Integer orderToUpdate = 1;
    Integer orderOrigin = articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getOrder();
    Assert.assertNotEquals(orderOrigin, orderToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(3)
        .setOrder(orderToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getOrder(),
        orderToUpdate);
    articleDto.setOrder(orderOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testUpdateArticle_time() throws AppException {
    Timestamp timeToUpdate = Timestamp.valueOf("2014-03-27 00:00:00");
    Timestamp timeOrigin = articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getTime();
    Assert.assertNotEquals(timeOrigin, timeToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(3)
        .setTime(timeToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(3, ArticleFields.ALL_FIELDS).getTime(),
        timeToUpdate);
    articleDto.setTime(timeOrigin);
    articleService.updateArticle(articleDto);
  }

  @Test
  public void testGetArticleList_withPageInfo() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.userId = 1;
    PageInfo pageInfo = PageInfo.create(300L, 3L, 10, 2L);
    List<ArticleDto> articleListDtos = articleService.getArticleList(articleCriteria, pageInfo,
        ArticleFields.FIELDS_FOR_LIST_PAGE);
    Assert.assertEquals(articleListDtos.size(), 1);
  }

  @Test
  public void testGetArticleList_withPageInfo_emptyResult() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.userId = 1;
    PageInfo pageInfo = PageInfo.create(300L, 3L, 10, 3L);
    List<ArticleDto> articleListDtos = articleService.getArticleList(articleCriteria, pageInfo,
        ArticleFields.FIELDS_FOR_LIST_PAGE);
    Assert.assertEquals(articleListDtos.size(), 0);
  }

  @Test
  public void testCreateArticle() throws AppException {
    Long exceptedId = articleService.count(new ArticleCriteria()) + 1;
    Integer newId = articleService.createNewArticle(1);
    Assert.assertEquals(newId.intValue(), exceptedId.intValue());
  }
}