package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.criteria.transformer.AliasToProtocolBufferBuilderTransformer;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.ArticleDtoProtos;
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
    DetachedCriteria criteria = DetachedCriteria.forClass(Article.class, "article");

    criteria.setProjection(Projections.projectionList()
            .add(Projections.property("article.articleId"), "articleId")
            .add(Projections.property("article.title"), "title")
    );
    criteria.setResultTransformer(new AliasToProtocolBufferBuilderTransformer(ArticleDtoProtos.ArticleDto.class));

    List<ArticleDtoProtos.ArticleDto> result = articleDAO.list(criteria, null);
    Assert.assertEquals(result.size(), 4);
    Assert.assertEquals(result.get(0).getArticleId(), 1);
    Assert.assertEquals(result.get(0).getTitle(), "Frequently Asked Questions");
    // Empty fields.
    Assert.assertEquals(result.get(0).hasContent(), false);
    Assert.assertEquals(result.get(0).hasTime(), false);
    Assert.assertEquals(result.get(0).hasClicked(), false);
    Assert.assertEquals(result.get(0).hasOrder(), false);
    Assert.assertEquals(result.get(0).hasType(), false);
    Assert.assertEquals(result.get(0).hasIsVisible(), false);
    Assert.assertEquals(result.get(0).hasParentId(), false);
    Assert.assertEquals(result.get(0).hasProblemId(), false);
    Assert.assertEquals(result.get(0).hasContestId(), false);
    Assert.assertEquals(result.get(0).hasUserId(), false);
  }

}
