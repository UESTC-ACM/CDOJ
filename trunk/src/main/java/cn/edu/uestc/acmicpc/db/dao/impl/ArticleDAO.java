package cn.edu.uestc.acmicpc.db.dao.impl;

import cn.edu.uestc.acmicpc.db.dao.base.DAO;
import cn.edu.uestc.acmicpc.db.dao.iface.IArticleDAO;
import cn.edu.uestc.acmicpc.db.entity.Article;

import org.springframework.stereotype.Repository;

/**
 * DAO for article entity.
 */
@Repository
public class ArticleDAO extends DAO<Article, Integer> implements IArticleDAO {

  @Override
  protected Class<Integer> getPKClass() {
    return Integer.class;
  }

  @Override
  protected Class<Article> getReferenceClass() {
    return Article.class;
  }
}
