package cn.edu.uestc.acmicpc.db.criteria;

import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Article database criteria entity.
 */
public class ArticleCriteria extends BaseCriteria<Article, ArticleDto> {

  public ArticleCriteria() {
    super(Article.class, ArticleDto.class);
  }

  public Integer startId;

  public Integer endId;

  public String title;

  public String keyword;

  public Boolean isVisible;

  public Integer userId;

  public Integer problemId;

  public Integer contestId;

  public Integer parentId;

  public Integer type;

  public String userName;

  @Override
  DetachedCriteria updateCriteria(DetachedCriteria criteria) throws AppException {
    if (startId != null) {
      criteria.add(Restrictions.ge("articleId", startId));
    }
    if (endId != null) {
      criteria.add(Restrictions.le("articleId", endId));
    }
    if (title != null) {
      criteria.add(Restrictions.eq("title", title));
    }
    if (isVisible != null) {
      criteria.add(Restrictions.eq("isVisible", isVisible));
    }
    if (userId != null) {
      criteria.add(Restrictions.eq("userId", userId));
    }
    if (type != null) {
      criteria.add(Restrictions.eq("type", type));
    }
    if (userName != null) {
      criteria.add(Restrictions.eq("owner.userName", userName));
      addAlias(ArticleFields.ALIAS_OWNER);
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
      keyword = "%" + keyword + "%";
      criteria.add(Restrictions.or(
          Restrictions.ilike("title", keyword),
          Restrictions.ilike("content", keyword),
          Restrictions.ilike("owner.userName", keyword),
          Restrictions.ilike("owner.nickName", keyword)
          ));
      addAlias(ArticleFields.ALIAS_OWNER);
    }

    return criteria;
  }
}
