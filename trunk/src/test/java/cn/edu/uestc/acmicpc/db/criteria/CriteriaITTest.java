package cn.edu.uestc.acmicpc.db.criteria;

import static com.google.common.truth.Truth.assertThat;

import cn.edu.uestc.acmicpc.db.dao.iface.ArticleDao;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.testing.PersistenceITTest;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import com.google.common.collect.ImmutableSet;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.annotations.Test;

/**
 * Test cases for criteria entities.
 */
public class CriteriaITTest extends PersistenceITTest {

  @Autowired
  ArticleDao articleDao;
  
  @Test
  public void testFetchArticle_withSpecifiedFields() throws AppException {
    ArticleDto article = articleProvider.createArticle(testUserId);

    ArticleCriteria articleCriteria = new ArticleCriteria();

    articleCriteria.startId = article.getArticleId();
    articleCriteria.endId = article.getArticleId();

    List<ArticleDto> result = articleDao.findAll(articleCriteria, null,
        ImmutableSet.of(ArticleFields.ARTICLE_ID));

    assertThat(result).hasSize(1);
    ArticleDto articleDto = result.get(0);

    assertThat(articleDto.getArticleId()).isEqualTo(article.getArticleId());
    assertThat(articleDto.getTitle()).isNull();
    assertThat(articleDto.getContent()).isNull();
    assertThat(articleDto.getTime()).isNull();
    assertThat(articleDto.getClicked()).isNull();
    assertThat(articleDto.getOrder()).isNull();
    assertThat(articleDto.getType()).isNull();
    assertThat(articleDto.getIsVisible()).isNull();
    assertThat(articleDto.getParentId()).isNull();
    assertThat(articleDto.getProblemId()).isNull();
    assertThat(articleDto.getContestId()).isNull();
    assertThat(articleDto.getUserId()).isNull();
    assertThat(articleDto.getOwnerName()).isNull();
    assertThat(articleDto.getOwnerEmail()).isNull();
  }
}
