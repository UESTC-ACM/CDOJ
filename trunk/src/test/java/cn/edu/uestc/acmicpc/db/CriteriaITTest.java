package cn.edu.uestc.acmicpc.db;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.criteria.impl.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.field.FieldProjection;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.util.exception.AppException;

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

  public void test_fetch_article_with_specified_fields() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria(new Fields() {
      @Override
      public FieldProjection[] getProjections() {
        FieldProjection[] projection = new FieldProjection[1];
        projection[0] = FieldProjection.Property("articleId");
        return projection;
      }
    });

    articleCriteria.startId = 1;
    articleCriteria.endId = 1;

    List<ArticleDto> result = articleDAO.findAll(articleCriteria.getCriteria(), null);
    Assert.assertEquals(result.size(), 1);
    ArticleDto articleDto = result.get(0);

    Assert.assertEquals(articleDto.getArticleId(), Integer.valueOf(1));
    Assert.assertNull(articleDto.getTitle());
    Assert.assertNull(articleDto.getContent());
    Assert.assertNull(articleDto.getTime());
    Assert.assertEquals(articleDto.getClicked(), Integer.valueOf(0));
    Assert.assertEquals(articleDto.getOrder(), Integer.valueOf(0));
    Assert.assertEquals(articleDto.getType(), Integer.valueOf(0));
    Assert.assertEquals(articleDto.getIsVisible(), Boolean.FALSE);
    Assert.assertNull(articleDto.getParentId());
    Assert.assertNull(articleDto.getProblemId());
    Assert.assertNull(articleDto.getContestId());
    Assert.assertEquals(articleDto.getUserId(), Integer.valueOf(1));
    Assert.assertNull(articleDto.getOwnerName());
    Assert.assertNull(articleDto.getOwnerEmail());
  }

  @Test
  public void test_fetch_article_list_by_id_range_successful() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria(ArticleFields.ALL);

    articleCriteria.startId = 2;
    articleCriteria.endId = 3;

    List<ArticleDto> result = articleDAO.findAll(articleCriteria.getCriteria(), null);

    Assert.assertEquals(result.size(), 2);
    Assert.assertEquals(result.get(0).getArticleId(), Integer.valueOf(2));
    Assert.assertEquals(result.get(1).getArticleId(), Integer.valueOf(3));
  }
}
