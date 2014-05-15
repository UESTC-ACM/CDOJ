package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.criteria.impl.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.criteria.transformer.AliasToProtocolBufferBuilderTransformer;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.ArticleDtoProtos;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for criteria entities.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class CriteriaITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  IArticleDAO articleDAO;

  @Test
  public void test_fetch_list_successful() throws AppException {
    // Build criteria manually
    DetachedCriteria criteria = DetachedCriteria.forClass(Article.class);
    criteria.createAlias("userByUserId", "owner");

    criteria.setProjection(Projections.projectionList()
            .add(Projections.property("articleId"), "articleId")
            .add(Projections.property("title"), "title")
            .add(Projections.property("time"), "time")
            .add(Projections.property("type"), "type")
            .add(Projections.property("owner.userName"), "ownerName")
            .add(Projections.property("owner.email"), "ownerEmail")
    );
    criteria.setResultTransformer(new AliasToProtocolBufferBuilderTransformer(ArticleDtoProtos.ArticleDto.class));

    List<ArticleDtoProtos.ArticleDto> result = articleDAO.list(criteria, null);
    Assert.assertEquals(result.size(), 4);
    Assert.assertEquals(result.get(0).getArticleId(), 1);
    Assert.assertEquals(result.get(0).getTitle(), "Frequently Asked Questions");
    Assert.assertNotNull(result.get(0).getTime());
    Assert.assertEquals(result.get(0).getType(), ArticleDtoProtos.ArticleDto.ArticleType.ARTICLE);
    Assert.assertEquals(result.get(0).getOwnerName(), "administrator");
    Assert.assertEquals(result.get(0).getOwnerEmail(), "acm@uestc.edu.cn");
    // Empty fields.
    Assert.assertEquals(result.get(0).hasContent(), false);
    Assert.assertEquals(result.get(0).hasClicked(), false);
    Assert.assertEquals(result.get(0).hasOrder(), false);
    Assert.assertEquals(result.get(0).hasIsVisible(), false);
    Assert.assertEquals(result.get(0).hasParentId(), false);
    Assert.assertEquals(result.get(0).hasProblemId(), false);
    Assert.assertEquals(result.get(0).hasContestId(), false);
    Assert.assertEquals(result.get(0).hasUserId(), false);
  }

  @Test
  public void test_fetch_article_list_by_id_range_successful() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria(ArticleFields.ALL);

    articleCriteria.startId = 2;
    articleCriteria.endId = 3;

    List<ArticleDtoProtos.ArticleDto> result = articleDAO.list(articleCriteria.getCriteria(), null);

    Assert.assertEquals(result.size(), 2);
    Assert.assertEquals(result.get(0).getArticleId(), 2);
    Assert.assertEquals(result.get(1).getArticleId(), 3);
  }
}
