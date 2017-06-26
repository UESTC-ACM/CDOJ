package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Article provider for integration testing.
 */
@Component
public class ArticleProvider {

  @Autowired
  private ArticleService articleService;

  public ArticleDto createArticle(Integer userId) throws AppException {
    Integer articleId = articleService.createNewArticle(userId);
    return articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
  }

  public Integer[] createArticles(Integer userId, int count) throws AppException {
    Integer[] articleIds = new Integer[count];
    for (int i = 0; i < count; i++) {
      articleIds[i] = articleService.createNewArticle(userId);
    }
    return articleIds;
  }
}
