package cn.edu.uestc.acmicpc.service;

import static com.google.common.truth.Truth.assertThat;

import cn.edu.uestc.acmicpc.db.criteria.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.service.testing.ArticleProvider;
import cn.edu.uestc.acmicpc.service.testing.ContestProvider;
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

  @Autowired
  private ArticleProvider articleProvider;

  @Autowired
  private ContestProvider contestProvider;

  @Test
  public void testGetArticleDto() throws AppException {
    Integer articleId = articleService.createNewArticle(getTestUserId());
    Assert.assertEquals(
        articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS).getArticleId(),
        articleId);
  }

  @Test
  public void testGetArticleDto_noSuchArticle() throws AppException {
    Assert.assertNull(articleService.getArticleDto(1234, ArticleFields.ALL_FIELDS));
  }

  @Test
  public void testCheckArticleExists_true() throws AppException {
    ArticleDto article = articleProvider.createArticle();
    Assert.assertTrue(articleService.checkArticleExists(article.getArticleId()));
  }

  @Test
  public void testCheckArticleExists_false() throws AppException {
    Assert.assertFalse(articleService.checkArticleExists(1234));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(5);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleIds[1];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(4L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(5);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.endId = articleIds[1];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(5);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleIds[1];
    articleCriteria.endId = articleIds[2];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(10);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleIds[4];
    articleCriteria.endId = articleIds[3];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byTitle() throws AppException {
    ArticleDto article = articleProvider.createArticle();
    article.setTitle("About");
    articleService.updateArticle(article);
    articleProvider.createArticles(5);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "About";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(1L));
  }

  @Test
  public void testCount_byTitle_emptyResult() throws AppException {
    ArticleDto article = articleProvider.createArticle();
    article.setTitle("About");
    articleService.updateArticle(article);
    articleProvider.createArticles(5);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "About 1";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byType() throws AppException {
    ArticleDto article1 = articleProvider.createArticle();
    article1.setType(ArticleType.ARTICLE.ordinal());
    articleService.updateArticle(article1);
    ArticleDto article2 = articleProvider.createArticle();
    article2.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article2);
    ArticleDto article3 = articleProvider.createArticle();
    article3.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article3);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.type = ArticleType.NOTICE.ordinal();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byType_emptyResult() throws AppException {
    ArticleDto article1 = articleProvider.createArticle();
    article1.setType(ArticleType.ARTICLE.ordinal());
    articleService.updateArticle(article1);
    ArticleDto article2 = articleProvider.createArticle();
    article2.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article2);
    ArticleDto article3 = articleProvider.createArticle();
    article3.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article3);

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
    ArticleDto article1 = articleProvider.createArticle();
    ArticleDto article2 = articleProvider.createArticle();
    ContestDto contest = contestProvider.createContest();
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.contestId = contest.getContestId();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation(
        "contestId",
        article1.getArticleId() + ", " + article2.getArticleId(),
        contest.getContestId().toString());
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
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
    ArticleDto article = articleProvider.createArticle();
    Integer beforeIncrement = article.getClicked();
    articleService.incrementClicked(article.getArticleId());
    Integer afterIncrement = articleService.getArticleDto(
        article.getArticleId(), ArticleFields.ALL_FIELDS).getClicked();
    Assert.assertEquals(Integer.valueOf(beforeIncrement + 1), afterIncrement);
  }

  @Test
  public void testUpdateArticle_title() throws AppException {
    ArticleDto article = articleProvider.createArticle();
    String titleToUpdate = "Hello world!";
    String titleOrigin = article.getTitle();
    Assert.assertNotEquals(titleOrigin, titleToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setTitle(titleToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(
        article.getArticleId(), ArticleFields.ALL_FIELDS).getTitle()).isEqualTo(titleToUpdate);
  }

  @Test
  public void testUpdateArticle_parentId() throws AppException {
    ArticleDto parent = articleProvider.createArticle();
    ArticleDto child = articleProvider.createArticle();
    child.setParentId(parent.getArticleId());
    articleService.updateArticle(child);
    ArticleDto newParent = articleProvider.createArticle();
    Integer parentIdToUpdate = newParent.getArticleId();
    Integer parentIdOrigin = child.getParentId();
    Assert.assertNotEquals(parentIdOrigin, parentIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(child.getArticleId())
        .setParentId(parentIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(
        child.getArticleId(), ArticleFields.ALL_FIELDS).getParentId()).isEqualTo(parentIdToUpdate);
  }

  @Test
  public void testUpdateArticle_type() throws AppException {
    ArticleDto article = articleProvider.createArticle();
    article.setType(2);
    articleService.updateArticle(article);
    Integer typeToUpdate = 1;
    Integer typeOrigin = article.getType();
    Assert.assertNotEquals(typeOrigin, typeToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setType(typeToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(1, ArticleFields.ALL_FIELDS).getType())
        .isEqualTo(typeToUpdate);
  }

  @Test
  public void testUpdateArticle_content() throws AppException {
    ArticleDto article = articleProvider.createArticle();
    String contentToUpdate = "content";
    String contentOrigin = article.getContent();
    Assert.assertNotEquals(contentOrigin, contentToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setContent(contentToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(
        article.getArticleId(), ArticleFields.ALL_FIELDS).getContent()).isEqualTo(contentToUpdate);
  }

  @Test
  public void testUpdateArticle_clicked() throws AppException {
    ArticleDto article = articleProvider.createArticle();
    Integer clickedToUpdate = 15;
    Integer clickedOrigin = article.getClicked();
    Assert.assertNotEquals(clickedOrigin, clickedToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setClicked(clickedToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(
        article.getArticleId(), ArticleFields.ALL_FIELDS).getClicked()).isEqualTo(clickedToUpdate);
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
    Integer userId = getTestUserId();
    for (int i = 0; i < 4; i++) {
      articleService.createNewArticle(userId);
    }
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.userId = userId;
    PageInfo pageInfo = PageInfo.create(300L, 3L, 10, 2L);
    List<ArticleDto> articles = articleService.getArticleList(articleCriteria, pageInfo,
        ArticleFields.FIELDS_FOR_LIST_PAGE);
    assertThat(articles).hasSize(1);
  }

  @Test
  public void testGetArticleList_withPageInfo_emptyResult() throws AppException {
    Integer userId = getTestUserId();
    for (int i = 0; i < 4; i++) {
      System.err.println("added: " + articleService.createNewArticle(userId));
    }
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.userId = userId;
    PageInfo pageInfo = PageInfo.create(300L, 3L, 10, 3L);
    List<ArticleDto> articles = articleService.getArticleList(articleCriteria, pageInfo,
        ArticleFields.FIELDS_FOR_LIST_PAGE);
    articles.forEach(a -> System.err.println(a.getArticleId()));
    assertThat(articles).isEmpty();
  }
}