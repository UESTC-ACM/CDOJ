package cn.edu.uestc.acmicpc.db.dao.impl;

import org.springframework.stereotype.Repository;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;

/**
 * DAO for article entity.
 */
@Repository
public class ArticleDAO extends DAO<Article, Integer, ArticleDTO> implements IArticleDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Article> getReferenceClass() {
    return Article.class;
  }

  @Override
  public void delete(Integer key) throws AppException {
    throw new UnsupportedOperationException();
  }

  @Override
  public ArticleDTO persist(ArticleDTO dto) throws AppException {
    throw new UnsupportedOperationException();
  }
}
