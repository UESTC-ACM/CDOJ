package cn.edu.uestc.acmicpc.db.dao;

import cn.edu.uestc.acmicpc.db.criteria.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ArticleDao;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.google.common.collect.ImmutableSet;

import java.util.List;

/**
 * Test cases for Daos.
 */
public class DaoImplITTest extends PersistenceITTest {

  @Autowired
  ArticleDao articleDao;

  @Test
  public void test_daos_autowired_successful() {
    Assert.assertNotNull(articleDao);
  }

  @Test
  public void test_fetch_list_with_page_range_successful() throws AppException {
    ArticleCriteria criteria = new ArticleCriteria();

    // 4 articles in total, 1 result per page, and the 3rd article is on page 3.
    List<ArticleDto> result = articleDao.findAll(criteria,
        PageInfo.create(4L, 1L, 1, 3L), ImmutableSet.of(ArticleFields.ARTICLE_ID));
    Assert.assertEquals(result.size(), 1);
    Assert.assertEquals(result.get(0).getArticleId(), Integer.valueOf(3));
  }
}
