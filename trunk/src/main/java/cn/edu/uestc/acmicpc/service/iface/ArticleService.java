package cn.edu.uestc.acmicpc.service.iface;

import java.util.List;

import cn.edu.uestc.acmicpc.db.condition.impl.ArticleCondition;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleDTO;
import cn.edu.uestc.acmicpc.db.dto.impl.article.ArticleListDTO;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.web.view.PageInfo;

/**
 * Description
 * TODO(mzry1992)
 */
public interface ArticleService extends DatabaseService<Article, Integer> {

  public ArticleDTO getArticleDTO(Integer articleId) throws AppException;

  public Long count(ArticleCondition condition) throws AppException;

  public List<ArticleListDTO> getArticleList(ArticleCondition condition, PageInfo pageInfo) throws AppException;

}
