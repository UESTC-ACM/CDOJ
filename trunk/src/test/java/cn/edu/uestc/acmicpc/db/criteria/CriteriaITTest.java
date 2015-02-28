package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dao.iface.ArticleDao;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

import java.util.List;

/**
 * Test cases for criteria entities.
 */
public class CriteriaITTest extends PersistenceITTest {

  @Autowired
  ArticleDao articleDao;

  public void testFetchArticle_withSpecifiedFields() throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();

    articleCriteria.startId = 1;
    articleCriteria.endId = 1;

    List<ArticleDto> result = articleDao.findAll(articleCriteria, null,
        ImmutableSet.of(ArticleFields.ARTICLE_ID));
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
    ArticleCriteria articleCriteria = new ArticleCriteria();

    articleCriteria.startId = 2;
    articleCriteria.endId = 3;

    List<ArticleDto> result = articleDao.findAll(articleCriteria, null,
        ArticleFields.ALL_FIELDS);

    Assert.assertEquals(result.size(), 2);
    Assert.assertEquals(result.get(0).getArticleId(), Integer.valueOf(2));
    Assert.assertEquals(result.get(1).getArticleId(), Integer.valueOf(3));
  }
}
