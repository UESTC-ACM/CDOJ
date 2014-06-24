package cn.edu.uestc.acmicpc.db.dao;

import cn.edu.uestc.acmicpc.config.IntegrationTestContext;
import cn.edu.uestc.acmicpc.db.dao.iface.ArticleDao;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.AliasToBeanResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Test cases for Daos.
 */
@ContextConfiguration(classes = {IntegrationTestContext.class})
public class DaoImplITTest extends AbstractTestNGSpringContextTests {

  @Autowired
  ArticleDao articleDao;

  @Test
  public void test_daos_autowired_successful() {
    Assert.assertNotNull(articleDao);
  }

  @Test
  public void test_fetch_list_with_page_range_successful() throws AppException {
    // Build criteria manually
    DetachedCriteria criteria = DetachedCriteria.forClass(Article.class);

    criteria.setProjection(Projections.projectionList()
            .add(Projections.property("articleId"), "articleId")
    );
    criteria.setResultTransformer(new AliasToBeanResultTransformer(ArticleDto.class));

    // 4 articles in total, 1 result per page, and the 3rd article is on page 3.
    List<ArticleDto> result = articleDao.findAll(criteria, PageInfo.create(4L, 1L, 1, 3L));
    Assert.assertEquals(result.size(), 1);
    Assert.assertEquals(result.get(0).getArticleId(), Integer.valueOf(3));
  }
}
