package cn.edu.uestc.acmicpc.db.criteria.impl;

import cn.edu.uestc.acmicpc.db.criteria.base.BaseCriteria;
import cn.edu.uestc.acmicpc.db.dto.ArticleDtoProtos;
import cn.edu.uestc.acmicpc.db.dto.field.Fields;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Article database criteria entity.
 */
public class ArticleCriteria extends BaseCriteria<Article, ArticleDtoProtos.ArticleDto> {

  public ArticleCriteria(Fields resultFields) {
    super(Article.class, ArticleDtoProtos.ArticleDto.class, resultFields);
  }

  public Integer startId;

  public Integer endId;

  public String keyword;

  public Boolean isVisible;

  public Integer userId;

  public Integer problemId;

  public Integer contestId;

  public Integer parentId;

  public ArticleDtoProtos.ArticleDto.ArticleType type;

  @Override
  public DetachedCriteria getCriteria() throws AppException {
    DetachedCriteria criteria = super.getCriteria();

    if (startId != null) {
      criteria.add(Restrictions.ge("articleId", startId));
    }
    if (endId != null) {
      criteria.add(Restrictions.le("articleId", endId));
    }
    if (isVisible != null) {
      criteria.add(Restrictions.eq("isVisible", isVisible));
    }
    if (userId != null) {
      criteria.add(Restrictions.eq("userId", userId));
    }

    if (contestId != null) {
      if (contestId == -1) {
        criteria.add(Restrictions.isNull("contestId"));
      } else {
        criteria.add(Restrictions.eq("contestId", contestId));
      }
    }
    if (problemId != null) {
      if (problemId == -1) {
        criteria.add(Restrictions.isNull("problemId"));
      } else {
        criteria.add(Restrictions.eq("problemId", problemId));
      }
    }
    if (parentId != null) {
      if (parentId == -1) {
        criteria.add(Restrictions.isNull("parentId"));
      } else {
        criteria.add(Restrictions.eq("parentId", parentId));
      }
    }

    if (keyword != null) {
      criteria.add(Restrictions.or(
          Restrictions.ilike("title", keyword),
          Restrictions.ilike("content", keyword),
          Restrictions.ilike("userByUserId.userName", keyword),
          Restrictions.ilike("userByUserId.nickName", keyword)
      ));
    }

    return criteria;
  }
}
