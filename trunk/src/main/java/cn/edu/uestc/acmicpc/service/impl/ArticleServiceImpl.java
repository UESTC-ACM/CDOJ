package cn.edu.uestc.acmicpc.service.impl;

import cn.edu.uestc.acmicpc.db.criteria.ArticleCriteria;
import cn.edu.uestc.acmicpc.db.dao.iface.ArticleDao;
import cn.edu.uestc.acmicpc.db.dto.field.ArticleFields;
import cn.edu.uestc.acmicpc.db.dto.impl.ArticleDto;
import cn.edu.uestc.acmicpc.db.entity.Article;
import cn.edu.uestc.acmicpc.service.iface.ArticleService;
import cn.edu.uestc.acmicpc.util.exception.AppException;
import cn.edu.uestc.acmicpc.util.exception.AppExceptionUtil;
import cn.edu.uestc.acmicpc.web.dto.PageInfo;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleServiceImpl extends AbstractService implements ArticleService {

  private final ArticleDao articleDao;

  @Autowired
  public ArticleServiceImpl(ArticleDao articleDao) {
    this.articleDao = articleDao;
  }

  @Override
  public ArticleDto getArticleDto(Integer articleId, Set<ArticleFields> articleFields)
      throws AppException {
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleId;
    articleCriteria.endId = articleId;
    return articleDao.getUniqueDto(articleCriteria, articleFields);
  }

  @Override
  public Long count(ArticleCriteria articleCriteria) throws AppException {
    return articleDao.count(articleCriteria);
  }

  @Override
  public List<ArticleDto> getArticleList(ArticleCriteria articleCriteria, PageInfo pageInfo,
      Set<ArticleFields> articleFields) throws AppException {
    return articleDao.findAll(articleCriteria, pageInfo, articleFields);
  }

  @Override
  public void applyOperation(String field, String ids, String value) throws AppException {
    Map<String, Object> properties = new HashMap<>();
    properties.put(field, value);
    articleDao.updateEntitiesByField(properties, "articleId", ids);
  }

  @Override
  public Integer createNewArticle(Integer authorId) throws AppException {
    Article article = new Article();
    article.setTitle("");
    article.setContent("");
    article.setTime(new Timestamp(new Date().getTime()));
    article.setClicked(0);
    article.setOrder(0);
    article.setType(1);
    article.setIsVisible(true);
    article.setUserId(authorId);
    articleDao.addOrUpdate(article);
    return article.getArticleId();
  }

  private void updateArticleByArticleDto(Article article, ArticleDto articleDto) {
    if (articleDto.getParentId() != null) {
      article.setParentId(articleDto.getParentId());
    }
    if (articleDto.getClicked() != null) {
      article.setClicked(articleDto.getClicked());
    }
    if (articleDto.getContent() != null) {
      article.setContent(articleDto.getContent());
    }
    if (articleDto.getType() != null) {
      article.setType(articleDto.getType());
    }
    if (articleDto.getIsVisible() != null) {
      article.setIsVisible(articleDto.getIsVisible());
    }
    if (articleDto.getOrder() != null) {
      article.setOrder(articleDto.getOrder());
    }
    if (articleDto.getProblemId() != null) {
      article.setProblemId(articleDto.getProblemId());
    }
    if (articleDto.getContestId() != null) {
      article.setContestId(articleDto.getContestId());
    }
    if (articleDto.getTime() != null) {
      article.setTime(articleDto.getTime());
    }
    if (articleDto.getTitle() != null) {
      article.setTitle(articleDto.getTitle());
    }
    if (articleDto.getUserId() != null) {
      article.setUserId(articleDto.getUserId());
    }
  }

  @Override
  public void updateArticle(ArticleDto articleDto) throws AppException {
    AppExceptionUtil.assertNotNull(articleDto);
    AppExceptionUtil.assertNotNull(articleDto.getArticleId());
    Article article = articleDao.get(articleDto.getArticleId());
    AppExceptionUtil.assertNotNull(article);
    updateArticleByArticleDto(article, articleDto);
    articleDao.addOrUpdate(article);
  }

  @Override
  public void incrementClicked(Integer articleId) throws AppException {
    articleDao.increment("clicked", "articleId", articleId.toString());
  }

  @Override
  public Boolean checkArticleExists(Integer articleId) throws AppException {
    AppExceptionUtil.assertNotNull(articleId);
    ArticleCriteria articleCriteria = new ArticleCriteria();
    articleCriteria.startId = articleId;
    articleCriteria.endId = articleId;
    return articleDao.count(articleCriteria) == 1;
  }

}
