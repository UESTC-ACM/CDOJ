package cn.edu.uestc.acmicpc.service.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * Description
 * TODO(mzry1992)
 */
public interface ArticleService extends DatabaseService<Article, Integer> {

  public ArticleDTO getArticleDTO(Integer articleId) throws AppException;
}
