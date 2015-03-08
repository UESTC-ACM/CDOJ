package cn.edu.uestc.acmicpc.service.testing;

import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Article provider for intergration testing.
 */
@Component
public class ArticleProvider {

  @Autowired
  private UserProvider userProvider;

  @Autowired
  private ArticleService articleService;

  private Integer testUserId;

  @PostConstruct
  protected void setUp() throws AppException {
    testUserId = userProvider.createUser().getUserId();
  }

  public ArticleDto createArticle() throws AppException {
    Integer articleId = articleService.createNewArticle(testUserId);
    return articleService.getArticleDto(articleId, ArticleFields.ALL_FIELDS);
  }

  public Integer[] createArticles(int count) throws AppException {
    Integer[] articleIds = new Integer[count];
    for (int i = 0; i < count; i++) {
      articleIds[i] = articleService.createNewArticle(testUserId);
    }
    return articleIds;
  }
}
