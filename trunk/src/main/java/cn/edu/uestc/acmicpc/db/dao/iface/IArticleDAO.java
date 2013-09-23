package cn.edu.uestc.acmicpc.db.dao.iface;

import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * ArticleDAO AOP interface.
 */
public interface IArticleDAO extends IDAO<Article, Integer, ArticleDTO> {

  @Override
  public ArticleDTO persist(ArticleDTO dto) throws AppException;
}
