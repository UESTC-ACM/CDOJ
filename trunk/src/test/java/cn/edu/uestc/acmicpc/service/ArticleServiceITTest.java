package cn.edu.uestc.acmicpc.service;

import static com.google.common.truth.Truth.assertThat;

import cn.edu.uestc.acmicpc.db.criteria.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.dto.impl.ContestDto;
import cn.edu.uestc.acmicpc.db.dto.impl.UserDto;
import cn.edu.uestc.acmicpc.db.dto.impl.problem.ProblemDto;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.enums.ArticleType;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.helper.ArrayUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Integration test cases for
 * {@link cn.edu.uestc.acmicpc.service.iface.ArticleService}.
 */
public class ArticleServiceITTest extends PersistenceITTest {

  @Autowired
  private ArticleService articleService;

  @Test
  public void testGetArticleDto() throws AppException {
    Integer articleId = articleService.createNewArticle(userProvider.createUser().getUserId());
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
    ArticleDto article = articleProvider.createArticle(testUserId);
    Assert.assertTrue(articleService.checkArticleExists(article.getArticleId()));
  }

  @Test
  public void testCheckArticleExists_false() throws AppException {
    Assert.assertFalse(articleService.checkArticleExists(1234));
  }

  @Test
  public void testCount_byStartId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 5);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleIds[1];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(4L));
  }

  @Test
  public void testCount_byEndId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 5);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.endId = articleIds[1];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 5);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleIds[1];
    articleCriteria.endId = articleIds[2];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byStartIdAndEndId_emptyResult() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 10);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleIds[4];
    articleCriteria.endId = articleIds[3];
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byTitle() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    article.setTitle("About");
    articleService.updateArticle(article);
    articleProvider.createArticles(testUserId, 5);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "About";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(1L));
  }

  @Test
  public void testCount_byTitle_emptyResult() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    article.setTitle("About");
    articleService.updateArticle(article);
    articleProvider.createArticles(testUserId, 5);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "About 1";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testCount_byType() throws AppException {
    ArticleDto article1 = articleProvider.createArticle(testUserId);
    article1.setType(ArticleType.ARTICLE.ordinal());
    articleService.updateArticle(article1);
    ArticleDto article2 = articleProvider.createArticle(testUserId);
    article2.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article2);
    ArticleDto article3 = articleProvider.createArticle(testUserId);
    article3.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article3);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.type = ArticleType.NOTICE.ordinal();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testCount_byType_emptyResult() throws AppException {
    ArticleDto article1 = articleProvider.createArticle(testUserId);
    article1.setType(ArticleType.ARTICLE.ordinal());
    articleService.updateArticle(article1);
    ArticleDto article2 = articleProvider.createArticle(testUserId);
    article2.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article2);
    ArticleDto article3 = articleProvider.createArticle(testUserId);
    article3.setType(ArticleType.NOTICE.ordinal());
    articleService.updateArticle(article3);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.type = ArticleType.COMMENT.ordinal();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
  }

  @Test
  public void testOperator_title() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 2);

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.title = "new title";
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation("title", ArrayUtil.join(articleIds, ", "), "new title");
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testOperator_contestId() throws AppException {
    ArticleDto article1 = articleProvider.createArticle(testUserId);
    ArticleDto article2 = articleProvider.createArticle(testUserId);
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
    ArticleDto article = articleProvider.createArticle(testUserId);
    try {
      articleService.applyOperation("contestId", article.getArticleId().toString(), "5");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_problemId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 2);
    ProblemDto problem = problemProvider.createProblem();
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.problemId = 1;
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation(
        "problemId", ArrayUtil.join(articleIds, ", "), problem.getProblemId().toString());
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testOperator_contestId_noSuchProblem() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    try {
      articleService.applyOperation("problemId", article.getArticleId().toString(), "12345");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_parentId() throws AppException {
    ArticleDto parent = articleProvider.createArticle(testUserId);
    Integer[] articleIds = articleProvider.createArticles(testUserId, 2);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.parentId = parent.getArticleId();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation(
        "parentId", ArrayUtil.join(articleIds, ", "), parent.getArticleId().toString());
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testOperator_parentId_noSuchParent() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 2);
    try {
      articleService.applyOperation("parentId", ArrayUtil.join(articleIds, ", "), "12345");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testOperator_userId() throws AppException {
    Integer[] articleIds = articleProvider.createArticles(testUserId, 2);
    UserDto user = userProvider.createUser();

    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.userId = user.getUserId();
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(0L));
    articleService.applyOperation(
        "userId", ArrayUtil.join(articleIds, ", "), user.getUserId().toString());
    Assert.assertEquals(articleService.count(articleCriteria), Long.valueOf(2L));
  }

  @Test
  public void testOperator_userId_noSuchUser() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    try {
      articleService.applyOperation("userId", article.getArticleId().toString(), "12345");
      Assert.fail();
    } catch (AppException e) {
      Assert.assertEquals("Error while execute database query.", e.getMessage());
    }
  }

  @Test
  public void testIncrementClicked() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    Integer beforeIncrement = article.getClicked();
    articleService.incrementClicked(article.getArticleId());
    Integer afterIncrement = articleService.getArticleDto(
        article.getArticleId(), ArticleFields.ALL_FIELDS).getClicked();
    Assert.assertEquals(Integer.valueOf(beforeIncrement + 1), afterIncrement);
  }

  @Test
  public void testUpdateArticle_title() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
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
    ArticleDto parent = articleProvider.createArticle(testUserId);
    ArticleDto child = articleProvider.createArticle(testUserId);
    child.setParentId(parent.getArticleId());
    articleService.updateArticle(child);
    ArticleDto newParent = articleProvider.createArticle(testUserId);
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
    ArticleDto article = articleProvider.createArticle(testUserId);
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
    assertThat(articleService.getArticleDto(article.getArticleId(),
        ArticleFields.ALL_FIELDS).getType()).isEqualTo(typeToUpdate);
  }

  @Test
  public void testUpdateArticle_content() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
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
    ArticleDto article = articleProvider.createArticle(testUserId);
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
    ArticleDto article = articleProvider.createArticle(testUserId);
    Boolean isVisibleOrigin = article.getIsVisible();
    assertThat(isVisibleOrigin).isTrue();
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setIsVisible(false)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(article.getArticleId(),
        ArticleFields.ALL_FIELDS).getIsVisible()).isFalse();
  }

  @Test
  public void testUpdateArticle_problemId() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    ProblemDto problem = problemProvider.createProblem();
    ProblemDto newProblem = problemProvider.createProblem();
    article.setProblemId(problem.getProblemId());
    articleService.updateArticle(article);

    Integer problemIdToUpdate = newProblem.getProblemId();
    Integer problemIdOrigin = problem.getProblemId();
    assertThat(problemIdOrigin).isNotEqualTo(problemIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setProblemId(problemIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(
        article.getArticleId(), ArticleFields.ALL_FIELDS).getProblemId())
        .isEqualTo(problemIdToUpdate);
  }

  @Test
  public void testUpdateArticle_userId() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    UserDto newUser = userProvider.createUser();
    Integer userIdToUpdate = newUser.getUserId();
    Integer userIdOrigin = article.getUserId();
    Assert.assertNotEquals(userIdOrigin, userIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setUserId(userIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(article.getArticleId(),
        ArticleFields.ALL_FIELDS).getUserId(), userIdToUpdate);
  }

  @Test
  public void testUpdateArticle_contestId() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    ContestDto origin = contestProvider.createContest();
    ContestDto updated = contestProvider.createContest();
    article.setContestId(origin.getContestId());
    articleService.updateArticle(article);
    Integer contestIdToUpdate = updated.getContestId();
    Integer contestIdOrigin = origin.getContestId();
    assertThat(contestIdOrigin).isNotEqualTo(contestIdToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setContestId(contestIdToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    assertThat(articleService.getArticleDto(article.getArticleId(),
        ArticleFields.ALL_FIELDS).getContestId()).isEqualTo(contestIdToUpdate);
  }

  @Test
  public void testUpdateArticle_order() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    Integer orderToUpdate = 12;
    Integer orderOrigin = article.getOrder();
    Assert.assertNotEquals(orderOrigin, orderToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setOrder(orderToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(article.getArticleId(),
        ArticleFields.ALL_FIELDS).getOrder(), orderToUpdate);
  }

  @Test
  public void testUpdateArticle_time() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);
    Timestamp timeToUpdate = Timestamp.valueOf("2014-03-27 00:00:00");
    Timestamp timeOrigin = article.getTime();
    Assert.assertNotEquals(timeOrigin, timeToUpdate);
    ArticleDto articleDto = ArticleDto.builder()
        .setArticleId(article.getArticleId())
        .setTime(timeToUpdate)
        .build();
    articleService.updateArticle(articleDto);
    Assert.assertEquals(articleService.getArticleDto(article.getArticleId(),
        ArticleFields.ALL_FIELDS).getTime(), timeToUpdate);
  }

  @Test
  public void testGetArticleList_withPageInfo() throws AppException {
    Integer userId = userProvider.createUser().getUserId();
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
    Integer userId = userProvider.createUser().getUserId();
    for (int i = 0; i < 4; i++) {
      articleService.createNewArticle(userId);
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