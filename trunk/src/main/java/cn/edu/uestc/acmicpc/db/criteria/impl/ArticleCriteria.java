package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.ArticleDtoProtos;
import cn.edu.uestc.acmicpc.db.entity.Article;

/**
 * Article database criteria entity.
 */
public class ArticleCriteria extends BaseCriteria<Article, ArticleDtoProtos.ArticleDto> {

  public ArticleCriteria() {
    super(Article.class, ArticleDtoProtos.ArticleDto.class);
  }

}
